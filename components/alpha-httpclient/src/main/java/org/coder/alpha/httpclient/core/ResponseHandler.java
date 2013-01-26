/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.httpclient.core;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * ResponseHandler.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ResponseHandler {

	/**
	 * Handles the response 
	 * @param response
	 * @param returnType
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getEntity(HttpResponse response , Class<T> returnType) throws ParseException, IOException{
		response.getStatusLine().getStatusCode();
		T retValue = null;
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){			
			Header headers = response.getFirstHeader(HTTP.CONTENT_TYPE);			
			String responseValue = EntityUtils.toString(response.getEntity(),Consts.UTF_8);
			if(headers.getValue().toLowerCase().contains("xml")){
				retValue =  XmlUtility.unmarshal(responseValue, returnType);
			}else if(headers.getValue().toLowerCase().contains("json")){
				retValue = null;	//TODO JSON対応
			}else{
				retValue = (T)responseValue;
			}
		}		
		return retValue;
	}
}
