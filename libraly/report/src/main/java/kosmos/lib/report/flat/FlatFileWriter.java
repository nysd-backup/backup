/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.flat;

import java.util.HashSet;

import org.supercsv.io.ICsvListWriter;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface FlatFileWriter {

	public void write(ICsvListWriter writer ,Object target , HashSet<String> includes);
}
