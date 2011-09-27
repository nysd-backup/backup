/**
 * Copyright 2011 the original author
 */
package framework.jdoclient.api.orm;

import javax.jdo.Extent;

import framework.sqlclient.api.orm.OrmQuery;

/**
 * JDO用ORMクエリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface JDOOrmQuery<T> extends OrmQuery<T>{

	/**
	 * filterにand条件を追加する.
	 * @return　self
	 */
	public JDOOrmQuery<T> and();
	
	/**
	 * filterにor条件を追加する.
	 * @return　self
	 */
	public JDOOrmQuery<T> or();
	
	/**
	 * @return Iteratorでフェッチしながら全件取得
	 */
	public Extent<T> getExtent();
}
