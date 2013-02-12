/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.txscriptejb.tableentity;

import javax.persistence.EntityManager;

/**
 * Base of the rowdata gateway.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractTableEntity {

	/** the em */
	private EntityManager em;
	
	/**
	 * @param em the em to set
	 */
	public void setEntityManager(EntityManager em){
		this.em = em;
	}
	
	/**
	 * persist
	 */
	public void persist(){
		em.persist(this);
	}
	
	/**
	 * remove
	 */
	public void remove(){
		em.remove(this);
	}
}
