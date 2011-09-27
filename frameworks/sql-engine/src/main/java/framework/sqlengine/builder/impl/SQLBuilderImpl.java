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
import framework.sqlengine.builder.SQLBuilder;
import framework.sqlengine.builder.TemplateEngine;
import framework.sqlengine.exception.SQLEngineException;

/**
 * SQLファイルからSQLを読み取る.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLBuilderImpl implements SQLBuilder{
	
	/** バインド変数の正規表現パターン. */
	private static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");

	/** チE��プレートエンジン. */
	private TemplateEngine engine = new VelocityTemplateEngineImpl();
	
	/** 定数アクセス. */ 
	private ConstAccessor accessor = new ConstAccessorImpl();
	
	/** 絶対パスの場合�EルーチE */
	private String dirRoot = null;
	
	/**
	 * @param accessor 定数アクセス
	 */
	public void setConstAccessor(ConstAccessorImpl accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @param dirRoot 絶対パスの場合�EルーチE
	 */
	public void setDirRoot(String dirRoot){
		this.dirRoot = dirRoot;
	}
	
	/**
	 * @param engine チE��プレートエンジン
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

		// バインド変数にマッチした部刁E��?に置換すめE
		while (match.find()) {
			// マッチしたバインド変数名を取征E前後�E空白、E斁E��目のコロンを除ぁE
			String variableName = match.group(2);
			Object variable = params.get(variableName);		
			if(variable == null ){
				Object[] value = accessor.getConstTarget(variableName);
				if( value.length > 0){
					variable = value[0];
				}
			}			

			String question = match.group(1) + "?";

			// List型へのバインド�Eそ�EListのサイズ刁E��ぁEに変換し、バインド変数用リストにListの中身を追加する
			if (variable != null){
				Object val = variable;
				if( val.getClass().isArray()){
					val = Arrays.asList((Object[])val);
				}
				if(val instanceof List<?>) {
					final List<?> list = (List<?>) val;
					if (!list.isEmpty()) {
						// リスト�E1番目の処琁E
						StringBuilder questions = new StringBuilder(question);
						bindList.add(list.get(0));
						// リスト�E2番目以降�E処琁E
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
		
		//JPQLから作�EされるOracleの仕様に合わせる
		String firingSql = sql;
		if(firstResult > 0 && getSize > 0){
			firingSql = String.format("SELECT * FROM (SELECT a.*,ROWNUM rnum FROM (%s) a WHERE ROWNUM <= ?) WHERE rnum > ? ",firingSql);
			bindList.add(bindList.size(),firstResult+getSize);
			bindList.add(bindList.size(),firstResult);			
		}else if( firstResult > 0 ){
			firingSql = String.format("SELECT * FROM (SELECT a.*,ROWNUM rnum FROM (%s) a) WHERE rnum > ? ",firingSql);
			bindList.add(bindList.size(),firstResult);
		}else if( getSize > 0){
			firingSql = String.format("SELECT * FROM (SELECT a.*,ROWNUM rnum FROM (%s) a WHERE ROWNUM <= ?) ",firingSql);
			bindList.add(bindList.size(),getSize); 
		}
		return firingSql;			
	}

}
