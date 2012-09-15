/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.handler.impl;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import alpha.httpclient.core.XmlUtility;
import alpha.httpclient.handler.AbstractHttpInvocation;
import alpha.httpclient.handler.HttpCallback;


/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SyncHttpClient extends AbstractHttpInvocation {

	/**
	 * @see alpha.httpclient.handler.AbstractHttpInvocation#execute(org.apache.http.client.HttpClient, org.apache.http.client.methods.HttpRequestBase)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T execute(HttpCallback callback,HttpClient client, HttpRequestBase method,Class<T> returnType) throws Exception{
		HttpResponse response = client.execute(method);
		response.getStatusLine().getStatusCode();
		T retValue = null;
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){			
			Header headers = response.getFirstHeader("Content-Type");			
			String responseValue = EntityUtils.toString(response.getEntity(),"utf-8");
			if(headers.getValue().toLowerCase().contains("xml")){
				retValue =  XmlUtility.unmarshal(responseValue, returnType);
			}else if(headers.getValue().toLowerCase().contains("json")){
				retValue = null;	//TODO JSON対応
			}else{
				retValue = (T)responseValue;
			}
		}
		//callback
		if(callback != null){
			callback.callback(response);
		}
		return retValue;
	}

}