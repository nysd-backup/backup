/**
 * Use is subject to license terms.
 */
package framework.sqlengine.executer.impl;

import java.sql.ResultSet;
import java.util.Map;

import framework.sqlengine.exception.SQLEngineException;
import framework.sqlengine.executer.RecordHandler;
import framework.sqlengine.executer.TypeConverter;

/**
 * ResultSetの1行をMapに格納する.
 *
 * @author yoshida-n
 * @version created.
 */
public class MapRecordHandlerImpl<T> implements RecordHandler<T>{
	
	/** カラム名. */
	private final String[] labels;
	
	/** カラム型. */
	private final int[] types;
	
	/** 結果型. */
	private final Class<T> resultType;
	
	/** 型変換エンジン. */
	private TypeConverter converter;
	
	/**
	 * @param resultType 結果型 
	 * @param labels カラム名
	 * @param types カラム型
	 * @param converter 型変換エンジン
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
