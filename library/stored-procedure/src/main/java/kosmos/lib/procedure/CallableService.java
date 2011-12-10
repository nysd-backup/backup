/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure;

import java.sql.Connection;

/**
 * CallableService.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface CallableService {

	/**
	 * Invokes the StoredProcesure.
	 * 
	 * @param request the request
	 * @param connection the database connection
	 * @return the reply
	 */
	public CallResult execute(CallRequest request,Connection connection);
}
