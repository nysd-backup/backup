/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm;

import framework.sqlclient.api.orm.OrmCondition;


/**
 * SQLを作成する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface SQLStatementBuilder {

	/**
	 * SELECT文の作成.
	 * @param condition 条件
	 * @return　SELECT文
	 */
	public String createSelect(OrmCondition<?> condition);
	
}
