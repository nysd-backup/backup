/**
 * Copyright 2011 the original author
 */
package framework.web.core.history;

/**
 * 状態を生成、復元する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface StateOriginator {

	/**
	 * 現在の状態を保存したスナップショットを作成する。
	 * @return 状態
	 */	
	public PageMement save();
	
	/**
	 * スナップショットから状態を復元する。
	 * @param mement 状態
	 */
	public void restore(PageMement mement);
}
