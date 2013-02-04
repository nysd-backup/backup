/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.jdbc.strategy.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.coder.alpha.jdbc.exception.QueryException;
import org.coder.alpha.jdbc.strategy.RecordHandler;
import org.coder.alpha.jdbc.strategy.TypeConverter;



/**
 * Gets the one record from ResultSet to map. 
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("rawtypes")
public class ResultMapRecordHandler implements RecordHandler{
	
	/** the names of the column */
	private final String[] labels;
	
	/** the names of the column */
	private final String[] javaLabels;

	/** the result type */
	private final Class<? extends Map> type;
	
	/** the result map */
	private final Map<String,Class<?>> typeMap;
	
	/** the engine */
	private TypeConverter converter;
	
	/**
	 * @param resultType the resultType 
	 * @param labels the labels
	 * @param types the types
	 * @param converter the converter
	 */
	public ResultMapRecordHandler(Class<? extends Map> type , Map<String,Class<?>> typeMap,String[] labels, String[] javaLabels, TypeConverter converter){
		this.labels = labels;
		this.javaLabels = javaLabels;
		this.converter = converter;
		this.type = type;
		this.typeMap = typeMap;
	}

	/**
	 * @see org.coder.alpha.jdbc.strategy.RecordHandler#getRecord(java.sql.ResultSet)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getRecord(ResultSet resultSet) throws SQLException{
		T row = null;
		try {
			row = (T)type.newInstance();
		} catch (Exception e1) {
			throw new QueryException(e1);
		}
		
		//データ取得
		int size = labels.length;
		for (int i = 0; i < size; i++) {

			String label = labels[i];
			String javaLabel = javaLabels[i];
			try{
				Object value = converter.getParameter(typeMap.get(javaLabel), resultSet, label);
				((Map)row).put(javaLabel, value);
			}catch(SQLException sqle){
				throw sqle;
			} catch (Exception e) {
				throw new QueryException(String.format("label = %s : type = %d ",label,typeMap.get(javaLabel)),e);
			}
		}
		
		return row;
	}

}
