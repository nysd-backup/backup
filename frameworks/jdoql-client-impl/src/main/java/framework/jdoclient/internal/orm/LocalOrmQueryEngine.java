/**
 * Use is subject to license terms.
 */
package framework.jdoclient.internal.orm;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import framework.jdoclient.api.orm.JDOOrmQuery;
import framework.jdoclient.internal.ClosableExtent;
import framework.jdoclient.internal.JdoWhereOperand;
import framework.sqlclient.api.EmptyHandler;
import framework.sqlclient.api.MultiResultHandler;
import framework.sqlclient.api.Query;
import framework.sqlclient.api.orm.OrmQuery;

/**
 * ORMクエリのエンジン.
 *
 * @author yoshida-n
 * @version	created.
 */
@SuppressWarnings("unchecked")
public class LocalOrmQueryEngine<T> implements JDOOrmQuery<T>{

	private final Class<T> resultClass;
	
	private final PersistenceManager pm;

	private final MultiResultHandler mrh;

	private final EmptyHandler eh;
	
	private int parameterCount = 0;
	
	private StringBuilder filterString = new StringBuilder();
	
	private StringBuilder orderString = new StringBuilder();
	
	private List<Object> params = new ArrayList<Object>();
	
	private boolean noDataErrorEnabled = false;
	
	private int maxResults = 0;
	
	private int firstResult = 0;
		
	/**
	 * @param entityClass エンティティクラス
	 * @param pm パーシステンスマネージャ
	 * @param mrh マルチリザルトハンドラ
	 * @param eh 0件ハンドラ
	 */
	public LocalOrmQueryEngine(Class<T> entityClass,PersistenceManager pm,MultiResultHandler mrh , EmptyHandler eh){
		this.resultClass = entityClass;
		this.pm = pm;
		this.mrh = mrh;
		this.eh = eh;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#eq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> eq(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.Equal);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gt(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.GreaterThan);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#lt(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> lt(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.LessThan);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#gtEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> gtEq(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.GreaterEqual);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#ltEq(java.lang.String, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> ltEq(String column, Object value) {
		return setOperand(column, value, JdoWhereOperand.LessEqual);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#between(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public OrmQuery<T> between(String column, Object from, Object to) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#contains(java.lang.String, java.util.List)
	 */
	@Override
	public JDOOrmQuery<T> contains(String column, List<?> value) {
		parameterCount++;
		filterString.append(String.format(" %s.contains(:%s_%d) ",column,column,parameterCount));
		params.add(value);
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#asc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> asc(String column) {
		this.orderString.append(column).append(" asc ");
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#desc(java.lang.String)
	 */
	@Override
	public OrmQuery<T> desc(String column) {
		this.orderString.append(column).append(" desc ");
		return this;
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#find(java.lang.Object[])
	 */
	@Override
	public T find(Object... pks) {
		if(pks.length != 1){
			throw new IllegalArgumentException("count of pk must be one");
		}
		return pm.getObjectById(resultClass, pks[0]);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findWithLockNoWait(java.lang.Object[])
	 */
	@Override
	public T findWithLockNoWait(Object... pks) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny()
	 */
	@Override
	public T findAny() {
		List<T> result = getResultList();
		if(result.size() > 2){
			mrh.handleResult(resultClass);
		}
		return result.get(0);
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#exists(java.lang.Object[])
	 */
	@Override
	public boolean exists(Object... pks) {
		return find(pks) != null;			
	}

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#existsByAny()
	 */
	@Override
	public boolean existsByAny() {
		return findAny() != null;
	}

	/**
	 * @see framework.sqlclient.api.Query#enableNoDataError()
	 */
	
	@Override
	public <Q extends Query> Q enableNoDataError() {
		this.noDataErrorEnabled = true;
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <Q extends Query> Q setMaxResults(int arg0) {
		this.maxResults = arg0;
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <Q extends Query> Q setFirstResult(int arg0) {
		this.firstResult = arg0;
		return (Q)this;
	}

	/**
	 * @see framework.sqlclient.api.Query#getResultList()
	 */
	@Override
	public List<T> getResultList() {
		return list();
	}

	/**
	 * @see framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public T getSingleResult() {
		return single();
	}

	/**
	 * @see framework.sqlclient.api.Query#count()
	 */
	@Override
	public int count() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.api.Query#exists()
	 */
	@Override
	public boolean exists() {
		return getSingleResult() != null;
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#and()
	 */
	@Override
	public JDOOrmQuery<T> and() {
		filterString.append(" && ");
		return this;
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#or()
	 */
	@Override
	public JDOOrmQuery<T> or() {
		filterString.append(" || ");
		return this;
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#getExtent()
	 */
	@Override
	public Extent<T> getExtent() {
		Extent<T> e = pm.getExtent(resultClass);
		return new ClosableExtent<T>(e);
	}

	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#filter(java.lang.String)
	 */
	@Override
	public JDOOrmQuery<T> filter(String filterString) {
		this.filterString = new StringBuilder(filterString);
		return this;
	}
	
	/**
	 * @see framework.jdoclient.api.orm.JDOOrmQuery#order(java.lang.String)
	 */
	@Override
	public JDOOrmQuery<T> order(String orderString) {
		this. orderString = new StringBuilder( orderString);
		return this;
	}
	
	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#single(java.lang.Object[])
	 */
	@Override
	public T single(Object... params){
		setMaxResults(1);
		return list(params).get(0);
	}
	

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#list(java.lang.Object[])
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
		if(result.isEmpty()){
			if(noDataErrorEnabled){
				eh.handleEmptyResult(resultClass);
			}
			return new ArrayList<T>();
		}
		return result;
	}
	

	/**
	 * @see framework.sqlclient.api.orm.OrmQuery#findAny(java.lang.Object[])
	 */
	@Override
	public T findAny(Object... params) {
		List<T> list = list(params);
		if(list.size() > 2){
			mrh.handleResult(resultClass);
		}
		return list.get(0);
	}

	
	/**
	 * @param column カラム
	 * @param value 値
	 * @param operand 演算子
	 * @return
	 */
	private OrmQuery<T> setOperand(String column, Object value,JdoWhereOperand operand) {
		parameterCount++;
		filterString.append(String.format(" %s %s :%s_%d ",column,operand.name(),column,parameterCount));
		params.add(value);
		return this;
	}
	
	/**
	 * @return クエリ
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

}
