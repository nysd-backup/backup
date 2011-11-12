/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.LockModeType;

import kosmos.framework.sqlclient.api.free.FreeQuery;



/**
 * The named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NamedQuery extends FreeQuery{
	
	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public <T extends FreeQuery> T setHint(String arg0 , Object arg1);
	
	/**
	 * Set the lock mode
	 * 
	 * @param <T> the type
	 * @param arg0 the lock mode
	 * @return self
	 */
	public <T extends NamedQuery> T setLockMode(LockModeType arg0);
	
}
