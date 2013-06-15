/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.gateway;

import java.util.List;

import org.coder.alpha.jdbc.domain.TotalList;
import org.coder.alpha.query.free.Conditions;
import org.coder.alpha.query.free.ReadingConditions;





/**
 * The internal query.
 * 
 * <pre>
 * Native and JPA is supported. 
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface PersistenceGateway {
	
	/**
	 * Updates the table.
	 * 
	 * @return the updated count
	 */
	int executeUpdate(Conditions param);
	
	/**
	 * Updates the table.
	 * 
	 * @return the each updated count
	 */
	int[] executeBatch(List<Conditions> param);

	/**
	 * @return the total result
	 */
	<T> TotalList<T> getTotalResult(ReadingConditions param);

	/**
	 * @return the result holding the <code>ResultSet</code>
	 */
	<T> List<T> getFetchResult(ReadingConditions param);

	/**
	 * @return the hit count.
	 */
	long count(ReadingConditions param);

	/**
	 * Selects the table.
	 * 
	 * @return the found data.
	 */
	<T> List<T> getResultList(ReadingConditions param);


}