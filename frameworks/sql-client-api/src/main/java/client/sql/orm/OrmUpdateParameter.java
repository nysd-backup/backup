/**
 * Copyright 2011 the original author
 */
package client.sql.orm;


/**
 * The condition to update.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrmUpdateParameter<T> extends OrmQueryParameter<T>{

	/**
	 * @param entityClass the entityClass to set
	 */
	public OrmUpdateParameter(Class<T> entityClass) {
		super(entityClass);
	}

}
