/**
 * Copyright 2011 the original author
 */
package framework.api.query.services;

import java.util.List;

import framework.core.entity.AbstractEntity;
import framework.sqlclient.api.orm.OrmCondition;

/**
 * ORMクエリ実行用サービス.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQueryService<T extends AbstractEntity> {

	/**
	 * 主キー検索
	 * @param pks　主キー
	 * @return 検索結果
	 */
	public T find(OrmCondition<T> request,Object[] pks);

	/**
	 * 候補キー検索.
	 * 複数件取得時エラー
	 * @return　検索結果
	 */
	public T findAny(OrmCondition<T> request);

	/**
	 * 任意条件検索.
	 * @return 検索結果
	 */
	public List<T> getResultList(OrmCondition<T> request);
	
	/**
	 * 先頭行検索
	 * @return 先頭行
	 */
	public T getSingleResult(OrmCondition<T> request);
	
	/**
	 * 存在チェック
	 * @return true:存在する
	 */
	public boolean exists(OrmCondition<T> request);
	
	/**
	 * 主キー指定存在チェック
	 * @param pks 主キー
	 * @return true:存在する
	 */
	public boolean exists(OrmCondition<T> request,Object[] pks);
	
	/**
	 * 候補キー指定存在チェック
	 * @return true:存在する
	 */
	public boolean existsByAny(OrmCondition<T> request);	

}
