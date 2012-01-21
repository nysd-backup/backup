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

import kosmos.framework.bean.FastAccessible;
import kosmos.framework.bean.Pair;
import kosmos.framework.bean.PropertyAccessor;
import kosmos.framework.sqlengine.executer.RecordHandler;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.TypeConverter;
import kosmos.framework.utility.ClassUtils;
import kosmos.framework.utility.ReflectionUtils;

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
	public RecordHandler create(Class<?> type , ResultSet rs)throws SQLException {
	
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] columnLabels = new String[columnCount];
		String[] columnJavaLabels = new String[columnCount];
		
		//Map
		if( Map.class.isAssignableFrom(type)){			
			int[] columnType = new int[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);	
				columnJavaLabels[i] = toJavaString(columnLabels[i]);
				columnType[i] = metaData.getColumnType(i + 1);
			}
			return new MapRecordHandlerImpl(type, columnLabels, columnJavaLabels,columnType,converter);
			
		//FastBean	
		}else if(type.getAnnotation(FastAccessible.class) != null){			
			@SuppressWarnings("unchecked")
			PropertyAccessor<Object> accessor = (PropertyAccessor<Object>)ClassUtils.newInstance(type.getAnnotation(FastAccessible.class).propertyAccessor());	
			Map<String,Pair<Class<?>>> types = accessor.getProperties(accessor.create());
			Class<?>[] columnTypes = new Class<?>[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnLabels[i] = metaData.getColumnLabel(i + 1);
				columnJavaLabels[i] = toJavaString(columnLabels[i]);			
				columnTypes[i] = types.get(columnJavaLabels[i]).getKey();
			}			
			return new FastBeanRecordHandlerImpl(accessor, columnLabels,columnJavaLabels,columnTypes,converter);
			
		//Bean	
		}else{
			Map<String,Method> methodMap = new HashMap<String,Method>();
			if(!(Map.class.isAssignableFrom(type))){
				Method[] ms = type.getMethods();
				for(Method m : ms){
					if(ReflectionUtils.isSetter(m)){
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
