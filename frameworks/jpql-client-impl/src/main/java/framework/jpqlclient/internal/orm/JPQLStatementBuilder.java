/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm;

import java.util.Collection;

import framework.sqlclient.api.orm.OrmCondition;

/**
 * JPQLのSQL文を作成する.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface JPQLStatementBuilder {

	/**
	 * SELECT文の作成.
	 * @param condition 条件
	 * @return　SELECT文
	 */
	public String createSelect(OrmCondition<?> condition);
	
	
	/**
	 * DELETE文の作成.
	 * @param condition 条件
	 * @return　DELTE文
	 */
	public String createDelete(OrmCondition<?> condition);
	
	/**
	 * UPDATE文の作成.
	 * @param condition 条件
	 * @param set 更新対象
	 * @return UPDATE文
	 */
	public String createUpdate(OrmCondition<?> condition, Collection<String> set) ;
	
}
