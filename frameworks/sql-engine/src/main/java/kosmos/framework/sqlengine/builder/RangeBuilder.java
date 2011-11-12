/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.builder;

import java.util.List;

/**
 * Sets the range of query.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface RangeBuilder {

	/**
	 * @param sql the sql
	 * @param firstResult the firstResult
	 * @param getSize the gettable size
	 * @param bindList the bindList
	 * @return the SQL
	 */
	public String setRange(String sql , int firstResult , int getSize, List<Object> bindList);
}
