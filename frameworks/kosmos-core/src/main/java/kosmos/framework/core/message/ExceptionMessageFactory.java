/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.message;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface ExceptionMessageFactory {

	/**
	 * @param t
	 * @return
	 */
	MessageBean getMessageBeanFrom(Throwable t);
	
	
	/**
	 * @return
	 */
	MessageBean getOtherErrorBean();
}
