/**
 * Copyright 2011 the original author
 */
package framework.sqlclient.internal;

import framework.sqlclient.api.free.NativeQuery;

/**
 *　NativeQueryの実行エンジン
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractLocalNativeQueryEngine<T extends AbstractInternalQuery> extends AbstractLocalQueryEngine<T> implements NativeQuery{

	/**
	 * @param delegate　delegate
	 */
	public AbstractLocalNativeQueryEngine(T delegate){
		super(delegate);
	}
	
}
