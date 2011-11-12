/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kosmos.framework.api.dto.ClientSessionBean;

/**
 * The utility for security.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class SecurityUtil {
	
	public static final String ONETIME_TOKEN = "onetime.token";
	
	/**
	 * Determines whether the one-time token is valid.
	 * 
	 * @param request the request
	 * @return true:invalid token
	 */
	public static boolean isTokenInvalid(HttpServletRequest request){
		String requestToken = request.getParameter(ONETIME_TOKEN);
		HttpSession session = request.getSession(false);
		String savedToken = (String)session.getAttribute(ONETIME_TOKEN);
		return requestToken == null ? savedToken == null : requestToken.equals(savedToken);
	}

	/**
	 * Determines whether the session is expired.
	 * 
	 * @param request the request
	 * @return true:session is expired
	 */
	public static boolean isSessionExpired(HttpServletRequest request){
		return getClientSessionBean(request) == null;
	}
	
	/**
	 * Determines whether the IP-address is changed in the session.
	 * 
	 * @param request the request
	 * @return true:ip-address is changed
	 */
	public static boolean isClientIpChanged(HttpServletRequest request ){
		String uid = request.getRemoteAddr();
		if(uid == null ){
			return getClientSessionBean(request).getSessionClientIp() != null;
		}else{
			return !uid.equals(getClientSessionBean(request).getSessionClientIp());
		}
	}
	
	/**
	 * Gets the session.
	 * 
	 * @param request the request
	 * @return the session 
	 */
	private static ClientSessionBean getClientSessionBean(HttpServletRequest request ){
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else {
			return ClientSessionBean.class.cast(session.getAttribute(ClientSessionBean.class.getName()));
		}
	}
}
