/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api;

import java.util.List;

/**
 * The base of the queries.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Query {
	
	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public <T extends Query> T setHint(String arg0 , Object arg1);

	/**
	 * @param <T> the type
	 * @param arg0 the max result
	 * @return self
	 */
	public <T extends Query> T setMaxResults(int arg0) ;
	
	/**
	 * @param <T>　the type
	 * @param arg0　the start position
	 * @return self
	 */
	public <T extends Query> T setFirstResult(int arg0) ;
	
	/**
	 * @param <T> the type
	 * @return the result
	 */
	public <T> List<T> getResultList() ;

	/**
	 * @param <T> the type
	 * @return the first result hit
	 */
	public <T> T getSingleResult();
	
	/**
	 * @return the count
	 */
	public int count();

}
