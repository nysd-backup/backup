/**
 * Copyright 2011 the original author
 */
package alpha.sqlclient.orm;


/**
 * The condition to update.
 *
 * @author yoshida-n
 * @version	created.
 */
public class CriteriaModifyQueryParameter<T> extends CriteriaQueryParameter<T>{

	/**
	 * @param entityClass the entityClass to set
	 */
	public CriteriaModifyQueryParameter(Class<T> entityClass) {
		super(entityClass);
	}

}
