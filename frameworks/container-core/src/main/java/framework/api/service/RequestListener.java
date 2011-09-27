/**
 * Copyright 2011 the original author
 */
package framework.api.service;

import framework.api.dto.ReplyDto;
import framework.api.dto.RequestDto;

/**
 * クライアントからサービス実行要求を受け付ける。
 * リモート呼び出しやメッセージ駆動による起動時にも使用する。
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public interface RequestListener {


	/**
	 * @param dto DTO
	 */
	public ReplyDto processService(RequestDto dto);
	
}
