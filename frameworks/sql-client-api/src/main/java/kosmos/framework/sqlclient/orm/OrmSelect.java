package kosmos.framework.sqlclient.orm;

import java.util.Iterator;
import java.util.List;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.free.QueryCallback;
import kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery;


/**
 * The ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmSelect<T>{
	
	/** the InternalOrmQuery */
	private final InternalOrmQuery internalQuery ;
	
	/** the condition */
	private OrmSelectParameter<T> condition = null;
	
	/**
	 * @param entityClass the entity class
	 */
	OrmSelect(Class<T> entityClass,InternalOrmQuery internalQuery){
		this.condition = new OrmSelectParameter<T>(entityClass);
		this.internalQuery = internalQuery;
	}
	
	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public OrmSelect<T> setHint(String arg0 , Object arg1){
		condition.getHints().put(arg0, arg1);
		return this;
	}

	/**
	 * @param <T> the type
	 * @param arg0 the max result
	 * @return self
	 */
	public OrmSelect<T> setMaxResults(int arg0){
		condition.setMaxSize(arg0);
		return this;
	}
	
	/**
	 * @param <T>　the type
	 * @param arg0　the start position
	 * @return self
	 */
	public OrmSelect<T> setFirstResult(int arg0){
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
	public <V> OrmSelect<T> eq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.Equal);
	}
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> eqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.Equal);
	}

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> gt(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.GreaterThan);
	}
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> gtFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.GreaterThan);
	}
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> lt(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.LessThan);
	}
	
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> ltFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.LessThan);
	}
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> gtEq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.GreaterEqual);
	}
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> gtEqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.GreaterEqual);
	}
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> ltEq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.LessEqual);
	}
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmSelect<T> ltEqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.LessEqual);
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
	public <V> OrmSelect<T> between(Metadata<T, V> column, V from , V to){
		condition.getConditions().add(new WhereCondition(column.name(),condition.getConditions().size()+1,WhereOperand.Between,from,to));
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
	public <V> OrmSelect<T> contains(Metadata<T, V> column, List<V> value){
		condition.getConditions().add(new WhereCondition(column.name(),condition.getConditions().size()+1,WhereOperand.IN,value));
		return this;
	}
	
	/**
	 * Adds 'DESC'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @return self
	 */
	public OrmSelect<T> desc(Metadata<T,?> column){
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
	public OrmSelect<T> asc(Metadata<T, ?> column){
		condition.getSortKeys().add(new SortKey(true,column.name()));
		return this;
	}
	
	/**
	 * Finds the result by primary keys.
	 * Throws the error if the multiple result is found.
	 * 
	 * @param poks the primary keys
	 * @return the result
	 */
	public T find(Object... pks){
		return internalQuery.find(condition,pks);
	}
	
	/**
	 * @param type the lock mode
	 * @return self
	 */
	public OrmSelect<T> setLockMode(LockModeType type){
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
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
	 * @return
	 */
	private OrmSelect<T> setOperand(String column, Object value,WhereOperand operand) {
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,operand,value));
		return this;
	}
	
}
