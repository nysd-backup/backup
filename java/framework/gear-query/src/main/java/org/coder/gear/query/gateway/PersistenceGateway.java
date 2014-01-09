/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.gateway;

import java.util.List;

import org.coder.gear.query.free.query.Conditions;
import org.coder.gear.query.free.result.CloseableIterator;
import org.coder.gear.query.free.result.TotalList;



/**
 * The internal query.
 * 
 * <pre>
 * Native and JPA is supported. 
 * </pre>
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface PersistenceGateway {
	
	/**
	 * Updates the table.
	 * 
	 * @return the updated count
	 */
	int executeUpdate(Conditions param);

	/**
	 * @return the total result
	 */
	<T> TotalList<T> getTotalResult(Conditions param);

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> CloseableIterator<T> getFetchResult(Conditions param);

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList(Conditions param);


}