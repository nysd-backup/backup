/**
 * Copyright 2011 the original author
 */
package service.test;

import org.coder.alpha.message.context.RollbackTrigger;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RollbackableImpl implements RollbackTrigger{
	
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
