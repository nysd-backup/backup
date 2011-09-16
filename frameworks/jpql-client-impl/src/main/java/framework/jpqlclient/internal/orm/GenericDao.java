/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal.orm;

import java.util.List;
import java.util.Map;

import framework.jpqlclient.api.orm.JPAOrmCondition;

/**
 * データアクセスオブジェクト
 *
 * @author	yoshida-n
 * @version	created.
 */
public interface GenericDao {
	
	/**
	 *　UPDATE文.
	 * @param <T>　型
	 * @param condition 更新条件
	 * @param set 更新値
	 */
	public abstract int updateAny(JPAOrmCondition<?> condition , Map<String,Object> set);
	
	/** 
	 * DELETE文.
	 * @param <T>　型
	 * @param condition 更新条件
	 */
	public abstract int deleteAny(JPAOrmCondition<?> condition);
	
	/**
	 * 主キー検索
	 * @param 型
	 * @param entity 対象エンティティ
	 * @param pks 主キー
	 * @return 検索結果
 	 */
	public <E> E find(JPAOrmCondition<E> entity , Object... pks);

	/**
	 *　FOR UPDATE NOWAITを使用して主キー検索する
	 * @param 型 
	 * @param entity エンティティ
	 * @return　検索結果
	 */
	public <E> E findWithLock(JPAOrmCondition<E> entity, int timeout, Object... pks);
	
	/**
	 * 検索
	 * @param 型
	 * @param entity 検索条件
	 * @return 検索結果
	 */
 	public <E> List<E> getResultList(JPAOrmCondition<E> entity);
 	
	/**
	 * 候補キー検索.
	 * 複数件取得時は即時システムエラー
	 * 
	 * @param 型
	 * @param entity　検索条件
	 * @return 検索結果
	 */
	public <E> E findAny(JPAOrmCondition<E> entity);
	
}
