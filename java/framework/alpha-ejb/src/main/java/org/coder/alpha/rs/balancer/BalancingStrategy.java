/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.rs.balancer;

import java.util.List;

/**
 * BalancingStrategy.
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface BalancingStrategy {

	/**
	 * @param candidate to select
	 * @return selected candidate
	 */
	String select(List<String> candidate);
}
