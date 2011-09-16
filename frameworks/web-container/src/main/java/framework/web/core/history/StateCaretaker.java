package framework.web.core.history;

import java.util.Iterator;

/**
 * 画面状態履歴を保持する.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface StateCaretaker {

	/**
	 * @param viewId 画面ID
	 * @return true:存在している
	 */
	public abstract boolean exists(String viewId);

	/**
	 * @param mement 追加する履歴
	 */
	public abstract void push( PageMement mement);

	/**
	 * 取得した履歴はこのオブジェクトから取り除かれない.
	 * 
	 * @return 一番最後に追加した履歴
	 */
	public abstract PageMement peek();

	/**	
	 * 取得した履歴はこのオブジェクトから取り除かれる.
	 * 
	 * @return 一番最後に追加した履歴
	 */
	public abstract PageMement pop();

	/**
	 * @return 古い履歴から新しい順に履歴を返すイテレータ
	 */
	public abstract Iterator<PageMement> iterator();

	/**
	 * @return 保持している履歴数
	 */
	public abstract int size();

	/**
	 * 履歴を消去する.
	 */
	public abstract void clear();

}
