/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.Update;

/**
 * The free writable updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface FreeUpdate extends Update{
	
	<T extends FreeUpdate> T setSql(String sql);

	<T extends FreeUpdate> T setSqlId(String sqlId);
	
	/**
	 * @return the current parameter
	 */
	FreeUpdateParameter getCurrentParams();
	
	/**
	 * Set the binding parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	<T extends FreeUpdate> T setParameter(String arg0 , Object arg1);
	

	/**
	 * Set the branch parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key 
	 * @param arg1 the value
	 * @return self
	 */
	<T extends FreeUpdate> T setBranchParameter(String arg0, Object arg1);

	
}
