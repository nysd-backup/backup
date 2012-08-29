/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import javax.jms.ConnectionFactory;

/**
 * MessagingProperty for EJB .
 *
 * @author yoshida-n
 * @version	created.
 */
public class EJBMessagingProperty extends MessagingProperty{

	private static final long serialVersionUID = 1L;

	private static final String _CONNECTION_FACTORY = "alpha.connection.factory";
	
	/**
	 * @param factory ConnectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory factory){
		put(_CONNECTION_FACTORY,factory);
	}
	/**
	 * @return ConnectionFactory
	 */
	public ConnectionFactory getConnectionFactory(){
		return (ConnectionFactory)get(_CONNECTION_FACTORY);
	}
}
