/**
 * Use is subject to license terms.
 */
package framework.api.query.orm;

import java.util.List;

/**
 * 簡易ORM更新.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface EasyUpdate<T> extends AdvancedOrmUpdate<T>{

	/**
	 * セット句
	 * @param setString 値
	 * @return self 
	 */
	public EasyUpdate<T> set(String... setString);
	
	/**
	 * 条件
	 * @param filterString フィルター
	 * @return self
	 */
	public EasyUpdate<T> filter(String filterString);
	
	/**
	 * 更新
	 * @param set set句
	 * @param params パラメータ
	 * @return 件数
	 */
	public int execute(List<Object> set , Object... params);
	
}
