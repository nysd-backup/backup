/**
 * Copyright 2011 the original author
 */
package framework.core.message;

import java.io.Serializable;

/**
 * メッセージ.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class MessageBean implements Serializable{

	private static final long serialVersionUID = 7686881503687250217L;

	/** メッセージコード  */
	private int code = -1;
	
	/** 詳細情報 */
	private Object[] detail	= null;
	
	/** 行番号 */
	private int rowIndex;
	
	/** オプション */
	private Object option;
	
	/**
	 * @param code　コード
	 * @param args 引数
	 */
	public MessageBean(int code , Object... args){
		this.code = code;
		this.detail = args;
	}
	
	/**
	 * get code
	 *
	 * @return code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * get detail
	 *
	 * @return detail
	 */
	public Object[] getDetail() {
		return detail;
	}

	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the rowIndex
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(Object option) {
		this.option = option;
	}

	/**
	 * @return the option
	 */
	public Object getOption() {
		return option;
	} 

	
	
}
