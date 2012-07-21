/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import client.sql.free.strategy.InternalQuery;



/**
 * The free writable query.
 * The base of all the query.
 *
 * @author yoshida-n
 * @version　 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeReadQuery{
	
	/** the delegate */
	private FreeReadQueryParameter condition = null;

	/** the internal query */
	private InternalQuery internalQuery = null;
	
	/**
	 * @param em the em to set
	 */
	public <T extends AbstractFreeReadQuery> T setEntityManager(EntityManager em){
		this.condition.setEntityManager(em);
		return (T)this;
	}
	
	/**
	 * @return the condition
	 */
	public FreeReadQueryParameter getParameter(){
		return this.condition;
	}
	
	/**
	 * @return the internal query
	 */
	protected InternalQuery getInternalQuery(){
		return this.internalQuery;
	}
	
	/**
	 *　Constructor 
	 */
	public AbstractFreeReadQuery(){
		condition = new FreeReadQueryParameter();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setInternalQuery(InternalQuery internalQuery ){
		this.internalQuery = internalQuery;
	}
	
	/**
	 * Gets the query result.
	 * @return the result
	 */
	public <T> List<T> getResultList() {
		return (List<T>)internalQuery.getResultList(condition);
	}
	
	/**
	 * Gets the query result.
	 * @return the first result
	 */
	public <T> T getSingleResult() {
		return (T)internalQuery.getSingleResult(condition);
	}
	
	/**
	 * Gets the hit count
	 * @return the hit count
	 */
	public long count() {
		return internalQuery.count(condition);
	}

	/**
	 * Sets the first result.
	 * @param arg0 the start position that starts with 1
	 * @return self
	 */
	public <T extends AbstractFreeReadQuery> T setFirstResult(int arg0) {
		condition.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * Sets the max results.
	 * @param arg0 the size to get
	 * @return self
	 */
	public <T extends AbstractFreeReadQuery> T setMaxResults(int arg0) {
		condition.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	protected AbstractFreeReadQuery setParameter(String arg0, Object arg1) {
		condition.getParam().put(arg0, arg1);
		return this;
	}

	/**
	 * Sets the query hints
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractFreeReadQuery> T setHint(String arg0, Object arg1) {
		condition.getHints().put(arg0,arg1);
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

