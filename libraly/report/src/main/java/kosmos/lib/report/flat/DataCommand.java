/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.flat;

import java.util.List;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface DataCommand<T> {

	public List<T> getResultList();
}
