/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.coder.alpha.rs.balancer.LoadBalancer;
import org.coder.alpha.rs.balancer.Requester;

/**
 * HttpInvocationHandler.
 *
 * @author yoshida-n
 * @version	created.
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
	 * @see org.coder.alpha.rs.balancer.Requester#request(java.lang.String)
	 */
	@Override
	public Object request(String server) throws SocketException {
		List<Path> listPath = new ArrayList<Path>();
		Path path = method.getDeclaringClass().getAnnotation(Path.class);
		if (path != null ){
			listPath.add(path);
		}
		Path methodPath = method.getAnnotation(Path.class);
		if (methodPath != null ){
			listPath.add(methodPath);
		}
		
		//URL生成
		WebTarget target = client.target(server);
		for(Path p : listPath){
			target.path(p.value());
		}
		
		Consumes cms = method.getDeclaringClass().getAnnotation(Consumes.class);
		if(cms == null){
			cms = method.getAnnotation(Consumes.class);
		}		
		Produces pds = method.getDeclaringClass().getAnnotation(Produces.class);
		if(pds == null){
			pds = method.getAnnotation(Produces.class);
		}		
		MediaType requestType = MediaType.valueOf(cms.value()[0]);
		Response response =  target.request(pds.value()).post(Entity.entity(parameter, requestType));
		return response.readEntity(method.getReturnType());
	
	}

}
