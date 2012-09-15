/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.handler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import alpha.httpclient.config.ProxyProperty;
import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.core.XmlUtility;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractHttpInvocation implements HttpInvocation{

	@Override
	public Object request(RequestProperty property,Method method, Object[] args)
			throws Exception {

		HttpParams params = createHttpParams(property);
		SchemeRegistry registry = createSchemeRegistry(property);		
		ClientConnectionManager cm = createConnectionManager(property,registry);
		String url = createURL(property, method);
		HttpRequestBase base = createMethod(property, method, url,args);	
		
		DefaultHttpClient client = new DefaultHttpClient(cm,params);
		if(property.getProxyProperty() != null){
			throughProxy(property.getProxyProperty(),client);			
		}
		try {
			return execute(property.getHttpCallback(),client,base,method.getReturnType());
		}finally{	
			//keep-alive offの場合終了
			if(!property.isKeepAlive()){
				base.releaseConnection();
			}
		}
	}
	/**
	 * @param property
	 * @param client
	 */
	protected void throughProxy(ProxyProperty proxy, DefaultHttpClient client){
		HttpHost host = new HttpHost(proxy.getHost(),proxy.getPort());
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
		if(StringUtils.isNotEmpty(proxy.getUser())){
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(proxy.getUser(), proxy.getPassword());
			AuthScope scope = new AuthScope(proxy.getHost(), proxy.getPort());
			client.getCredentialsProvider().setCredentials(scope, credentials);
		}
	}
	
	/**
	 * @param client
	 * @param method
	 * @return
	 */
	protected abstract <T> T execute(HttpCallback callback,HttpClient client,HttpRequestBase method,Class<T> returnType) throws Exception;
	
	/**
	 * @param property
	 * @param registry
	 * @return
	 */
	protected ClientConnectionManager createConnectionManager(RequestProperty property,SchemeRegistry registry){
		if(registry != null){
			return new BasicClientConnectionManager(registry);
		}else {
			return null;
		}
	}
	
	/**
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
		
		List<Header> headers = property.getHttpHeader();
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
		httpMethod.setHeader("Content-Type", builder.toString());
		
		return httpMethod;
	}
	
	
	/**
	 * @param property
	 * @return
	 */
	protected SchemeRegistry createSchemeRegistry(RequestProperty property){
		SchemeRegistry registry = new SchemeRegistry();		
		boolean has = false;
		for(Entry<String,Integer> h : property.getSchemas().entrySet()){
			org.apache.http.conn.scheme.Scheme scheme = new Scheme(h.getKey(), h.getValue(),
					PlainSocketFactory.getSocketFactory());
			registry.register(scheme);
			has = true;
		}
		if(has){
			return registry;
		}
		return null;
	}
	
	/**
	 * @param property
	 * @return
	 */
	protected HttpParams createHttpParams(RequestProperty property){
		HttpParams httpParams = new BasicHttpParams();

		if(property.getConnectionProperty().getSocketTimeout() >= 0){
			HttpConnectionParams.setSoTimeout(httpParams, 
					property.getConnectionProperty().getSocketTimeout());
		}
		if(property.getConnectionProperty().getConnectionTimeout() >= 0){
			HttpConnectionParams.setConnectionTimeout(httpParams,
					property.getConnectionProperty().getConnectionTimeout());
		}
		
		return httpParams;
	}

}

