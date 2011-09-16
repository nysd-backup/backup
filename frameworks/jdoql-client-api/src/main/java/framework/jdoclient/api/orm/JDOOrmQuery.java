/**
 * Use is subject to license terms.
 */
package framework.jdoclient.api.orm;

import javax.jdo.Extent;

import framework.sqlclient.api.orm.OrmQuery;

/**
 * JDO用ORMクエリ.
 *
 * @author yoshida-n
 * @version	created.
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
