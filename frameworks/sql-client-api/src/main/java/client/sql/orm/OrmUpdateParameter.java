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

	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass the entityClass to set
	 */
	public OrmUpdateParameter(Class<T> entityClass) {
		super(entityClass);
	}

}
