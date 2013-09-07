/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.rs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * HttpInvocationHandler.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HttpInvocationHandler implements InvocationHandler{
	
	/** context root. */
	private String contextRoot;
	
	/** client. */
	private Client client = ClientBuilder.newClient();
	
	/**
	 * @param contextRoot to set
	 */
	public void setContextRoot(String contextRoot){
		this.contextRoot = contextRoot;
	}
	
	/**
	 * @param client to set
	 */
	public void setClient(Client client){
		this.client = client;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		List<Path> listPath = new ArrayList<Path>();
		Path path = method.getDeclaringClass().getAnnotation(Path.class);
		listPath.add(path);
			
		//URL生成
		WebTarget target = client.target(contextRoot);
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
		
		//送信
		Response response = target.request(cms.value()).accept(pds.value())
			.post(Entity.entity(null, cms.value()[0]));
		
		return response.readEntity(method.getReturnType());
	}

}
