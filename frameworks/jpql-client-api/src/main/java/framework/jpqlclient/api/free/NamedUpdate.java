/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import framework.sqlclient.api.free.FreeUpdate;


/**
 * NamedUpdateのAPI.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NamedUpdate extends FreeUpdate{
	
	/**
	 * JPAHint.
	 * @param <T> 型
	 * @param arg0 キー
	 * @param arg1 値
	 * @return self
	 */
	public <T extends NamedUpdate> T setHint(String arg0 , Object arg1);

}
