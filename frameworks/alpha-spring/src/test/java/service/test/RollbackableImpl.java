/**
 * Copyright 2011 the original author
 */
package service.test;

import org.coder.alpha.framework.transaction.Rollbackable;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RollbackableImpl implements Rollbackable{
	
	private final String source;
	
	public RollbackableImpl(String source){
		this.source = source;
	}

	@Override
	public boolean isRollbackRequired() {
		return source.toString().equals("100");
	}

	@Override
	public Object getSource() {
		return source;
	}

}