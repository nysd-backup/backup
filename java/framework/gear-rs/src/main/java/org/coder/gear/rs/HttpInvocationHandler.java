/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.rs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.coder.gear.rs.balancer.LoadBalancer;
import org.coder.gear.rs.balancer.Requester;

/**
 * HttpInvocationHandler.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class HttpInvocationHandler implements InvocationHandler, Requester<Object>{
	
	/** fail over. */
	private final List<String> failoverCandidate;
	
	/** Http Client */
	private final Client client;
	
	/** method */
	private Method method = null;
	
	/** parameter */
	private Object parameter = null;
	
	/**
	 * Constructor .
	 */
	HttpInvocationHandler(List<String> failoverCandidate, Client client){
		this.failoverCandidate = failoverCandidate;
		this.client = client;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {		
		this.parameter = args[0];
		this.method = method;
		LoadBalancer balancer = new LoadBalancer();
		balancer.setCandidate(failoverCandidate);		
		return balancer.request(this);		
	}

	/**
	 * @see org.coder.gear.rs.balancer.Requester#request(java.lang.String)
	 */
	@Override
	public Object request(String server) throws SocketException {
		StringBuilder paths = new StringBuilder();
		Path path = method.getDeclaringClass().getAnnotation(Path.class);
		if (path != null ){
			paths.append(path.value());
		}
		Path methodPath = method.getAnnotation(Path.class);
		if (methodPath != null ){
			paths.append(methodPath.value());
		}
		
		//URL生成
		WebTarget target = client.target(String.format("%s/%s",server,paths.toString()));		
		
		Consumes cms = method.getDeclaringClass().getAnnotation(Consumes.class);
		if(cms == null){
			cms = method.getAnnotation(Consumes.class);
		}		
		Produces pds = method.getDeclaringClass().getAnnotation(Produces.class);
		if(pds == null){
			pds = method.getAnnotation(Produces.class);
		}		
		
		//実行
		Response response =  target.request(MediaType.valueOf(pds.value()[0])).post(Entity.entity(parameter,MediaType.valueOf(cms.value()[0])));
		
		//レスポンス取得 TODO exception handlerにする
		if(response.getStatus() == Response.Status.OK.getStatusCode()){
			return response.readEntity(method.getReturnType());
		}else if(response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()){
			throw new SocketException("internal server error");
		}else {
			throw new IllegalStateException(String.valueOf(response.getStatus()));
		}
	}
}
