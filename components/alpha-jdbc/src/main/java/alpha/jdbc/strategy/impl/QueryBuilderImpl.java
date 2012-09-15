/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alpha.jdbc.domain.PreparedQuery;
import alpha.jdbc.exception.QueryException;
import alpha.jdbc.strategy.ConstantAccessor;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.TemplateEngine;




/**
 * Builds the SQL from the file.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryBuilderImpl implements QueryBuilder{
	
	/** the pattern for binding value. */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-zA-Z0-9][a-zA-Z0-9_]*)");

	/** the template engine */
	private TemplateEngine engine = new VelocityTemplateEngineImpl();
	
	/** the accessor */ 
	private ConstantAccessor accessor = new ConstantAccessorImpl();
	
	/** the root directory */
	private String dirRoot = null;
	
	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstantAccessorImpl accessor){
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
	 * @see alpha.jdbc.strategy.QueryBuilder#build(java.lang.String, java.lang.String)
	 */
	@Override
	public String build(String queryId ,String rowString) {
		Matcher matcher = Pattern.compile("^@(.+)").matcher(rowString);

		if (matcher.find()) {
			String filePath = matcher.group(1);
			InputStream stream = null;
			
			if (dirRoot == null) {
				stream = getClass().getResourceAsStream(filePath);	
			} else {
				try{
					stream = new FileInputStream(new File(dirRoot,filePath));
				}catch(FileNotFoundException fnfe){
					throw new QueryException(fnfe);
				}
			}
			if (stream == null) {
				throw new QueryException(String.format("file not found : sqlId = %s , filePath = %s",queryId, filePath));
			}
			return engine.load(stream);		
		} else {
			return rowString;
		}
			
	}
	
	/**
	 * @see alpha.jdbc.strategy.QueryBuilder#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	public String evaluate(String query , Map<String,Object> parameter,String queryId){
		return engine.evaluate(query, parameter);
	}

	/**
	 * @see alpha.jdbc.strategy.QueryBuilder#prepare(java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String sql , List<Map<String,Object>> params ,String wrapClause,String sqlId){

		List<List<Object>> bindList = new ArrayList<List<Object>>();
		if(params.isEmpty()){
			throw new IllegalArgumentException("parameter is required");
		}
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
				Map<String,Object> map = params.get(i);				
				List<Object> binds = bindList.get(i);
				
				Object variable = null;
				//パラメータがなければ定数キャッシュから取得する
				if(!map.containsKey(variableName) ){
					Object[] value = accessor.getConstTarget(variableName);		
					if( value.length < 1){
						throw new IllegalArgumentException("invalid parameter : name=" + variableName + " : batchIndex=" + i);
					}
					variable = value[0];				
				}else{
					variable =  map.get(variableName);	
				}
	
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
								binds.add(list.get(j));
							}
							question = questions.toString();
						}else{
							for (int j = 0; j < list.size(); j++) {
								binds.add(list.get(j));
							}
						}						
					
					}else {
						binds.add(variable);
					}				
				}else{
					binds.add(variable);
				}

			}
			match.appendReplacement(buff, question);			
		}
		
		int parameterCount = -1;
		for(int i = 0 ; i < bindList.size(); i ++){
			if(parameterCount == -1 ){
				parameterCount = bindList.get(i).size();
			}else if(bindList.get(i).size() != parameterCount){
				throw new IllegalArgumentException("batch parameter count must be same : current=" + bindList.get(i).size() + " : previous=" + parameterCount + " : batchIndex=" + i);				
			}
		}
		
		match.appendTail(buff);
		
		String preparedSql = buff.toString();
		if(wrapClause != null && !wrapClause.isEmpty()){
			preparedSql = String.format(wrapClause, preparedSql);
		}
		return new PreparedQuery(preparedSql,bindList,sqlId);
		
	}

}
