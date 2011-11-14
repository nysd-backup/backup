/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

/**
 * The native updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface NativeUpdate extends FreeUpdate{
	
	/**
	 * @param <T> type
	 * @param seconds the timeout seconds
	 * @return self
	 */
	public <T extends NativeUpdate> T setQueryTimeout(int seconds);
}
