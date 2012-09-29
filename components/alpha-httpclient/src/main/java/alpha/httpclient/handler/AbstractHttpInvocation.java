/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.handler;

import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.core.XmlUtility;

/**
 * AbstractHttpInvocation.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractHttpInvocation implements HttpInvocation{

	/**
	 * @see alpha.httpclient.handler.HttpInvocation#request(alpha.httpclient.config.RequestProperty, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object request(RequestProperty property,Method method, Object[] args)
			throws Exception {
						
		String url = createURL(property, method);
		HttpRequestBase httpMethod = createMethod(property, method, url,args);				
		
		return execute(property,httpMethod,method.getReturnType());
	}
	
	/**
	 * Executes the request.
	 * @param property
	 * @param method
	 * @param returnType
	 * @return
	 * @throws Exception
	 */
	protected abstract <T> T execute(RequestProperty property,
			HttpRequestBase method,Class<T> returnType) throws Exception;
	
	
	
	/**
	 * Creates the url
	 * @param property
	 * @param method
	 * @return
	 */
	protected String createURL(RequestProperty property ,Method method ){
		StringBuilder url = new StringBuilder();
		url.append(property.getContextRoot());
		
		Path path = method.getDeclaringClass().getAnnotation(Path.class);
		if(path != null){
			url.append(path.value());
		}
		path = method.getAnnotation(Path.class);
		if( path != null){
			url.append(path.value());
		}
		return url.toString();
	}
	
	/**
	 * Creats the method
	 * @param property
	 * @param method
	 * @param url
	 * @return
	 */
	protected HttpRequestBase createMethod(RequestProperty property, Method method , String url, Object[] args)
	throws Exception{
		
		POST post = method.getAnnotation(POST.class);
		Produces produce = method.getAnnotation(Produces.class);
		HttpRequestBase httpMethod = null;
		if(post != null){
			httpMethod = new HttpPost(url);		
			if(produce.value()[0].toLowerCase().contains("xml")){
				String xml = XmlUtility.marshal(args[0]);
				((HttpPost)httpMethod).setEntity(new StringEntity(xml));
			}
		}
		
		List<Header> headers = property.getHeaderProperty().getHttpHeader();
		for(Header h : headers){
			httpMethod.setHeader(h);
		}		
		String[] type = produce.value();
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < type.length; i++){
			String t = type[i];
			if(i > 0)builder.append(",");
			builder.append(t);				
		}
		httpMethod.setHeader(HTTP.CONTENT_TYPE, builder.toString());
		
		return httpMethod;
	}

}

