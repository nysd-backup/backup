/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

import java.io.Serializable;
import java.util.List;

/**
 * ネイティブクエリの実行結果.
 *
 * @author yoshida-n
 * @version	created.
 */
public class NativeResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 上限達したか否か */
	private final boolean limitedOver;
	
	/** 検索結果 */
	private final List<T> resultList;
	
	/** ヒット件数 */
	private final int hitCount;
	
	/**
	 * @param limited リミットオーバ
	 * @param result 結果
	 * @param hitCount ヒット件数
	 */
	public NativeResult(boolean limitedOver , List<T> result , int hitCount){
		this.limitedOver = limitedOver;
		this.resultList = result;
		this.hitCount = hitCount;
	}
	
	/**
	 * @return true:件数超過
	 */
	public boolean isLimited(){
		return this.limitedOver;
	}
	
	/**
	 * 
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
