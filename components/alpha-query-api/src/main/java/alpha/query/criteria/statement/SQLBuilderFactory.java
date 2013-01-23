/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria.statement;



/**
 * The JPQLBuilderFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLBuilderFactory implements StatementBuilderFactory {

	/**
	 * @see alpha.query.criteria.statement.StatementBuilderFactory#createBuilder()
	 */
	@Override
	public StatementBuilder createBuilder() {
		return new SQLBuilder();
	}
	
}