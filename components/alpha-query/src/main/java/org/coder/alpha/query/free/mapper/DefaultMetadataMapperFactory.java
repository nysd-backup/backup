/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.mapper;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;




/**
 * The factory to create the <code>RecordHandler</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DefaultMetadataMapperFactory implements MetadataMapperFactory{

	/** the converter. */
	private TypeConverter converter = new DefaultTypeConverter();
	
	/**
	 * @param converter the converter to set
	 */
	public void setTypeConverter(TypeConverter converter){
		this.converter = converter;
	}
	
	/**
	 * @see org.coder.alpha.query.free.mapper.MetadataMapperFactory#create(java.lang.Class, java.sql.ResultSet)
	 */
	public MetadataMapper create(Class<?> type , ResultSet rs)throws SQLException {
	
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
			return new MapMetadataMapper(type, columnLabels, columnJavaLabels,converter);
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
			return new BeanMetadataMapper(type, columnLabels, columnJavaLabels, methodMap,converter);
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
