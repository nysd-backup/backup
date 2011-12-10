/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.var;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface VarFileWriter {

	public void write(String... target);
	
	public void write(Object target , Metadata... includes);
	
	public void close();
}
