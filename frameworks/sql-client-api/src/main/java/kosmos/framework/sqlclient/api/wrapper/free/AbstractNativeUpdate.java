/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.wrapper.free;

import kosmos.framework.sqlclient.api.free.NativeUpdate;



/**
 * The native updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractNativeUpdate extends AbstractFreeUpdate<NativeUpdate>{
	
	/**
	 * @return the delegating query
	 */
	public NativeUpdate unwrap(){
		return delegate;
	}
}

