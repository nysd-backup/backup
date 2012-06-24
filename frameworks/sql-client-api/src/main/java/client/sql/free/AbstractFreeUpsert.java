/**
 * Copyright 2011 the original author
 */
package client.sql.free;

import javax.persistence.EntityManager;

import client.sql.free.strategy.InternalQuery;


/**
 * The base of the updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeUpsert {
	
	/** the query */
	private FreeUpsertParameter parameter;
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param em the em to set
	 */	
	public <T extends AbstractFreeUpsert> T setEntityManager(EntityManager em){
		this.parameter.setEntityManager(em);
		return (T)this;
	}

	/**
	 * @return the parameter
	 */
	public FreeUpsertParameter getParameter(){
		return this.parameter;
	}
	
	/**
	 * @return the internalQuery
	 */
	protected InternalQuery getInternalQuery(){
		return this.internalQuery;
	}
	
	/**
	 * Constructor
	 */
	public AbstractFreeUpsert(){
		parameter = new FreeUpsertParameter();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}
	
	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public AbstractFreeUpsert setParameter(String arg0, Object arg1) {
		parameter.getParam().put(arg0,arg1);
		return this;
	}
	
	/**
	 * Sets the branch parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public AbstractFreeUpsert setBranchParameter(String arg0, Object arg1) {
		parameter.getBranchParam().put(arg0, arg1);
		return this;
	}
	
	/**
	 * Sets the query hints.
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractFreeUpsert> T  setHint(String arg0, Object arg1) {
		parameter.getHints().put(arg0,arg1);
		return (T)this;
	}

	/**
	 * Updates the data.
	 * @return the updated count
	 */
	public int update(){
		return internalQuery.executeUpdate(parameter);
	}
	
}

