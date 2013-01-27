/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.criteria.query;

import java.util.ArrayList;
import java.util.List;

import org.coder.alpha.query.criteria.ComparingOperand;
import org.coder.alpha.query.criteria.Criteria;
import org.coder.alpha.query.criteria.Metadata;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class CriteriaQuery<E,T> {
	
	/** the criteria */
	private List<Criteria> criterias = new ArrayList<Criteria>();

	/**
	 * Adds 'is null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaQuery<E,T> isNull(Metadata<E, ?> column){
		return addCriteria(column.name(), ComparingOperand.IsNull,null);
	}
	
	/**
	 * Adds 'is not null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaQuery<E,T> isNotNull(Metadata<E, ?> column){
		return addCriteria(column.name(), ComparingOperand.IsNotNull,null);
	}
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<E,T> eq(Metadata<E, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.Equal,value);
	}
	
	/**
	 * Adds '!='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<E,T> ne(Metadata<E, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.NotEqual,value);
	}

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<E,T> gt(Metadata<E, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.GreaterThan,value);
	}

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<E,T> lt(Metadata<E, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.LessThan,value);
	}

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<E,T> gtEq(Metadata<E, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.GreaterEqual,value);
	}

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaQuery<E,T> ltEq(Metadata<E, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.LessEqual,value);
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
	public <V> CriteriaQuery<E,T> between(Metadata<E, V> column, V from , V to){
		List<V> values = new ArrayList<V>();
		values.add(from);
		values.add(to);
		addCriteria(column.name(),ComparingOperand.Between,values);
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
	public <V> CriteriaQuery<E,T> contains(Metadata<E, V> column, List<V> value){
		addCriteria(column.name(),ComparingOperand.IN,value);
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
	public <V> CriteriaQuery<E,T> like(Metadata<E, V> column, V value){
		addCriteria(column.name(),ComparingOperand.LIKE,value);
		return this;
	}
	

	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
	 * @return self
	 */
	public CriteriaQuery<E,T> addCriteria(String column,ComparingOperand operand,Object value) {
		criterias.add(new Criteria(column,criterias.size()+1,operand,value));
		return this;
	}

	/**
	 * Call the specified query.
	 * @return the result
	 */
	public T call(){
		return doCall(criterias);
	}

	/**
	 * Call the specified query.
	 * @param builder the builder
	 * @return the result
	 */
	protected abstract T doCall(List<Criteria> criterias);
}
