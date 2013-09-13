/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.query;

import javax.persistence.EntityManager;

import org.coder.alpha.query.gateway.PersistenceGateway;


/**
 * The base of the updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractModifyQuery {
	
	/** the query */
	private Conditions parameter;
	
	/** the internal query */
	private PersistenceGateway persistenceGateway;
	
	/**
	 * @param em the em to set
	 */	
	public <T extends AbstractModifyQuery> T setEntityManager(EntityManager em){
		this.parameter.setEntityManager(em);
		return (T)this;
	}

	/**
	 * @return the parameter
	 */
	public Conditions getParameter(){
		return this.parameter;
	}
	
	/**
	 * Sets the wrapping clause.
	 * @param format the format string
	 */
	public <T extends AbstractModifyQuery> T setWrappingClause(String format){
		this.parameter.setWrappingClause(format);
		return (T)this;
	}
	
	/**
	 * @return the internalQuery
	 */
	protected PersistenceGateway getPersistenceGateway(){
		return this.persistenceGateway;
	}
	
	/**
	 * Constructor
	 */
	public AbstractModifyQuery(){
		parameter = new Conditions();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setPersistenceGateway(PersistenceGateway persistenceGateway){
		this.persistenceGateway = persistenceGateway;
	}
	
	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	protected AbstractModifyQuery setParameter(String arg0, Object arg1) {
		parameter.getParam().put(arg0,arg1);
		return this;
	}
	
	/**
	 * Sets the query hints.
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractModifyQuery> T setHint(String arg0, Object arg1) {
		parameter.getHints().put(arg0,arg1);
		return (T)this;
	}

	/**
	 * Updates the data.
	 * @return the updated count
	 */
	public int update(){
		return persistenceGateway.executeUpdate(parameter);
	}
	
}

