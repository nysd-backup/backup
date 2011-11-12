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
	 * Set the branch parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key 
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends FreeQuery> T setBranchParameter(String arg0 , Object arg1);

	/**
	 * Set the binding parameter.
	 * 
	 * @param <T> the type
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends FreeQuery> T setParameter(String arg0 , Object arg1);
	
}
