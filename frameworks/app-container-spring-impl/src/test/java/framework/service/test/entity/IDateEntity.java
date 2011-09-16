/**
 * Use is subject to license terms.
 */
package framework.service.test.entity;

import java.util.Date;

import framework.core.entity.Metadata;

/**
 * function.
 *
 * @author yoshida-n
 * @version	2011/04/20 created.
 */
public interface IDateEntity {

	public static final Metadata<DateEntity,String> TEST = new Metadata<DateEntity,String>("test");
	
	public static final Metadata<DateEntity,String> ATTR = new Metadata<DateEntity,String>("attr");

	public static final Metadata<DateEntity,Integer> ATTR2 = new Metadata<DateEntity,Integer>("attr2");
	
	public static final Metadata<DateEntity,Date> DATE_COL = new Metadata<DateEntity,Date>("dateCol");
	
	public static final Metadata<DateEntity,Integer> VERSION = new Metadata<DateEntity,Integer>("version");
	
}
