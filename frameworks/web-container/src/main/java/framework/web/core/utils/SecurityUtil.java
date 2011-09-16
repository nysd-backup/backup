/**
 * Use is subject to license terms.
 */
package framework.web.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import framework.api.dto.ClientSessionBean;

/**
 * セキュリティチェック.
 *
 * @author yoshida-n
 * @version	created.
 */
public class SecurityUtil {
	
	public static final String ONETIME_TOKEN = "onetime.token";
	
	/**
	 * @param request リクエスト
	 * @return true:トークン不正
	 */
	public static boolean isTokenInvalid(HttpServletRequest request){
		String requestToken = request.getParameter(ONETIME_TOKEN);
		HttpSession session = request.getSession(false);
		String savedToken = (String)session.getAttribute(ONETIME_TOKEN);
		return requestToken == null ? savedToken == null : requestToken.equals(savedToken);
	}

	/**
	 * @param request リクエスト
	 * @return true:セッションなし
	 */
	public static boolean isSessionExpired(HttpServletRequest request){
		return getClientSessionBean(request) == null;
	}
	
	/**
	 * @param request リクエスト
	 * @return true:IPアドレスが変更された
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
	 * @param request リクエスト
	 * @return セッション情報
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
