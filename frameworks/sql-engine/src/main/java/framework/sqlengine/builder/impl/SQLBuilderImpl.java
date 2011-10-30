/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.sqlengine.builder.ConstAccessor;
import framework.sqlengine.builder.RangeBuilder;
import framework.sqlengine.builder.SQLBuilder;
import framework.sqlengine.builder.TemplateEngine;
import framework.sqlengine.exception.SQLEngineException;

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
	
	/** the rangeBuilder */
	private RangeBuilder rangeBuilder = new OracleRangeBuilderImpl();
	
	/** the root directory */
	private String dirRoot = null;
	
	/**
	 * @param rangeBuilder the rangeBuilder to set
	 */
	public void setRangeBuilder(RangeBuilder rangeBuilder){
		this.rangeBuilder = rangeBuilder;
	}
	
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
	 * @see framework.sqlengine.builder.SQLBuilder#build(java.lang.String, java.lang.String)
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
	 * @see framework.sqlengine.builder.SQLBuilder#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	public String evaluate(String query , Map<String,Object> parameter,String queryId){
		return engine.evaluate(query, parameter);
	}

	/**
	 * @see framework.sqlengine.builder.SQLBuilder#setCount(java.lang.String)
	 */
	@Override
	public String setCount(String sql) {
		return String.format("select count(*) from (%s)",sql);
	}

	/**
	 * @see framework.sqlengine.builder.SQLBuilder#replaceToPreparedSql(java.lang.String, java.util.Map, java.util.List, java.lang.String)
	 */
	@Override
	public String replaceToPreparedSql(String sql , Map<String,Object> params ,List<Object> bindList,String sqlId){

		final StringBuffer buff = new StringBuffer(sql.length());

		// バインド変数を検索
		final Matcher match = BIND_VAR_PATTERN.matcher(sql);

		// バインド変数にマッチした部分を置換
		while (match.find()) {
			// マッチしたバインド変数名を取得後、空白、最初のコロンを除去する
			String variableName = match.group(2);
			Object variable = params.get(variableName);		
			if(variable == null ){
				Object[] value = accessor.getConstTarget(variableName);
				if( value.length > 0){
					variable = value[0];
				}
			}			

			String question = match.group(1) + "?";

			// List型へのバインドの場合はサイズ文?を追加する
			if (variable != null){
				Object val = variable;
				if( val.getClass().isArray()){
					val = Arrays.asList((Object[])val);
				}
				if(val instanceof List<?>) {
					final List<?> list = (List<?>) val;
					if (!list.isEmpty()) {
						// リストの1番目の追加
						StringBuilder questions = new StringBuilder(question);
						bindList.add(list.get(0));
						// リストの番目以降に追加
						for (int i = 1; i < list.size(); i++) {
							questions.append(",?");
							bindList.add(list.get(i));
						}
						question = questions.toString();
					}
				}else {
					bindList.add(variable);
				}				
			}			
			match.appendReplacement(buff, question);
		}
		match.appendTail(buff);
		
		String firingSql = buff.toString();

		return firingSql;
	}
	
	/**
	 * @see framework.sqlengine.builder.SQLBuilder#setRange(java.lang.String, int, int, java.util.List)
	 */
	@Override
	public String setRange(String sql , int firstResult , int getSize, List<Object> bindList){
		return rangeBuilder.setRange(sql, firstResult, getSize, bindList);
	}

}
