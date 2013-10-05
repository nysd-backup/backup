/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs.balancer;

import java.util.List;

/**
 * RandomRoundRobin.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RandomRoundRobin implements BalancingStrategy{

	/**
	 * @see org.coder.alpha.rs.balancer.BalancingStrategy#select(java.util.List)
	 */
	@Override
	public String select(List<String> candidate) {
		long seed = System.currentTimeMillis();
		return candidate.get((int)(seed % candidate.size()));
	}

}
