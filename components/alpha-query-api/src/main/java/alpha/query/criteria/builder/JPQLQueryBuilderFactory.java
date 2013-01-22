/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.builder;



/**
 * The JPQLQueryBuilderFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JPQLQueryBuilderFactory implements QueryBuilderFactory {

	/**
	 * @see alpha.query.criteria.builder.QueryBuilderFactory#createBuilder()
	 */
	@Override
	public QueryBuilder createBuilder() {
		return new JPQLQueryBuilder();
	}
	

}