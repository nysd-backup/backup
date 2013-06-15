/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free;



/**
 * BatchModifyQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BatchModifyQuery {

	/**
	 * @param parameter the parameter to add
	 */
	public void addBatch(ModifyingConditions parameter);

	/**
	 * @param query the query holding parameter.
	 */
	public void addBatch(AbstractNativeModifyQuery query);
	
	/**
	 * Executes batch update.
	 * @return result
	 */
	public int[] modify();
}
