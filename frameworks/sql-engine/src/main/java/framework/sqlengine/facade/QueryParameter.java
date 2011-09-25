/**
 * Use is subject to license terms.
 */
package framework.sqlengine.facade;

import framework.sqlengine.executer.RecordFilter;

/**
 * 検索パラメータ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryParameter<T> extends SQLParameter{
	
	/** マックス件数 */
	private int maxSize = 0;
	
	/** 先頭位置 */
	private int firstResult = 0;
	
	/** 結果セット用 */
	private Class<T> resultType = null;
	
	/** リザルトセットフィルター */
	private RecordFilter<T> filter;	
	
	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(RecordFilter<T> filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter
	 */
	public RecordFilter<T> getFilter() {
		return filter;
	}

	/**
	 * @param resultType the resultType to set
	 */
	public void setResultType(Class<T> resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the resultType
	 */
	public Class<T> getResultType() {
		return resultType;
	}

	/**
	 * @param firstResult the firstResult to set
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	
}
