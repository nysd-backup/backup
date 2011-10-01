/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.executer.impl;

import java.sql.ResultSet;
import java.util.Map;

import framework.sqlengine.exception.SQLEngineException;
import framework.sqlengine.executer.RecordHandler;
import framework.sqlengine.executer.TypeConverter;

/**
 * Gets the one record from ResultSet to map. 
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MapRecordHandlerImpl<T> implements RecordHandler<T>{
	
	/** the names of the column */
	private final String[] labels;
	
	/** the types of the column */
	private final int[] types;
	
	/** the result type */
	private final Class<T> resultType;
	
	/** the engine */
	private TypeConverter converter;
	
	/**
	 * @param resultType the resultType 
	 * @param labels the labels
	 * @param types the types
	 * @param converter the converter
	 */
	public MapRecordHandlerImpl(Class<T> resultType , String[] labels, int[] types ,TypeConverter converter){
		this.labels = labels;
		this.types = types;
		this.resultType = resultType;
		this.converter = converter;
	}

	/**
	 * @see framework.sqlengine.executer.RecordHandler#getRecord(java.sql.ResultSet)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T getRecord(ResultSet resultSet) {
		T row = null;
		try{
			row = resultType.newInstance();
		}catch(Exception e){
			throw new SQLEngineException(e);
		}
		
		//データ取得
		int size = labels.length;
		for (int i = 0; i < size; i++) {

			String label = labels[i];
			int type = types[i];
			try{
				Object value = converter.getParameter(Object.class, resultSet, label);
				((Map)row).put(label, value);
			} catch (Exception e) {
				throw new SQLEngineException(String.format("label = %s : type = %d ",label,type),e);
			}
		}
		
		return row;
	}

}
