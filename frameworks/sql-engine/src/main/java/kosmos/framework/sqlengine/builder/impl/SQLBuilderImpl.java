/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kosmos.framework.sqlengine.builder.ConstAccessor;
import kosmos.framework.sqlengine.builder.SQLBuilder;
import kosmos.framework.sqlengine.builder.TemplateEngine;
import kosmos.framework.sqlengine.exception.SQLEngineException;


/**
 * Builds the SQL from the file.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLBuilderImpl implements SQLBuilder{
	
	/** the pattern for binding value. */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");

	/** the template engine */
	private TemplateEngine engine = new VelocityTemplateEngineImpl();
	
	/** the accessor */ 
	private ConstAccessor accessor = new ConstAccessorImpl();
	
	/** the root directory */
	private String dirRoot = null;
	
	/**
	 * @param accessor the accessor to set
	 */
	public void setConstAccessor(ConstAccessorImpl accessor){
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
	 * @see kosmos.framework.sqlengine.builder.SQLBuilder#build(java.lang.String, java.lang.String)
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
					throw new SQLEngineException(fnfe);
				}
			}
			if (stream == null) {
				throw new SQLEngineException(String.format("file not found : sqlId = %s , filePath = %s",queryId, filePath));
			}
			return engine.load(stream);		
		} else {
			return rowString;
		}
			
	}
	
	/**
	 * @see kosmos.framework.sqlengine.builder.SQLBuilder#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	public String evaluate(String query , Map<String,Object> parameter,String queryId){
		return engine.evaluate(query, parameter);
	}

	/**
	 * @see kosmos.framework.sqlengine.builder.SQLBuilder#setCount(java.lang.String)
	 */
	@Override
	public String setCount(String sql) {
		return String.format("select count(*) from (%s)",sql);
	}

	/**
	 * @see kosmos.framework.sqlengine.builder.SQLBuilder#replaceToPreparedSql(java.lang.String, java.util.Map, java.util.List, java.lang.String)
	 */
	@Override
	public String replaceToPreparedSql(String sql , List<Map<String,Object>> params ,List<List<Object>> bindList,String sqlId){

		if(params.isEmpty() || bindList.isEmpty()){
			throw new IllegalArgumentException("parameter is required");
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
							for (int j = 1; j < list.size(); i++) {
								questions.append(",?");
								binds.add(list.get(j));
							}
							question = questions.toString();
						}else{
							binds.add(list.get(0));
							for (int j = 1; j < list.size(); i++) {
								binds.add(list.get(j));
							}
						}						
					
					}else {
						binds.add(variable);
					}				
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
		
		String firingSql = buff.toString();

		return firingSql;
	}

}
