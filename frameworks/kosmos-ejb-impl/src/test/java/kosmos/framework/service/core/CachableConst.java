/**
 * Copyright 2011 the original author
 */
package kosmos.framework.service.core;

import java.math.BigDecimal;

/**
 * function.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface CachableConst {

	public static final String TARGET_TEST_1 = "1";

	public static final String TARGET_TEST_1_OK = "3";
	
	public static final BigDecimal TARGET_DECIMAL = new BigDecimal(100);
	
	public static final int TARGET_INT = 1000;
	
	public static final boolean TARGET_BOOL = true;
}
