/**
 * Use is subject to license terms.
 */
package framework.sqlengine.facade;

import java.util.List;

/**
 * 実行結果.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryResult<T> {

	/** 上限値超過有無 */
	private final boolean limited;
	
	/** 結果 */
	private final List<T> resultList;
	
	/** ヒット件数 */
	private final int hitCount;

	/**
	 * @param limited 上限値超過有無
	 * @param result 検索結果
	 * @param hitCount ヒット件数
	 */
	public QueryResult(boolean limited ,List<T> result , int hitCount){
		this.limited = limited;
		this.resultList = result;
		this.hitCount = hitCount;
	}
	
	/**
	 * @return true:上限超過
	 */
	public boolean isLimited(){
		return this.limited;
	}
	
	/**
	 * @return 検索結果
	 */
	public List<T> getResultList(){
		return this.resultList;
	}
	
	/**
	 * @return ヒット件数
	 */
	public int getHitCount(){
		return this.hitCount;
	}
}
