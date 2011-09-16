/**
 * Use is subject to license terms.
 */
package framework.web.core.history;

/**
 * 状態を生成、復元する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface StateOriginator {

	/**
	 * 現在の状態を保存したスナップショットを作成する。
	 * @return 状態
	 */	
	public PageMement save();
	
	/**
	 * スナップショットから状態を復元する。
	 * @param 状態
	 */
	public void restore(PageMement mement);
}
