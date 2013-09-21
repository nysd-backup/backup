/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs.balancer;

import java.util.List;

/**
 * BalancingStrategy.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface BalancingStrategy {

	/**
	 * @param candidate to select
	 * @return selected candidate
	 */
	String select(List<String> candidate);
}
