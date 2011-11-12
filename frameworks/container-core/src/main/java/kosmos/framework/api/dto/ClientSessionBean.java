/**
 * Copyright 2011 the original author
 */
package kosmos.framework.api.dto;

import java.io.Serializable;
import java.util.Locale;

/**
 * The session information between the client and the server.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ClientSessionBean implements Serializable,Cloneable{

	private static final long serialVersionUID = -5810188274130263782L;
	
	/** the user's id */
	private String userId = null;
	
	/** the client's IP address */
	private String sessionClientIp = null;
	
	/** the client's locale */
	private Locale locale = null;
	
	/**
	 * @return the locale
	 */
	public Locale getLocale(){
		return locale;
	}
	
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale){
		this.locale = locale;
	}
	
	/**
	 * @return ユーザID
	 */
	public String getUserId(){
		return userId;
	}
	
	/**
	 * @param userId ユーザID
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}

	/**
	 * @param sessionClientIp the sessionClientIp to set
	 */
	public void setSessionClientIp(String sessionClientIp) {
		this.sessionClientIp = sessionClientIp;
	}

	/**
	 * @return the sessionClientIp
	 */
	public String getSessionClientIp() {
		return sessionClientIp;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public ClientSessionBean clone(){
		try {
			return (ClientSessionBean)(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}
