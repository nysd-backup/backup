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
	default <T> TotalList<T> getTotalResult(Conditions param){
		throw new UnsupportedOperationException();
	}

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	default <T> CloseableIterator<T> getFetchResult(Conditions param){
		throw new UnsupportedOperationException();
	}

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList(Conditions param);


}