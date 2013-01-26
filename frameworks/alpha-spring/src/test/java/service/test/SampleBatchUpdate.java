/**
 * Copyright 2011 the original author
 */
package service.test;

import org.coder.alpha.query.free.AbstractNativeModifyQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SampleBatchUpdate extends AbstractNativeModifyQuery{
	
	public SampleBatchUpdate(){
		getParameter().setSql("@SAMPLE_BATCH_UPDATE.sql");
	}

	public enum Bind {
		attr,
		attr2set
	}

	
	/**
	 * @param value
	 */
	public SampleBatchUpdate setAttr(String value){
		setParameter(Bind.attr.name(), value);
		return this;
	}

	/**
	 * @param value
	 */
	public SampleBatchUpdate setAttr2set(Integer value){
		setParameter(Bind.attr2set.name(), value);
		return this;
	}
}
