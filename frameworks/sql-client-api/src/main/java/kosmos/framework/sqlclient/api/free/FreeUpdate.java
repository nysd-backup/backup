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
	
	/**
	 * @return the current parameter
	 */
	FreeUpdateParameter getCurrentParams();
	
	/**
	 * @param parameter the parameter to set
	 */
	void setCondition(FreeUpdateParameter parameter) ;
	
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
