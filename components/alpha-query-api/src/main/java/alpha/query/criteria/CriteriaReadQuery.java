package alpha.query.criteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import alpha.query.criteria.statement.StatementBuilderFactory;
import alpha.query.free.QueryCallback;
import alpha.query.free.ResultSetFilter;
import alpha.query.free.gateway.PersistenceGateway;



/**
 * The Query Object.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaReadQuery<T>{

	/** the StatementBuilderFactory */
	private final StatementBuilderFactory builderFactory;
	
	/** the internalQuery */
	private final PersistenceGateway persistenceGateway;
	
	/** the condition */
	private ReadQueryBuilder<T> builder = null;
	
	/**
	 * @param entityClass the entity class
	 */
	protected CriteriaReadQuery(Class<T> entityClass,PersistenceGateway persistenceGateway,EntityManager em, StatementBuilderFactory builderFactory){
		this.builder = new ReadQueryBuilder<T>(entityClass);
		this.builderFactory = builderFactory;
		this.persistenceGateway = persistenceGateway;
		this.builder.setEntityManager(em);
	}
	
	/**
	 * Sets the filter 
	 * @param filter filter 
	 * @return self
	 */
	public CriteriaReadQuery<T> setFilter(ResultSetFilter filter){
		builder.setFilter(filter);
		return this;
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
		builder.getHints().put(arg0, arg1);
		return this;
	}

	/**
	 * @param <T> the type
	 * @param arg0 the max result
	 * @return self
	 */
	public CriteriaReadQuery<T> setMaxResults(int arg0){
		builder.setMaxSize(arg0);
		return this;
	}
	
	/**
	 * @param <T>　the type
	 * @param arg0　the start position
	 * @return self
	 */
	public CriteriaReadQuery<T> setFirstResult(int arg0){
		builder.setFirstResult(arg0);
		return this;
	}
	
	/**
	 * @param <T> the type
	 * @return the result
	 */
	public List<T> getResultList() {
		return persistenceGateway.getResultList(builder.buildSelect(builderFactory.createBuilder()));
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
	 * Adds 'is null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaReadQuery<T> isNull(Metadata<T, ?> column){
		return addCriteria(column.name(), ComparingOperand.IsNull,null);
	}
	
	/**
	 * Adds 'is not null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaReadQuery<T> isNotNull(Metadata<T, ?> column){
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
	public <V> CriteriaReadQuery<T> eq(Metadata<T, V> column, V value){
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
	public <V> CriteriaReadQuery<T> ne(Metadata<T, V> column, V value){
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
		builder.getSortKeys().add(new SortKey(false,column.name()));
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
		builder.getSortKeys().add(new SortKey(true,column.name()));
		return this;
	}
	
	/**
	 * @param type the lock mode
	 * @return self
	 */
	public CriteriaReadQuery<T> setLockMode(LockModeType type){
		builder.setLockModeType(type);
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
		List<T> lazyList = persistenceGateway.getFetchResult(builder.buildSelect(builderFactory.createBuilder()));
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
		builder.getConditions().add(new Criteria<Object>(column,builder.getConditions().size()+1,operand,value));
		return this;
	}
	
}
