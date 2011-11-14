/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal;

import kosmos.framework.sqlclient.api.free.NativeQuery;

/**
 *　The native query engine.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractLocalNativeQueryEngine<T extends AbstractInternalQuery> extends AbstractLocalQueryEngine<T> implements NativeQuery{

	/**
	 * @param delegate　the delegate
	 */
	public AbstractLocalNativeQueryEngine(T delegate){
		super(delegate);
	}

}
