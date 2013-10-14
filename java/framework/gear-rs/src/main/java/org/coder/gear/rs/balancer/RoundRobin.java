/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.rs.balancer;

import java.util.List;

/**
 * RoundRobin.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class RoundRobin implements BalancingStrategy{

	/**
	 * @see org.coder.gear.rs.balancer.BalancingStrategy#select(java.util.List)
	 */
	@Override
	public String select(List<String> candidate) {
		return candidate.get(0);
	}

}
