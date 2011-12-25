/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jdoclient.internal.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import kosmos.framework.jdoclient.api.orm.JDOOrmQuery;
import kosmos.framework.jdoclient.internal.ClosableExtent;
import kosmos.framework.jdoclient.internal.JdoWhereOperand;
import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.orm.OrmQueryContext;
import kosmos.framework.sqlclient.api.orm.OrmQuery;


/**
 * The local engine of JDO.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public class LocalOrmQueryEngine<T> implements JDOOrmQuery<T>{

	/** the result type */
	private final Class<T> resultClass;
	
	/** the persistence manager */
	private final PersistenceManager pm;
	
	/** the parameter count */
	private int parameterCount = 0;
	
	/** the filter to search */
	private StringBuilder filterString = new StringBuilder();
	
	/** the order string to sort */
	private StringBuilder orderString = new StringBuilder();
	
	/** the parameters */
	private List<Object> params = new ArrayList<Object>();
	
	/** the max size to search */
	private int maxResults = 0;
	
	/** the start position */
	private int firstResult = 0;
	
	/** the hint */
	private Map<String,Object> hints = new HashMap<String,Object>();
		
	/**
	 * @param entityClass the entityClass
	 * @param pm the pm
	 */
	public LocalOrmQueryEngine(Class<T> entityClass,PersistenceManager pm){
		this.resultClass = entityClass;
		this.pm = pm;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> eq(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.Equal);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gt(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.GreaterThan);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> lt(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.LessThan);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gtEq(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.GreaterEqual);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> ltEq(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.LessEqual);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> between(String column, Object from, Object to) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.jdoclient.api.orm.JDOOrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public JDOOrmQuery<T> contains(String column, List<?> value) {
		parameterCount++;
		filterString.append(String.format(" %s.contains(:%s_%d) ",column,column,parameterCount));
		params.add(value);
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> asc(String column) {
		this.orderString.append(column).append(" asc ");
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> desc(String column) {
		this.orderString.append(column).append(" desc ");
		return this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks) {
		if(pks.length != 1){
			throw new IllegalArgumentException("count of pk must be one");
		}
		return pm.getObjectById(resultClass, pks[0]);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <Q extends Query> Q setMaxResults(int arg0) {
		this.maxResults = arg0;
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <Q extends Query> Q setFirstResult(int arg0) {
		this.firstResult = arg0;
		return (Q)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<T> getResultList() {
		return list();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public T getSingleResult() {
		return single();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.jdoclient.api.orm.JDOOrmQuery#and()
	 */
	@Override
	public JDOOrmQuery<T> and() {
		filterString.append(" && ");
		return this;
	}

	/**
	 * @see kosmos.framework.jdoclient.api.orm.JDOOrmQuery#or()
	 */
	@Override
	public JDOOrmQuery<T> or() {
		filterString.append(" || ");
		return this;
	}

	/**
	 * @see kosmos.framework.jdoclient.api.orm.JDOOrmQuery#getExtent()
	 */
	@Override
	public Extent<T> getExtent() {
		Extent<T> e = pm.getExtent(resultClass);
		return new ClosableExtent<T>(e);
	}

	/**
	 * @see kosmos.framework.jdoclient.api.orm.JDOOrmQuery#filter(java.lang.String)
	 */
	@Override
	public JDOOrmQuery<T> filter(String filterString) {
		this.filterString = new StringBuilder(filterString);
		return this;
	}
	
	/**
	 * @see kosmos.framework.jdoclient.api.orm.JDOOrmQuery#order(java.lang.String)
	 */
	@Override
	public JDOOrmQuery<T> order(String orderString) {
		this. orderString = new StringBuilder( orderString);
		return this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params){
		setMaxResults(1);
		return list(params).get(0);
	}
	

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
	 */
	@Override
	public List<T> list(Object... params){
		javax.jdo.Query query = createQuery();
		List<T> result = null;
		if(params.length == 0){
			result = (List<T>)query.execute();
		}else{
			result = (List<T>)query.executeWithArray(params);
		}
		return result;
	}
	
	/**
	 * @param column the column
	 * @param value the value
	 * @param operand the operand
	 * @return self
	 */
	private OrmQuery<T> setOperand(String column, Object value,JdoWhereOperand operand) {
		parameterCount++;
		filterString.append(String.format(" %s %s :%s_%d ",column,operand.name(),column,parameterCount));
		params.add(value);
		return this;
	}
	
	/**
	 * @return the query
	 */
	private javax.jdo.Query createQuery(){
		javax.jdo.Query query = pm.newQuery(resultClass);
		if(filterString.length() > 0 ){
			query.setFilter(filterString.toString());
		}
		if(orderString.length() > 0){
			query.setOrdering(orderString.toString());
		}
		if(firstResult > maxResults){
			throw new IllegalArgumentException("maxResults must be greater or equal to firstResult");
		}
		if(maxResults > 0 ){
			query.setRange(firstResult,maxResults);
		}
		return query;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setCondition(kosmos.framework.sqlclient.api.orm.OrmQueryContext)
	 */
	@Override
	public OrmQuery<T> setCondition(OrmQueryContext<T> condition) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.sqlclient.api.orm.OrmQuery#setHint(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> setHint(String key, Object value) {
		hints.put(key, value);
		return this;
	}

}
