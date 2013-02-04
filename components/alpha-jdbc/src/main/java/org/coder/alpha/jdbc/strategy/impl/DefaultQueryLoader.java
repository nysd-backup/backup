/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.coder.alpha.jdbc.domain.PreparedQuery;
import org.coder.alpha.jdbc.exception.QueryException;
import org.coder.alpha.jdbc.strategy.ConstantAccessor;
import org.coder.alpha.jdbc.strategy.QueryLoader;
import org.coder.alpha.jdbc.strategy.TemplateEngine;



/**
 * Builds the SQL from the file.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultQueryLoader implements QueryLoader{
	
	/** the classpath prefix */
	private static final String CLASSPATH_PREFIX = "classpath:";
	
	/** the pattern for binding value. */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-zA-Z0-9][a-zA-Z0-9_]*)");

	/** the template engine */
	private TemplateEngine engine = new VelocityTemplateEngine();
	
	/** the accessor */ 
	private ConstantAccessor accessor = new DefaultConstantAccessor();
	
	/** the root directory */
	private String dirRoot = CLASSPATH_PREFIX + "query/";
	
	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(DefaultConstantAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @param dirRoot the dirRoot to set
	 */
	public void setDirRoot(String dirRoot){
		this.dirRoot = dirRoot;
	}
	
	/**
	 * @param engine the engine to set
	 */
	public void setEngine(TemplateEngine engine){
		this.engine = engine;
	}
	
	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#build(java.lang.String, java.lang.String)
	 */
	@Override
	public String build(String queryId ,String rowString) {
		Matcher matcher = Pattern.compile("^@(.+)").matcher(rowString);

		if (matcher.find()) {
			String filePath = matcher.group(1);
			InputStream stream = null;
			
			if(dirRoot.startsWith(CLASSPATH_PREFIX)){
				stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(dirRoot.substring(CLASSPATH_PREFIX.length()) + filePath);	
			}else{
				try{
					stream = new FileInputStream(new File(dirRoot,filePath));
				}catch(FileNotFoundException fnfe){
					throw new QueryException(fnfe);
				}
			}
		
			if (stream == null) {
				throw new QueryException(String.format("file not found : sqlId = %s , filePath = %s",queryId, dirRoot +filePath));
			}
			return engine.load(stream);		
		} else {
			return rowString;
		}
			
	}
	
	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	public String evaluate(String query , Map<String,Object> parameter,String queryId){
		return engine.evaluate(query, parameter);
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.QueryLoader#prepare(java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String sql , List<Map<String,Object>> params ,String wrapClause,String sqlId){

		List<List<Object>> bindList = new ArrayList<List<Object>>();
		for(int i = 0; i <params.size();i++){
			bindList.add(new ArrayList<Object>());
		}
		final StringBuffer buff = new StringBuffer(sql.length());

		// バインド変数を検索
		final Matcher match = BIND_VAR_PATTERN.matcher(sql);

		// バインド変数にマッチした部分を置換
		while (match.find()) {
			// マッチしたバインド変数名を取得後、空白、最初のコロンを除去する
			String variableName = match.group(2);
			String question = null;
			
			for(int i = 0 ; i < params.size(); i ++){			
				List<Object> binds = bindList.get(i);				
				Object variable = getValue(params.get(i), variableName);
	
				//?は最初のリストで判定する
				if(i == 0){
					question = match.group(1) + "?";
				}
				
				// List型へのバインドの場合はサイズ文?を追加する
				if (variable != null){
					Object val = variable.getClass().isArray() ? Arrays.asList((Object[])variable) : variable;				
					if((val instanceof List<?>) && !((List<?>)val).isEmpty()) {
						final List<?> list = (List<?>) val;
						//?は最初のリストで判定する
						if(i == 0 ){
							// リストの1番目の追加
							StringBuilder questions = new StringBuilder(question);
							binds.add(list.get(0));
							// リストの番目以降に追加
							for (int j = 1; j < list.size(); j++) {
								questions.append(",?");
								binds.add(convert(list.get(j),variableName,sqlId));
							}
							question = questions.toString();
						}else{
							for (int j = 0; j < list.size(); j++) {
								binds.add(convert(list.get(j),variableName,sqlId));
							}
						}						
					
					}else {
						binds.add(convert(variable,variableName,sqlId));
					}				
				}else{
					binds.add(convert(variable,variableName,sqlId));
				}

			}
			match.appendReplacement(buff, question);			
		}
		
		int parameterCount = -1;
		for(int i = 0 ; i < bindList.size(); i ++){
			if(parameterCount == -1 ){
				parameterCount = bindList.get(i).size();
			}else if(bindList.get(i).size() != parameterCount){
				throw new QueryException("batch parameter count must be same : current=" + bindList.get(i).size() + " : previous=" + parameterCount + " : batchIndex=" + i);				
			}
		}
		
		match.appendTail(buff);
		
		String preparedSql = buff.toString();
		if(wrapClause != null && !wrapClause.isEmpty()){
			preparedSql = String.format(wrapClause, preparedSql);
		}
		return new PreparedQuery(preparedSql,bindList,sqlId);
		
	}
	
	/**
	 * Gets the value 
	 * @param paramMap
	 * @param variableName
	 * @return
	 */
	protected Object getValue(Map<String,Object> paramMap , String variableName){
		//パラメータがなければ定数キャッシュから取得する
		if(!paramMap.containsKey(variableName) ){
			if(accessor.isValidKey(variableName)){
				return accessor.getConstTarget(variableName);		
			}else{
				throw new QueryException("invalid parameter : name=" + variableName );
			}
		}else{
			return paramMap.get(variableName);	
		}
	}
	
	/**
	 * Convert value
	 * @param value source value
	 * @return converted value
	 */
	protected Object convert(Object value,String name, String sqlId){
		if(value instanceof Boolean ){
			return (Boolean)value ? BigDecimal.ONE.toString() : BigDecimal.ZERO.toString(); 
		}
		return value;
	}

}
