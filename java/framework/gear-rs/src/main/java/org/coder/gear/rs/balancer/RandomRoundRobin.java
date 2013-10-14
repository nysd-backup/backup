/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.rs.balancer;

import java.util.List;

/**
 * RandomRoundRobin.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class RandomRoundRobin implements BalancingStrategy{

	/**
	 * @see org.coder.gear.rs.balancer.BalancingStrategy#select(java.util.List)
	 */
	@Override
	public String select(List<String> candidate) {
		long seed = System.currentTimeMillis();
		return candidate.get((int)(seed % candidate.size()));
	}

}
