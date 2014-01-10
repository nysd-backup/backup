/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.result;

import java.util.Iterator;

/**
 * CloseableIterator.
 *
 * @author yoshida-n
 * @version	1.0
 */
public abstract class CloseableIterator<T> implements Iterator<T> ,AutoCloseable {
	
	/**
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public abstract void close();

}
