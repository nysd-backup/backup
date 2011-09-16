/**
 * Use is subject to license terms.
 */
package framework.sqlengine.exception.impl;

import framework.sqlengine.exception.ExceptionHandler;
import framework.sqlengine.exception.SQLEngineException;

/**
 * 例外ハンドラ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ExceptionHandlerImpl implements ExceptionHandler{

	/**
	 * @see framework.sqlengine.exception.ExceptionHandler#rethrow(java.lang.Exception)
	 */
	@Override
	public RuntimeException rethrow(Exception e) {
		if(e instanceof RuntimeException){
			return (RuntimeException)e;
		}else{
			return new SQLEngineException(e);
		}
	}

}
