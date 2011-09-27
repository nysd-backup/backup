/**
 * Copyright 2011 the original author
 */
package framework.web.core.history;

/**
 * ページ状態.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PageMement {

	/**　画面ID */
	private final String viewId;
	
	/** データ */
	private Object state;
	
	/**
	 * @param viewId 画面ID
	 * @param state データ
	 */
	public PageMement(String viewId, Object state){
		this.viewId = viewId;
		this.state = state;
	}
	
	/**
	 * @return 状態
	 */
	@SuppressWarnings("unchecked")
	public <T> Object getSavedState(){
		return (T)this.state;
	}
	
	/**
	 * @return 画面ID
	 */
	public String getViewId(){
		return this.viewId;
	}
}
