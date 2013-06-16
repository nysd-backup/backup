/**
 * Copyright 2011 the original author
 */
package service.entity;

import org.coder.alpha.query.criteria.Metadata;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ITestEntity {

	public static final Metadata<TargetEntity,String> TEST = new Metadata<TargetEntity,String>("test");
	
	public static final Metadata<TargetEntity,String> ATTR = new Metadata<TargetEntity,String>("attr");

	public static final Metadata<TargetEntity,Integer> ATTR2 = new Metadata<TargetEntity,Integer>("attr2");
	
	public static final Metadata<TargetEntity,Integer> VERSION = new Metadata<TargetEntity,Integer>("version");
	
}
