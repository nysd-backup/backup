/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.rs.balancer;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LoadBalancer.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class LoadBalancer {
	
	/** blackList */
	private static final Map<String,Long> blackList = new ConcurrentHashMap<String,Long>();
	
	/** retriveTime */
	private long retriveTime = 60;

	/** balancer */
	private BalancingStrategy strategy = new RoundRobin();

	/** candidate */
	private List<String> candidate = new ArrayList<String>();
	
	/**
	 * @param requester request
	 * @throws SocketException
	 */
	public <T> T request(Requester<T> requester) throws SocketException{
		return doExecute(requester,candidate);
	}
	
	/**
	 * @param requester to requester
	 * @param candidate candidate
	 * @throws SocketException 
	 */
	private <T> T doExecute(Requester<T> requester,List<String> candidate) throws SocketException{
		
		String server = selectServer(candidate);
		if(server == null){
			throw new SocketException("no server is available");
		}
		try{					
			return requester.request(server);
		}catch(SocketException se){
			blackList.put(server, System.currentTimeMillis());
			List<String> copy = copy(candidate,server);	
			if(copy.isEmpty()){
				throw se;
			}else{
				return doExecute(requester,copy);
			}			
		}
	}
	
	/**
	 * Selects the specified server.
	 * @param serverList  serverlist
	 * @return server
	 */
	private String selectServer(List<String> serverList){
		if(serverList == null || serverList.isEmpty()){
			return null;
		}
		String server = strategy.select(serverList);
		if(isMarkedAsBlack(server)){			
			List<String> array = copy(serverList,server);
			return selectServer(array);
		}else {
			return server;
		}
	}
	
	/**
	 * Tests if the specified server is marked as black.
	 * @param server server
	 * @return true:black
	 */
	private boolean isMarkedAsBlack(String server){
		Long time = blackList.get(server);
		if(time == null){
			return false;
		}else {
			long msec = System.currentTimeMillis() - time;
			long sec = msec / 1000;
			boolean black = sec < retriveTime;
			if(!black){
				blackList.remove(server);
			}
			return black;
		}
	}
	
	/**
	 * Copy list
	 * @param source source
	 * @param ignore ignoring target
	 * @return copy
	 */
	private List<String> copy(List<String> source, String ignore){
		List<String> array = new ArrayList<String>();
		array.addAll(source);
		array.remove(ignore);
		return array;
	}

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(BalancingStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * @param candidate the candidate to set
	 */
	public void setCandidate(List<String> candidate) {
		this.candidate = candidate;
	}
	
	/**
	 * @param retriveTime the retriveTime to set
	 */
	public void setRetriveTime(long retriveTime) {
		this.retriveTime = retriveTime;
	}
}
