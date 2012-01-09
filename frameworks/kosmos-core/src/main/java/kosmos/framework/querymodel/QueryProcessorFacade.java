/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface QueryProcessorFacade {

	/**
	 * @param model the model
	 * @return
	 */
	public QueryModel execute(QueryModel model);

}