package client.sql.orm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import client.sql.free.QueryCallback;
import client.sql.orm.strategy.InternalOrmQuery;


/**
 * The Query Object.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaReadQuery<T>{
	
	/** the InternalOrmQuery */
	private final InternalOrmQuery internalQuery ;
	
	/** the condition */
	private CriteriaReadQueryParameter<T> condition = null;
	
	/**
	 * @param entityClass the entity class
	 */
	CriteriaReadQuery(Class<T> entityClass,InternalOrmQuery internalQuery,EntityManager em){
		this.condition = new CriteriaReadQueryParameter<T>(entityClass);
		this.internalQuery = internalQuery;
		this.condition.setEntityManager(em);
	}
	
	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public CriteriaReadQuery<T> setHint(String arg0 , Object arg1){
		condition.getHints().put(arg0, arg1);
		return this;
	}

	/**
	 * @param <T> the type
	 * @param arg0 the max result
	 * @return self
	 */
	public CriteriaReadQuery<T> setMaxResults(int arg0){
		condition.setMaxSize(arg0);
		return this;
	}
	
	/**
	 * @param <T>　the type
	 * @param arg0　the start position
	 * @return self
	 */
	public CriteriaReadQuery<T> setFirstResult(int arg0){
		condition.setFirstResult(arg0);
		return this;
	}
	
	/**
	 * @param <T> the type
	 * @return the result
	 */
	public List<T> getResultList() {
		return internalQuery.getResultList(condition);
	}

	/**
	 * @param <T> the type
	 * @return the first result hit
	 */
	public T getSingleResult(){
		setMaxResults(1);
		List<T> result = getResultList();
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaReadQuery<T> eq(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.Equal,value);
	}
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaReadQuery<T> gt(Metadata<T, V> column, V value){
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
	public <V> CriteriaReadQuery<T> lt(Metadata<T, V> column, V value){
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
	public <V> CriteriaReadQuery<T> gtEq(Metadata<T, V> column, V value){
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
	public <V> CriteriaReadQuery<T> ltEq(Metadata<T, V> column, V value){
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
	public <V> CriteriaReadQuery<T> between(Metadata<T, V> column, V from , V to){
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
	public <V> CriteriaReadQuery<T> contains(Metadata<T, V> column, List<V> value){
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
	public <V> CriteriaReadQuery<T> like(Metadata<T, V> column, V value){
		addCriteria(column.name(),ComparingOperand.LIKE,value);
		return this;
	}
	
	/**
	 * Adds 'DESC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaReadQuery<T> desc(Metadata<T,?> column){
		condition.getSortKeys().add(new SortKey(false,column.name()));
		return this;
	}
	
	/**
	 * Adds 'ASC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaReadQuery<T> asc(Metadata<T, ?> column){
		condition.getSortKeys().add(new SortKey(true,column.name()));
		return this;
	}
	
	/**
	 * @param type the lock mode
	 * @return self
	 */
	public CriteriaReadQuery<T> setLockMode(LockModeType type){
		condition.setLockModeType(type);
		return this;
	}
	
	/**
	 * @param callback the callback
	 * @return the result count
	 */
	public long getFetchResult(QueryCallback<T> callback){
		List<T> lazyList = getFetchResult();
		Iterator<T> iterator = lazyList.iterator();
		long count = 0;
		try{
			while(iterator.hasNext()){	
				callback.handleRow(iterator.next(), count);
				count++;
			}
		}finally{
			lazyList.clear();
		}
		return count;
	}
	
	/**
	 * @return the result
	 */
	public List<T> getFetchResult(){
		List<T> lazyList = internalQuery.getFetchResult(condition);
		return lazyList;
	}
	
	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
	 * @return
	 */
	public CriteriaReadQuery<T> addCriteria(String column, ComparingOperand operand,Object value) {
		condition.getConditions().add(new ExtractionCriteria<Object>(column,condition.getConditions().size()+1,operand,value));
		return this;
	}
	
}
