/**
 * Use is subject to license terms.
 */
package framework.sqlengine.exception;

/**
 * SQL例外.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SQLEngineException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * @param mes メッセージ
	 */
	public SQLEngineException(String mes){
		super(mes);
	}
	
	/**
	 * @param mes メッセージ
	 * @param t 例外
	 */
	public SQLEngineException(String mes,Throwable t){
		super(mes,t);
	}
	

	/**
	 * @param t 例外
	 */
	public SQLEngineException(Throwable t){
		super(t);
	}
	
}
