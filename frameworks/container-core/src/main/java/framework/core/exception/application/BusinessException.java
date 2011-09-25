/**
 * Use is subject to license terms.
 */
package framework.core.exception.application;

import java.io.Serializable;

import framework.api.dto.ReplyDto;

/**
 * 業務例外.
 * <pre>
 * EJBの場合、RuntimeExceptionは@ApplicationExceptionを設定しないとrollbackOnly=trueとなり処理継続不可能となる上にシステムエラー扱いとなる。
 * 同一トランザクション内のSessionBeanの作成すらも不可能となり、同一トランザクション内のSessionBeanを使用した是正処理が不可能となる。
 * EJBで使用する場合は必ずこのクラスを継承し@ApplicationExceptionとすること
 *</pre>
 *
 * @author	yoshida-n
 * @version	2010/12/30 new create
 */
public class BusinessException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;

	/** デフォルトメッセージ */
	private static final String DEFAULT_MESSAGE = "business error. ";
	
	/** WEB層へのリプライデータ */
	private ReplyDto reply;
	
	/**
	 * @param message メッセージ
	 * @param cause 原因
	 */
	public BusinessException(String message , Throwable cause){
		super(DEFAULT_MESSAGE+message,cause);
	}
	
	/**
	 * @param message メッセージ
	 */
	public BusinessException(String message){
		super(DEFAULT_MESSAGE+message);
	}
	
	/**
	 * @param message メッセージ
	 * @param reply リプライ
	 */
	public BusinessException(String message,Serializable reply){
		super(DEFAULT_MESSAGE+message);
		this.reply = new ReplyDto();
		this.reply.setReply(reply);
	}
	
	/**
	 * @param reply リプライ
	 */
	public BusinessException(Serializable reply){
		this("",reply);
	}

	/**
	 * @param <T> 型
	 * @return データ
	 */
	public ReplyDto getReply(){
		return reply;
	}
}
