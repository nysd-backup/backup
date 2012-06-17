/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.io.Serializable;


/**
 * A request data from WEB container
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InvocationParameter implements Serializable{

	private static final long serialVersionUID = -5671768150819629678L;
	
	/** the parameter */
	private Serializable[] parameter;
	
	/** the alias of target service */
	private String serviceName;
	
	/** the method name */
	private String methodName;
	
	/** the names of parameter class */
	private String[] parameterTypeNames;
	
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the parameter
	 */
	public Serializable[] getParameter() {
		return parameter;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(Serializable[] parameter) {
		this.parameter = parameter;
	}


	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the parameterTypeNames
	 */
	public String[] getParameterTypeNames() {
		return parameterTypeNames;
	}

	/**
	 * @param parameterTypeNames the parameterTypeNames to set
	 */
	public void setParameterTypeNames(String[] parameterTypeNames) {
		this.parameterTypeNames = parameterTypeNames;
	}

}
