/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.activation;



/**
 * Lookup by JavaEE6's unified JNDI name.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class UnifiedComponentFinderImpl extends AbstractEJBComponentFinder{

	@Override
	protected String getPrefix() {
		return "java:module/";
	}

}

