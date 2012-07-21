/**
 * Copyright 2011 the original author
 */
package service.test;

import client.sql.free.AbstractNativeModifyQuery;
import client.sql.free.AnonymousQuery;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@AnonymousQuery(query="@/sql/SAMPLE_BATCH_UPDATE.sql")
public class SampleBatchUpdate extends AbstractNativeModifyQuery{

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
