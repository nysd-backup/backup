/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.var;

import java.util.List;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface DataCommand<T> {
	
	public String[] getTitle();

	public List<T> getResultList();
	
	public void close();
}
