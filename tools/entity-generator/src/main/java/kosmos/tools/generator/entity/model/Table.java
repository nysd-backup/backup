/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator.entity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Table {
	
	public String basePkgName;
	
	public String physicalName;
	
	public String logicalName;
	
	public List<Column> columns = new ArrayList<Column>();

}
