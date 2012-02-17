/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.query;

/**
 * Generates the id for entity.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface IdentifierGenerator {

	/**
	 * Generates the id for entity.
	 * 
	 * @param name sequence name  
	 * @param arguments query arguments
	 * @return new id
	 */
	long generate(String name , Object... arguments);
}
