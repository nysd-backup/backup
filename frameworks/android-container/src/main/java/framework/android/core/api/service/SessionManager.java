/**
 * Copyright 2011 the original author
 */
package framework.android.core.api.service;

import org.apache.http.cookie.Cookie;

/**
 * セッション管理.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
class SessionManager {
	
	private static Cookie session = null;
	
	/**
	 * @param site サイト
	 * @param newSession セッション
	 */
	static void initializeSession(Cookie newSession){
		session = newSession;
	}
	
	/**
	 * @param site サイト
	 * @return セッション
	 */
	static Cookie getSession(){
		return session;
	}
}
