/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.test.entity;

import kosmos.framework.core.query.Metadata;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface IFastEntity {

	public static final Metadata<FastEntity,String> TEST = new Metadata<FastEntity,String>("test");
	
	public static final Metadata<FastEntity,String> ATTR = new Metadata<FastEntity,String>("attr");

	public static final Metadata<FastEntity,Integer> ATTR2 = new Metadata<FastEntity,Integer>("attr2");
	
	public static final Metadata<FastEntity,Integer> VERSION = new Metadata<FastEntity,Integer>("version");
	
}
