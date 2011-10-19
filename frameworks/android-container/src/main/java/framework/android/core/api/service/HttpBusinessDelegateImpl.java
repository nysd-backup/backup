/**
 * Copyright 2011 the original author
 */
package framework.android.core.api.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * HttpClientを使用してサーバと通信する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class HttpBusinessDelegateImpl implements InvocationHandler{
	
	/** サービスからリクエスチERLを取得すめE*/
	private DestinationResolver resolver = null;
	
	/**
	 * @param resolver リゾルチE
	 */
	public void setDestinationResolver(DestinationResolver resolver){
		this.resolver = resolver;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		
		HttpRequestBase method = null;
		if (arg1.getAnnotation(POST.class) != null || arg1.getDeclaringClass().getAnnotation(POST.class) != null){
			method = createPostParameter(resolver.resolveUrl(arg1),arg2);
		}else if(arg1.getAnnotation(GET.class) != null || arg1.getDeclaringClass().getAnnotation(GET.class) != null){
			method = createGetParameter(resolver.resolveUrl(arg1),arg2);			
		}else {
			throw new IllegalArgumentException();
		}

		//リクエスチE
		HttpResponse response = createClient().execute(method);
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
			
		}
			
		//TODO パラメータ解极E
		return response.getEntity();
	}
	/**
	 * @return HtppClient
	 */
	protected HttpClient createClient(){
		DefaultHttpClient client = new DefaultHttpClient();
		Cookie jsessionId = SessionManager.getSession();
		if(jsessionId != null){
			client.getCookieStore().addCookie(jsessionId);
		}
		return client;
	}
	
	/**
	 * POST処琁E
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	protected HttpRequestBase createPostParameter(String url, Object[] arg2){
		HttpPost method = new HttpPost(url);

		return method;
	}
	
	/**
	 * GET処琁E
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	protected HttpRequestBase createGetParameter(String url, Object[] arg2){
		HttpGet method = new HttpGet();

		return method;
	}

}
