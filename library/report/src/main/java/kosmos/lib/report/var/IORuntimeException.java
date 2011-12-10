/**
 * Copyright 2011 the original author
 */
package kosmos.lib.report.var;

import java.io.IOException;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class IORuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IORuntimeException(IOException ioe) {
		super(ioe);
	}
}
