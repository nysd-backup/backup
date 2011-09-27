/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.orm;

import java.util.List;
import java.util.Map;

import framework.jpqlclient.api.orm.JPAOrmCondition;

/**
 * データアクセスオブジェクト
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface GenericDao {
	
	/**
	 *　UPDATE文.
	 * @param condition 更新条件
	 * @param set 更新値
	 */
	public abstract int updateAny(JPAOrmCondition<?> condition , Map<String,Object> set);
	
	/** 
	 * DELETE文.
	 * @param condition 更新条件
	 */
	public abstract int deleteAny(JPAOrmCondition<?> condition);
	
	/**
	 * 主キー検索
	 * @param <E> 型
	 * @param entity 対象エンティティ
	 * @param pks 主キー
	 * @return 検索結果
 	 */
	public <E> E find(JPAOrmCondition<E> entity , Object... pks);
	
	/**
	 * 検索
	 * @param <E> 型 
	 * @param entity 検索条件
	 * @return 検索結果
	 */
 	public <E> List<E> getResultList(JPAOrmCondition<E> entity);
 	
	/**
	 * 候補キー検索.
	 * 複数件取得時は即時システムエラー
	 * 
	 * @param <E> 型 
	 * @param entity　検索条件
	 * @return 検索結果
	 */
	public <E> E findAny(JPAOrmCondition<E> entity);
	
}
