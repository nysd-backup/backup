/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs.balancer;

import java.util.List;

/**
 * RoundRobin.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RoundRobin implements BalancingStrategy{

	/**
	 * @see org.coder.alpha.rs.balancer.BalancingStrategy#select(java.util.List)
	 */
	@Override
	public String select(List<String> candidate) {
		return candidate.get(0);
	}

}
