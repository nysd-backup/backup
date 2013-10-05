/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
	 * @see org.coder.alpha.query.free.loader.QueryLoader#build(java.lang.String, java.lang.String)
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
					throw new IllegalStateException(fnfe);
				}
			}
		
			if (stream == null) {
				throw new IllegalStateException(String.format("file not found : sqlId = %s , filePath = %s",queryId, dirRoot +filePath));
			}
			return engine.load(stream);		
		} else {
			return rowString;
		}
			
	}
	
	/**
	 * @see org.coder.alpha.query.free.loader.QueryLoader#evaluate(java.lang.String, java.util.Map, java.lang.String)
	 */
	public String evaluate(String query , Map<String,Object> parameter,String queryId){
		return engine.evaluate(query, parameter);
	}

	/**
	 * @see org.coder.alpha.query.free.loader.QueryLoader#prepare(java.lang.String, java.util.List, java.lang.String)
	 */
	@Override
	public PreparedQuery prepare(String sql , Map<String,Object> params ,String wrapClause,String sqlId){

		List<Object> bindList = new ArrayList<Object>();	
		final StringBuffer buff = new StringBuffer(sql.length());

		// バインド変数を検索
		final Matcher match = BIND_VAR_PATTERN.matcher(sql);

		// バインド変数にマッチした部分を置換
		while (match.find()) {
			// マッチしたバインド変数名を取得後、空白、最初のコロンを除去する
			String variableName = match.group(2);
			String question = match.group(1) + "?";		
			Object variable = getValue(params, variableName);
			
			if (variable != null){
				Object val = variable.getClass().isArray() ? Arrays.asList((Object[])variable) : variable;				
				
				// List型へのバインドの場合はサイズ文?を追加する
				if((val instanceof Collection<?>) && !((Collection<?>)val).isEmpty()) {
					Collection<?> list = (Collection<?>) val;
					// リストの1番目の追加
					StringBuilder questions = new StringBuilder(question);
					int i = 0;
					for(Object o : list){
						questions.append(i == 0 ? "" : ",").append("?");
						bindList.add(convert(o,variableName,sqlId));
						i++;
					}					
					question = questions.toString();						
				}else {
					bindList.add(convert(variable,variableName,sqlId));
				}				
			}else{
				bindList.add(null);
			}
			match.appendReplacement(buff, question);			
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
				throw new IllegalStateException("invalid parameter : name=" + variableName );
			}
		}else{
			return paramMap.get(variableName);	
		}
	}
	
	/**
	 * Convert value.
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
