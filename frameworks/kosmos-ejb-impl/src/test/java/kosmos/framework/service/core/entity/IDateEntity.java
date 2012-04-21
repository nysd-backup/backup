/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core.entity;

import java.util.Date;

import kosmos.framework.sqlclient.orm.Metadata;


/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface IDateEntity {

	public static final Metadata<DateEntity,String> TEST = new Metadata<DateEntity,String>("test");
	
	public static final Metadata<DateEntity,String> ATTR = new Metadata<DateEntity,String>("attr");

	public static final Metadata<DateEntity,Integer> ATTR2 = new Metadata<DateEntity,Integer>("attr2");
	
	public static final Metadata<DateEntity,Date> DATE_COL = new Metadata<DateEntity,Date>("dateCol");
	
	public static final Metadata<DateEntity,Integer> VERSION = new Metadata<DateEntity,Integer>("version");
	
}
