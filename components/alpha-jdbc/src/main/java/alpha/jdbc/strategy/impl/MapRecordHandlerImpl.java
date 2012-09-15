/**
 * Copyright 2011 the original author
 */
package alpha.jdbc.strategy.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import alpha.jdbc.exception.QueryException;
import alpha.jdbc.strategy.RecordHandler;
import alpha.jdbc.strategy.TypeConverter;




/**
 * Gets the one record from ResultSet to map. 
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class MapRecordHandlerImpl implements RecordHandler{
	
	/** the names of the column */
	private final String[] labels;
	
	/** the names of the column */
	private final String[] javaLabels;
	
	/** the result type */
	private final Class<?> resultType;
	
	/** the engine */
	private TypeConverter converter;
	
	/**
	 * @param resultType the resultType 
	 * @param labels the labels
	 * @param types the types
	 * @param converter the converter
	 */
	public MapRecordHandlerImpl(Class<?> resultType , String[] labels, String[] javaLabels, TypeConverter converter){
		this.labels = labels;
		this.javaLabels = javaLabels;
		this.resultType = resultType;
		this.converter = converter;
	}

	/**
	 * @see alpha.jdbc.strategy.RecordHandler#getRecord(java.sql.ResultSet)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> T getRecord(ResultSet resultSet) throws SQLException{
		T row = null;
		if(LinkedHashMap.class.equals(resultType)){
			row = (T)new LinkedHashMap<String,Object>();			
		}else if(HashMap.class.equals(resultType)){
			row = (T)new HashMap<String,Object>();
		}else if(TreeMap.class.equals(resultType)){
			row = (T)new TreeMap<String,Object>();			
		}else{
			try {
				row = (T)resultType.newInstance();
			} catch (Exception e) {
				throw new QueryException(e);
			}
		}
		
		//データ取得
		int size = labels.length;
		for (int i = 0; i < size; i++) {

			String label = labels[i];
			String javaLabel = javaLabels[i];
			try{
				Object value = converter.getParameter(Object.class, resultSet, label);
				((Map)row).put(javaLabel, value);
			}catch(SQLException sqle){
				throw sqle;
			} catch (Exception e) {
				throw new QueryException(String.format("label = %s : type = %s",label,String.class.getName()),e);
			}
		}
		
		return row;
	}

}
