/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import framework.sqlclient.api.free.AbstractUpdate;

/**
 * UpdateのAPI.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNamedUpdate extends AbstractUpdate<NamedUpdate> implements NamedUpdate{
	

	/**
	 * @see framework.jpqlclient.api.free.NamedUpdate#setHint(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NamedUpdate> T setHint(String arg0, Object arg1) {
		delegate.setHint(arg0,arg1);
		return (T)this;
	}
	
}
