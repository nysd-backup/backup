/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.result;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * CloseableIterator.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface CloseableIterator<T> extends Iterator<T> ,AutoCloseable {
	
	/**
	 * @param action
	 */
	@Override
	default void forEachRemaining(Consumer<? super T> action){
		try{
			while(hasNext()){
				action.accept(next());
			}
		}finally {
			close();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	void close();

}
