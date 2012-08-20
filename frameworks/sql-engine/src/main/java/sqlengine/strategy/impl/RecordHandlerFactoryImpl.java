/**
 * Copyright 2011 the original author
 */
package sqlengine.strategy.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang.StringUtils;

import sqlengine.domain.AccessorDeclared;
import sqlengine.strategy.RecordHandler;
import sqlengine.strategy.RecordHandlerFactory;
import sqlengine.strategy.TypeConverter;


/**
 * The factory to create the <code>RecordHandler</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class RecordHandlerFactoryImpl implements RecordHandlerFactory{

	/** the converter. */
	private TypeConverter converter = new TypeConverterImpl();
	
	/**
	 * @param converter the converter to set
	 */
	public void setTypeConverter(TypeConverter converter){
		this.converter = converter;
	}
	
	/**
	 * @see sqlengine.strategy.RecordHandlerFactory#create(java.lang.Class, java.sql.ResultSet)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RecordHandler create(Class<?> type , ResultSet rs)throws SQLException {
	
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] columnLabels = new String[columnCount];
		String[] columnJavaLabels = new String[columnCount];
		
		//Map
		if( Map.class.isAssignableFrom(type)){			
			for (int i = 0; i < columnCount; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);	
				columnJavaLabels[i] = toJavaString(columnLabels[i]);					
			}
			//AccessorDeclared
			if( type.getAnnotation(AccessorDeclared.class) != null){	
				Method[] target = type.getDeclaredMethods();
				Map<String,Class<?>> types = new HashMap<String,Class<?>>();
				for(Method m : target){
					String name = m.getName();
					if(name.startsWith("get") || name.startsWith("is")){
						int start = name.startsWith("get")? 3 : 2;
						types.put(StringUtils.uncapitalize(name.substring(start)), m.getReturnType());
					}
				}								
				return new ResultMapRecordHandlerImpl((Class<? extends Map>)type, types, columnLabels, columnJavaLabels,converter);
	
			//Plain			
			}else{
				return new MapRecordHandlerImpl(type, columnLabels, columnJavaLabels,converter);
			}
		//Bean	
		}else{
			Map<String,Method> methodMap = new HashMap<String,Method>();
			if(!(Map.class.isAssignableFrom(type))){
				Method[] ms = type.getMethods();
				for(Method m : ms){
					if(m.getName().startsWith("set")){
						methodMap.put(m.getName(), m);
					}
				}
			}
			
			for (int i = 0; i < columnCount; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);
				columnJavaLabels[i] =StringUtils.capitalize(toJavaString(columnLabels[i]));
			}
			return new BeanRecordHandlerImpl(type, columnLabels, columnJavaLabels, methodMap,converter);
		}
		
	}
	
	/**
	 * @param columnLabel カラムラベル
	 * @return JavaString
	 */
	protected String toJavaString(String columnLabel){
		//snake to camel
		Pattern p = Pattern.compile("_([a-z])");
		Matcher m = p.matcher(columnLabel.toLowerCase());
		StringBuffer sb = new StringBuffer(columnLabel.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}
}