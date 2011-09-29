/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.api.free;

import framework.sqlclient.api.Update;

/**
 * The free writable updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface FreeUpdate extends Update{
	
	/**
	 * Set the binding parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends FreeUpdate> T setParameter(String arg0 , Object arg1);
	

	/**
	 * Set the branch parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key 
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1);

	
}
