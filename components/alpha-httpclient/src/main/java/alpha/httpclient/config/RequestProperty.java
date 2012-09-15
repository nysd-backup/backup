/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicHeader;

import alpha.httpclient.handler.HttpCallback;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RequestProperty {
	
	private String contextRoot = null;
	
	private ConnectionProperty connectionProperty = new ConnectionProperty();
	
	private List<Header> httpHeader = new ArrayList<Header>();
	
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	private Map<String,Integer> schemas = new HashMap<String,Integer>();
	
	private boolean asynchronous = false;

	private ProxyProperty proxyProperty = null;
	
	private HttpCallback httpCallback = null;
	
	private boolean keepAlive = false;

	public static RequestProperty create(HttpConfig config){
		return new RequestProperty();
	}
	
	public void override(RequestProperty property){
		contextRoot = property.getContextRoot();
		connectionProperty = property.getConnectionProperty();
		httpHeader.addAll(property.getHttpHeader());
		cookies.addAll(property.getCookies());
		schemas.putAll(property.getSchemas());
		asynchronous = property.isAsynchronous();
		proxyProperty = property.getProxyProperty();
		httpCallback = property.getHttpCallback();
		keepAlive = property.isKeepAlive();
	}

	/**
	 * @return the contextRoot
	 */
	public String getContextRoot() {
		return contextRoot;
	}

	/**
	 * @param contextRoot the contextRoot to set
	 */
	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}

	/**
	 * @return the httpHeader
	 */
	public List<Header> getHttpHeader() {
		return httpHeader;
	}

	/**
	 * @param httpHeader the httpHeader to set
	 */
	public void setHttpHeader(List<Header> httpHeader) {
		this.httpHeader = httpHeader;
	}
	
	public void addHeader(String name , String value){
		this.httpHeader.add(new BasicHeader(name, value));
	}

	/**
	 * @return the schemas
	 */
	public Map<String,Integer> getSchemas() {
		return schemas;
	}

	/**
	 * @param schemas the schemas to set
	 */
	public void setSchemas(Map<String,Integer> schemas) {
		this.schemas = schemas;
	}
	
	public void addSchema(String schema , int port){
		this.schemas.put(schema, port);
	}

	/**
	 * @return the asynchronous
	 */
	public boolean isAsynchronous() {
		return asynchronous;
	}

	/**
	 * @param asynchronous the asynchronous to set
	 */
	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	/**
	 * @return the httpCallback
	 */
	public HttpCallback getHttpCallback() {
		return httpCallback;
	}

	/**
	 * @param httpCallback the httpCallback to set
	 */
	public void setHttpCallback(HttpCallback httpCallback) {
		this.httpCallback = httpCallback;
	}

	/**
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}
	
	public void addCookie(Cookie cookie){
		this.cookies.add(cookie);
	}

	/**
	 * @return the connectionProperty
	 */
	public ConnectionProperty getConnectionProperty() {
		return connectionProperty;
	}

	/**
	 * @param connectionProperty the connectionProperty to set
	 */
	public void setConnectionProperty(ConnectionProperty connectionProperty) {
		this.connectionProperty = connectionProperty;
	}

	/**
	 * @return the proxyProperty
	 */
	public ProxyProperty getProxyProperty() {
		return proxyProperty;
	}

	/**
	 * @param proxyProperty the proxyProperty to set
	 */
	public void setProxyProperty(ProxyProperty proxyProperty) {
		this.proxyProperty = proxyProperty;
	}

	/**
	 * @return the keepAlive
	 */
	public boolean isKeepAlive() {
		return keepAlive;
	}

	/**
	 * @param keepAlive the keepAlive to set
	 */
	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
}

