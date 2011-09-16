/**
 * Use is subject to license terms.
 */
package framework.core.exception.system;

/**
 * システム例外.
 *
 * @author	yoshida-n
 * @version	2010/12/30 new create
 */
public class SystemException extends RuntimeException{

	/** serialVersionUID */
	private static final long serialVersionUID = 4928387597757529973L;
	
	/** メッセージコード */
	private int messageCode = -1;
	
	/** 埋め込み */
	private Object[] args;
	
	/**
	 * @param messageCode メッセージコード
	 * @param args 引数
	 */
	public SystemException(int messageCode , Object... args){
		this.messageCode = messageCode;
		this.args = args;
	}
	
	/**
	 * @param message メッセージ
	 * @param cause 原因
	 * @param messageCode コード
	 * @param args 引数
	 */
	public SystemException(String message , Throwable cause , int messageCode , Object... args){
		super(message,cause);
		this.messageCode = messageCode;
		this.args = args;
	}
	/**
	 * @param message メッセージ
	 * @param messageCode コード
	 * @param args 引数
	 */
	public SystemException(String message , int messageCode , Object... args){
		super(message);
		this.messageCode = messageCode;
		this.args = args;
	}

	/**
	 * @param message メッセージ
	 * @param cause 原因
	 */
	public SystemException(String message , Throwable cause){
		super(message,cause);
	}
	
	/**
	 * @param message メッセージ
	 */
	public SystemException(String message){
		super(message);
	}
	
	/**
	 * @return メッセージコード
	 */
	public int getMessageCode(){
		return this.messageCode;
	}
	
	/**
	 * @return 引数
	 */
	public Object[] getArgs(){
		return this.args;
	}
}
