/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.orm;


/**
 * The condition to update.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OrmUpsertParameter<T> extends OrmQueryParameter<T>{

	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass the entityClass to set
	 */
	public OrmUpsertParameter(Class<T> entityClass) {
		super(entityClass);
	}

}
