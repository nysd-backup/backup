/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.executer.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import kosmos.framework.bean.PropertyAccessor;
import kosmos.framework.sqlengine.exception.SQLEngineException;
import kosmos.framework.sqlengine.executer.RecordHandler;
import kosmos.framework.sqlengine.executer.TypeConverter;


/**
 * Gets the one record from ResultSet for JavaBean.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class FastBeanRecordHandlerImpl implements RecordHandler {

	/** the type*/
	private final PropertyAccessor<Object> accessor;
	
	/** the names of the column */
	private final String[] labels;
	
	/** the names of the column */
	private final String[] javaLabels;
	
	/** the types */
	private final Class<?>[] types;
	
	/** the engine. */
	private TypeConverter converter;
	
	/**
	 * @param resultType the resultType 
	 * @param labels the labels
	 * @param methodMap the setter methods
	 * @param converter the converter
	 */
	public FastBeanRecordHandlerImpl(PropertyAccessor<Object> accessor, String[] labels, String[] javaLabels, Class<?>[] types,TypeConverter converter){
		this.accessor = accessor;
		this.javaLabels = javaLabels;
		this.labels = labels;
		this.converter = converter;
		this.types = types;
	}
	
	/**
	 * @see kosmos.framework.sqlengine.executer.RecordHandler#getRecord(java.sql.ResultSet)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getRecord(ResultSet resultSet) throws SQLException {
		T row = (T)accessor.create();
		
		int size = labels.length;
		Map<String,Object> values = new HashMap<String,Object>();
		for (int i = 0; i < size; i++) {							
			try{				
				Object value = converter.getParameter(types[i], resultSet, labels[i]);
				values.put(javaLabels[i], value);
			}catch(SQLException sqle){
				throw sqle;
			} catch (Exception e) {
				throw new SQLEngineException(String.format("label = %s",labels[i]),e);
			}
		}
		accessor.setProperties(values, row);
		return (T)row;
	}


}
