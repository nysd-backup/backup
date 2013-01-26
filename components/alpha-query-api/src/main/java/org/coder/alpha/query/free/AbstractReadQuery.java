/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.coder.alpha.query.free.gateway.PersistenceGateway;





/**
 * The free writable query.
 * The base of all the query.
 *
 * @author yoshida-n
 * @version　 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractReadQuery{
	
	/** the delegate */
	private ReadingConditions condition = null;

	/** the internal query */
	private PersistenceGateway persistenceGateway = null;
	
	/**
	 * @param em the em to set
	 */
	public <T extends AbstractReadQuery> T setEntityManager(EntityManager em){
		this.condition.setEntityManager(em);
		return (T)this;
	}
	
	/**
	 * @return the condition
	 */
	public ReadingConditions getParameter(){
		return this.condition;
	}
	
	/**
	 * @return the internal query
	 */
	protected PersistenceGateway getPersistenceGateway(){
		return this.persistenceGateway;
	}
	
	/**
	 *　Constructor 
	 */
	public AbstractReadQuery(){
		condition = new ReadingConditions();
	}
	
	/**
	 * @param persistenceGateway the persistenceGateway to set
	 */
	public void setPersistenceGateway(PersistenceGateway persistenceGateway ){
		this.persistenceGateway = persistenceGateway;
	}
	
	/**
	 * Gets the query result.
	 * @return the result
	 */
	public <T> List<T> getResultList() {
		return (List<T>)persistenceGateway.getResultList(condition);
	}
	
	/**
	 * Gets the query result.
	 * @return the first result
	 */
	public <T> T getSingleResult() {
		setMaxResults(1);
		List<T> result = getResultList();
		return result == null ? null : result.get(0);
	}
	
	/**
	 * Sets the wrapping clause
	 * @param format the caluse
	 * @return self
	 */
	public <T extends AbstractReadQuery> T setWrappingClause(String format){
		condition.setWrappingClause(format);
		return (T)this;
	}
	
	/**
	 * Gets the hit count
	 * @return the hit count
	 */
	public long count() {
		condition.setWrappingClause("select count(*) from (%s)");
		return persistenceGateway.count(condition);
	}

	/**
	 * Sets the first result.
	 * @param arg0 the start position that starts with 1
	 * @return self
	 */
	public <T extends AbstractReadQuery> T setFirstResult(int arg0) {
		condition.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * Sets the max results.
	 * @param arg0 the size to get
	 * @return self
	 */
	public <T extends AbstractReadQuery> T setMaxResults(int arg0) {
		condition.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	protected AbstractReadQuery setParameter(String arg0, Object arg1) {
		condition.getParam().put(arg0, arg1);
		return this;
	}

	/**
	 * Sets the query hints
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractReadQuery> T setHint(String arg0, Object arg1) {
		condition.getHints().put(arg0,arg1);
		return (T)this;
	}
	
	/**
	 * Sets the lock mode
	 * @param arg0 the lockmode
	 * @return self
	 */
	public <T extends AbstractReadQuery> T setLockMode(LockModeType arg0) {
		condition.setLockMode(arg0);
		return (T)this;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}

