/**
 * Copyright 2011 the original author
 */
package alpha.httpclient.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Request Property.
 *
 * @author yoshida-n
 * @version	created.
 */
public class RequestProperty {
	
	/** the context root */
	private String contextRoot = null;
		
	/** request headers */
	private final HeaderProperty headerProperty = new HeaderProperty();
		
	/** credentials */
	private final Map<AuthScope,Credentials> credencials = new HashMap<AuthScope,Credentials>();

	/** schemes */
	private final Map<String,Integer> schemes = new HashMap<String,Integer>();

	/** httpclient parameter */
	private final HttpParams httpParams = new BasicHttpParams();
	
	/** executing property */
	private final ExecutionProperty executionProperty = new ExecutionProperty();
	
	/** connection property */
	private final ConnectionProperty connectionProperty = new ConnectionProperty();

	/**
	 * Creates the property
	 * @param con
	 * @param scheme
	 * @param async
	 * @param proxy
	 * @return
	 */
	public static RequestProperty create(ConnectionConfig con, Schemes scheme, Asynchronous async,ProxyConfig proxy){
		RequestProperty property = new RequestProperty();		
		//connection
		if(con != null){
			property.connectionProperty.setKeepAliveScope(con.scope());
			HttpConnectionParams.setSoTimeout(property.httpParams,con.socketTimeout());
			HttpConnectionParams.setConnectionTimeout(property.httpParams,con.connectionTimeout());
			property.connectionProperty.setPoolable(con.poolable());
		}
		//execution
		property.getExecutionProperty().setAsynchronous(async != null);
		//schema
		if(scheme != null){			
			for(SchemeConfig sc : scheme.schemes()){
				property.addScheme(sc.name(), sc.port());
			}
		}		
	
		//proxy
		if(proxy != null){			
			HttpHost host = new HttpHost(proxy.host(),proxy.port());
			ConnRouteParams.setDefaultProxy(property.httpParams,host);
			if(StringUtils.isNotEmpty(proxy.user())){ 
				UsernamePasswordCredentials cred = new UsernamePasswordCredentials(proxy.user(),proxy.password());
				AuthScope scope = new AuthScope(host);
				property.credencials.put(scope, cred);
			}
		}
	
		return property;
	}
	
	/**
	 * Overrides the property 
	 * @param property the source
	 */
	public void override(RequestProperty property){

		//context 
		contextRoot = property.getContextRoot();		

		//execution	
		if(property.getExecutionProperty().isAsynchronous()){
			executionProperty.setAsynchronous(true);
		}
		executionProperty.setHttpCallback(property.getExecutionProperty().getHttpCallback());	
		schemes.putAll(property.getSchemes());
		
		//connection
		if(KeepAliveScope.REQUEST != property.getConnectionProperty().getKeepAliveScope()){
			connectionProperty.setKeepAliveScope(property.getConnectionProperty().getKeepAliveScope());
		}

		//parameters
		BasicHttpParams params = (BasicHttpParams)property.httpParams;
		for(String name : params.getNames()){
			httpParams.setParameter(name, params.getParameter(name));
		}
		
		//authentication
		credencials.putAll(property.getCredencials());		
		
		//header
		headerProperty.getHttpHeader().addAll(property.getHeaderProperty().getHttpHeader());
		headerProperty.getCookies().addAll(property.getHeaderProperty().getCookies());
		headerProperty.setCookiePolicy(property.getHeaderProperty().getCookiePolicy());
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
	 * @return the connectionProperty
	 */
	public ConnectionProperty getConnectionProperty() {
		return connectionProperty;
	}

	/**
	 * @return the executionProperty
	 */
	public ExecutionProperty getExecutionProperty() {
		return executionProperty;
	}

	/**
	 * @return the headerProperty
	 */
	public HeaderProperty getHeaderProperty() {
		return headerProperty;
	}

	/**
	 * @return the credencials
	 */
	public Map<AuthScope,Credentials> getCredencials() {
		return credencials;
	}
	
	/**
	 * @param scope
	 * @param credentials
	 */
	public void putCredentials(AuthScope scope , Credentials credentials){
		this.credencials.put(scope, credentials);
	}

	/**
	 * @return the httpParams
	 */
	public HttpParams getHttpParams() {
		return httpParams;
	}
	
	
	/**
	 * @return the schemes
	 */
	public Map<String, Integer> getSchemes() {
		return schemes;
	}

	/**
	 * @param host
	 * @param port
	 */
	public void addScheme(String host, int port){
		schemes.put(host, port);
	}
	
	/**
	 * 
	 */
	public void setAsynchronous(){
		executionProperty.setAsynchronous(true);
		connectionProperty.setKeepAliveScope(KeepAliveScope.THREAD);
	}
}

