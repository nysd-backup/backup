/**
 * Copyright 2011 the original author
 */
package framework.api.dto;

import java.io.Serializable;

/**
 * A request data from WEB container
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class RequestDto implements Serializable{

	private static final long serialVersionUID = -5671768150819629678L;
	
	/** the parameter */
	private Serializable[] parameter;

	/** the class of target service */
	private Class<?> targetClass;
	
	/** the alias of target service */
	private String alias;
	
	/** the method name */
	private String methodName;
	
	/** the types of parameter */
	private Class<?>[] parameterTypes;
	
	/**
	 * @return the types
	 */
	public Class<?>[] getParameterTypes(){
		return parameterTypes;
	}
	
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the serviceName
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the parameter
	 */
	public Serializable[] getParameter() {
		return parameter;
	}

	/**
	 * @return the targetClass
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(Serializable[] parameter) {
		this.parameter = parameter;
	}

	/**
	 * @param targetClass the targetClass to set
	 */
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @param parameterTypes the parameterType to set
	 */
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

}
