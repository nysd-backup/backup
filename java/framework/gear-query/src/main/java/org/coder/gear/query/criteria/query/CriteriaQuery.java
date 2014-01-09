/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.Operand;
import org.coder.gear.query.criteria.statement.JPQLBuilder;
import org.coder.gear.query.criteria.statement.StatementBuilder;
import org.coder.gear.query.free.loader.QueryLoaderTrace;
import org.coder.gear.query.gateway.JpqlGateway;
import org.coder.gear.query.gateway.PersistenceGateway;
import org.coder.gear.query.gateway.PersistenceGatewayTrace;

/**
 * function.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class CriteriaQuery<T> {
	
	/** the criteria */
	private List<Criteria> criterias = new ArrayList<Criteria>();

	private Map<String,Object> hints = new HashMap<String,Object>();
	
	/** the persistenceGateway */
	protected PersistenceGateway gateway;
	
	/** the query builder factory */
	protected StatementBuilder builder;
	
	/** entity manager */
	protected EntityManager em;
	
	/**
	 * Constructor
	 */
	public CriteriaQuery() {
		construct();
	}
	
	/**
	 * Construct
	 */
	protected void construct() {
		builder = new JPQLBuilder();
		JpqlGateway named = new JpqlGateway();
		named.setQueryLoader(new QueryLoaderTrace());				
		if(PersistenceGatewayTrace.isEnabled()){
			PersistenceGatewayTrace trace = new PersistenceGatewayTrace();
			trace.setDelegate(named);
			gateway = trace;
		}else{
			gateway = named;
		}
	}
	
	/**
	 * Add hint.
	 * 
	 * @param key the key
	 * @param value the value
	 * @return self
	 */
	public CriteriaQuery<T> setHint(String key , Object value){
		hints.put(key, value);
		return this;
	}
	
	/**
	 * Gets the hint.
	 * @return value
	 */
	protected Map<String,Object> getHints(){
		return hints;
	}
	
	/**
	 * Adds 'is null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaQuery<T> isNull(String column){
		return addCriteria(column, Operand.IsNull,null);
	}
	
	/**
	 * Adds 'is not null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaQuery<T> isNotNull(String column){
		return addCriteria(column, Operand.IsNotNull,null);
	}
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> eq(String column, Object value){
		return addCriteria(column, Operand.Equal,value);
	}
	
	/**
	 * Adds '!='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> ne(String column, Object value){
		return addCriteria(column, Operand.NotEqual,value);
	}

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> gt(String column, Object value){
		return addCriteria(column, Operand.GreaterThan,value);
	}

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> lt(String column, Object value){
		return addCriteria(column, Operand.LessThan,value);
	}

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> gtEq(String column, Object value){
		return addCriteria(column, Operand.GreaterEqual,value);
	}

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> ltEq(String column, Object value){
		return addCriteria(column, Operand.LessEqual,value);
	}

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	public <V> CriteriaQuery<T> between(String column, V from , V to){
		List<V> values = new ArrayList<V>();
		values.add(from);
		values.add(to);
		addCriteria(column,Operand.Between,values);
		return this;
	}

	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> contains(String column, List<V> value){
		addCriteria(column,Operand.IN,value);
		return this;
	}
	
	/**
	 * Adds 'LIKE'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<T> like(String column, Object value){
		addCriteria(column,Operand.LIKE,value);
		return this;
	}
	

	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
	 * @return self
	 */
	private CriteriaQuery<T> addCriteria(String column,Operand operand,Object value) {
		criterias.add(new Criteria(column,criterias.size()+1,operand,value));
		return this;
	}

	/**
	 * Call the specified query.
	 * @return the result
	 */
	public T call(){
		return (T)doCall(criterias);
	}
	
	/**
	 * Call the specified query.
	 * @param builder the builder
	 * @return the result
	 */
	protected abstract T doCall(List<Criteria> criterias);
}
