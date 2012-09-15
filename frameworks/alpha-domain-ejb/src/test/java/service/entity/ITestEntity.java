/**
 * Copyright 2011 the original author
 */
package service.entity;

import alpha.sqlclient.orm.Metadata;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface ITestEntity {

	public static final Metadata<TestEntity,String> TEST = new Metadata<TestEntity,String>("test");
	
	public static final Metadata<TestEntity,String> ATTR = new Metadata<TestEntity,String>("attr");

	public static final Metadata<TestEntity,Integer> ATTR2 = new Metadata<TestEntity,Integer>("attr2");
	
	public static final Metadata<TestEntity,Integer> VERSION = new Metadata<TestEntity,Integer>("version");
	
}
