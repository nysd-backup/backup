/**
 * Copyright 2011 the original author
 */
package alpha.framework.core.base;


/**
 * CorrelativeRequest.
 *
 * @author yoshida-n
 * @version	created.
 */
public interface CorrelativeRequest {
	
	/**
	 * @return correlationId
	 */
	public String getCorrelationId();
	
	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId);
}
