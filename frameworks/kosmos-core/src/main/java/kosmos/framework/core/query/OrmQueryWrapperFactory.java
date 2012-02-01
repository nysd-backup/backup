/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.query;


/**
 * The factory to create ORM query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface OrmQueryWrapperFactory {

	/**
	 * Creates the <code>EasyQuery</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T> EasyQuery<T> createEasyQuery(Class<T> entityClass);
	
	/**
	 * Creates the <code>LightQuery</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T> LightQuery<T> createLightQuery(Class<T> entityClass);
	
	/**
	 * Creates the <code>EasyUpdate</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T> EasyUpdate<T> createEasyUpdate(Class<T> entityClass);

	/**
	 * Creates the <code>LightUpdate</code>.
	 * 
	 * @param <T>　the type
	 * @param <Q> 　the type
	 * @param entityClass the class of target entity
	 */
	public <T> LightUpdate<T> createLightUpdate(Class<T> entityClass);

}
