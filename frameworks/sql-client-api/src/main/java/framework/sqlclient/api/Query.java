/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api;

import java.util.List;


/**
 * SQL/JPQL/CQL/ORM Query.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface Query {

	/**
	 * 検索結果0件時システムエラーとする
	 * @param <T>型
	 * @return self
	 */
	public <T extends Query> T enableNoDataError();
	
	/**
	 * @param <T> 型
	 * @param arg0 最大検索件数
	 * @return self
	 */
	public <T extends Query> T setMaxResults(int arg0) ;
	
	/**
	 * @param <T>　型
	 * @param arg0　シーク行位置
	 * @return self
	 */
	public <T extends Query> T setFirstResult(int arg0) ;
	
	/**
	 * @param <T> 型
	 * @return 検索結果
	 */
	public <T> List<T> getResultList() ;

	/**
	 * @param <T> 型
	 * @return 検索結果先頭1件
	 */
	public <T> T getSingleResult();
	
	/**
	 * @return 件数
	 */
	public int count();
	
	/**
	 * 任意条件指定存在チェック
	 * @return true:存在する
	 */
	public boolean exists();

}
