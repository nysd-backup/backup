/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.builder;



/**
 * Internal Query Object.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryBuilderFactory {

	/**
	 *　Updates the table.
	 *
	 * @param parameter the parameter
	 * @param set the updating target
	 * @return the updated count
	 */
	public QueryBuilder createBuilder();

}
