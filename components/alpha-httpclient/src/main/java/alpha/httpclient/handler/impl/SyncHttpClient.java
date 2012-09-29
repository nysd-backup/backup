/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.handler.impl;

import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import alpha.httpclient.config.KeepAliveScope;
import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.context.HttpClientContext;
import alpha.httpclient.core.ResponseHandler;
import alpha.httpclient.handler.AbstractHttpInvocation;
import alpha.httpclient.handler.HttpCallback;


/**
 * SyncHttpClient.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SyncHttpClient extends AbstractHttpInvocation {
	
	/** total connection */
	private int maxTotal = 100;
	
	/** max connection per route */
	private int defaultMaxPerRoute = 50;
	
	/**
	 * @param defaultMaxPerRoute the defaultMaxPerRoute to set 
	 */
	public void setDefaultMaxPerRoute(int defaultMaxPerRoute){
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}
	
	/**
	 * @param maxTotal the maxTotal to set
	 */
	public void setMaxTotal(int maxTotal){
		this.maxTotal = maxTotal;
	}


	/**
	 * @see alpha.httpclient.handler.AbstractHttpInvocation#execute(alpha.httpclient.config.RequestProperty, org.apache.http.conn.ClientConnectionManager, org.apache.http.params.HttpParams, org.apache.http.client.methods.HttpRequestBase, java.lang.Class)
	 */
	@Override
	protected <T> T execute(RequestProperty property,
			HttpRequestBase method, Class<T> returnType) throws Exception {
		
		//connection manager
		
		//cookie
		DefaultHttpClient client = createClient(property);
		client.setParams(property.getHttpParams());
		
		for(Cookie c : property.getHeaderProperty().getCookies()){
			client.getCookieStore().addCookie(c);
		}
		
		//auth
		for(Entry<AuthScope,Credentials> e :property.getCredencials().entrySet()){
			client.getCredentialsProvider().setCredentials(e.getKey(), e.getValue());
		}
		
		//レスポンス取得
		HttpResponse response = client.execute(method);
		T retValue = ResponseHandler.getEntity(response, returnType);
		
		//callback
		HttpCallback callback = property.getExecutionProperty().getHttpCallback();
		if(callback != null){
			callback.callback(response);
		}
		return retValue;
	}
	
	/**
	 * @see alpha.httpclient.handler.AbstractHttpInvocation#createConnectionManager(alpha.httpclient.config.RequestProperty, org.apache.http.conn.scheme.SchemeRegistry)
	 */
	protected DefaultHttpClient createClient(RequestProperty property){
		KeepAliveScope scope = property.getConnectionProperty().getKeepAliveScope();
		if(KeepAliveScope.THREAD == scope){
			HttpClientContext context = HttpClientContext.getCurrentInstance();
			if(context == null){
				throw new IllegalStateException("HttpClientContext must be initialized");
			}
			DefaultHttpClient client = context.getSynchronousClient();
			if(client != null){
				client.getCookieStore().clear();
				client.getCredentialsProvider().clear();
				return client;
			}else {		
				SchemeRegistry registry = createSchemeRegistry(property);
				ClientConnectionManager manager = null;
				if(property.getConnectionProperty().isPoolable()){
					manager = registry != null ?  
							new PoolingClientConnectionManager(registry) : new PoolingClientConnectionManager();
					if(defaultMaxPerRoute > 0){
						((PoolingClientConnectionManager)manager).setDefaultMaxPerRoute(defaultMaxPerRoute);
					}
					if(maxTotal > 0){
						((PoolingClientConnectionManager)manager).setMaxTotal(maxTotal);
					}
				}else{
					manager = registry != null ? 
						new BasicClientConnectionManager(registry): new BasicClientConnectionManager();
				}
				client = new DefaultHttpClient(manager);
				
				context.setSynchronousClient(client);
				return client;
			}			
		}else{
			if(property.getConnectionProperty().isPoolable()){
				throw new IllegalStateException("KeepAliveScope must not be request when connection is poolable");
			}
			SchemeRegistry registry = createSchemeRegistry(property);
			ClientConnectionManager manager = registry != null ? 
					new BasicClientConnectionManager(registry): new BasicClientConnectionManager();
			return new DefaultHttpClient(manager);			
		}
	}
	

	/**
	 * Creates the scheme
	 * @param property
	 * @return
	 */
	protected SchemeRegistry createSchemeRegistry(RequestProperty property){
		SchemeRegistry registry = new SchemeRegistry();		
		boolean has = false;
		for(Entry<String,Integer> h : property.getSchemes().entrySet()){
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

}