/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder.impl;

import java.util.List;

import framework.sqlengine.builder.RangeBuilder;

/**
 * Sets the range of query only for ORACLE.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OracleRangeBuilderImpl implements RangeBuilder{

	/**
	 * @see framework.sqlengine.builder.RangeBuilder#setRange(java.lang.String, int, int, java.util.List)
	 */
	@Override
	public String setRange(String sql , int firstResult , int getSize, List<Object> bindList){
		
		//JPQLから作成されるOracleの仕様に合わせる
		String firingSql = sql;
		if(firstResult > 0 && getSize > 0){
			firingSql = String.format("SELECT * FROM (SELECT a.*,ROWNUM rnum FROM (%s) a WHERE ROWNUM <= ?) WHERE rnum > ? ",firingSql);
			bindList.add(bindList.size(),firstResult+getSize);
			bindList.add(bindList.size(),firstResult);			
		}else if( firstResult > 0 ){
			firingSql = String.format("SELECT * FROM (SELECT a.*,ROWNUM rnum FROM (%s) a) WHERE rnum > ? ",firingSql);
			bindList.add(bindList.size(),firstResult);
		}else if( getSize > 0){
			firingSql = String.format("SELECT * FROM (SELECT a.*,ROWNUM rnum FROM (%s) a WHERE ROWNUM <= ?) ",firingSql);
			bindList.add(bindList.size(),getSize); 
		}
		return firingSql;			
	}
}
