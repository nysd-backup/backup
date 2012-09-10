/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator.entity.model;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Column {
	
	public String physicalName;
	
	public String logicalName;
	
	public Class<?> typeName;
	
	public boolean primaryKey;
	
	public boolean versionManaged;

}
