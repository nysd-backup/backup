/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.Query;

/**
 * The free writable query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface FreeQuery extends Query{
	
	/**
	 * @return the current parameter
	 */
	FreeQueryParameter getCurrentParams(); 
	
	/**
	 * @param parameter the parameter to set
	 */
	void setCondition(FreeQueryParameter parameter); 
	
	/**
	 * Set the branch parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key 
	 * @param arg1 the value
	 * @return self
	 */
	<T extends FreeQuery> T setBranchParameter(String arg0 , Object arg1);

	/**
	 * Set the binding parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	<T extends FreeQuery> T setParameter(String arg0 , Object arg1);
	
}
