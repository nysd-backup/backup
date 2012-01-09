/**
 * Copyright 2011 the original author
 */
package kosmos.framework.querymodel;


/**
 * Sets the result of QueryModel.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface VisitableQueryProcessor {

	/**
	 * Updates the StrictQueryModel.
	 * 
	 * @param model the model.
	 */
	public void getResultList(StrictQueryModel model);
	
	/**
	 * Updates the StrictUpdateModel.
	 * 
	 * @param model the model to fill.
	 */
	public void update(StrictUpdateModel model);
	
	/**
	 * Updates the NativeUpdateModel.
	 * 
	 * @param model the model to fill.
	 */
	public void update(NativeUpdateModel model);
	
	/**
	 * Updates the NativeQueryModel.
	 * 
	 * @param model the model to fill.
	 */
	public void getResultList(NativeQueryModel model);
}
