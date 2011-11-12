/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kosmos.framework.sqlengine.executer.RecordHandler;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.TypeConverter;

import org.apache.commons.lang.StringUtils;


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
	public void setConveter(TypeConverter converter){
		this.converter = converter;
	}
	
	/**
	 * @see kosmos.framework.sqlengine.executer.RecordHandlerFactory#create(java.lang.Class, java.sql.ResultSet)
	 */
	public <T> RecordHandler<T> create(Class<T> type , ResultSet rs)throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] columnLabels = new String[columnCount];
		
		//Map
		if( Map.class.isAssignableFrom(type)){
			
			int[] columnType = new int[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);		
				columnType[i] = metaData.getColumnType(i + 1);
			}
			return new MapRecordHandlerImpl<T>(type, columnLabels, columnType,converter);
						
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
				columnLabels[i] = toJavaString(metaData.getColumnLabel(i + 1));		
			}
			return new BeanRecordHandlerImpl<T>(type, columnLabels, methodMap,converter);
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
		return StringUtils.capitalize(sb.toString());
	}
}
