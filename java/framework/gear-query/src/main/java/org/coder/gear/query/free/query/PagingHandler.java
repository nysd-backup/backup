package org.coder.gear.query.free.query;

import org.coder.gear.query.free.result.TotalList;

/**
 * Handler .
 *
 * @author yoshida-n
 * @version	1.0
 */
public class PagingHandler {

	/** 再実行開始位置 */
	private int newStart = 0;
	
	/**
	 * 再実行有無
	 * @param conditions 条件
	 * @param result 結果
	 * @return 再実行有無
	 */
	public boolean shouldRetry(Conditions conditions , TotalList<?> result) {
		boolean shouldRetry = result.getHitCount() > 0 && result.isEmpty() && conditions.getFirstResult() > 1;
		if(shouldRetry){
			newStart = conditions.getFirstResult() -conditions.getMaxResults();
		}
		return shouldRetry;
	}
	
	/**
	 * @return 再実行開始位置
	 */
	public int getNewStart(){
		return newStart;
	}
}
