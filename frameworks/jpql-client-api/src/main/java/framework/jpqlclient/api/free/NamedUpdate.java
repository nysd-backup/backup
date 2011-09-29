/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import framework.sqlclient.api.free.FreeUpdate;


/**
 * The named updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NamedUpdate extends FreeUpdate{
	
	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public <T extends NamedUpdate> T setHint(String arg0 , Object arg1);

}
