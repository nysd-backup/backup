package framework.api.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * クライアントとのセッションに使用する情報.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ClientSessionBean implements Serializable,Cloneable{

	private static final long serialVersionUID = -5810188274130263782L;
	
	/** ユーザID */
	private String userId = null;
	
	/** セッション作成時のIPアドレス */
	private String sessionClientIp = null;
	
	/** ロケール */
	private Locale locale = null;
	
	/** ビジネスロジック層に連携しないデータ */
	private Map<String,Object> webData = new HashMap<String,Object>();
	
	/**
	 * @return クローンを取得する、webDataは取得しない
	 */
	protected ClientSessionBean cloneExceptWebData(){
		try {
			ClientSessionBean clone = (ClientSessionBean)(super.clone());
			clone.webData.clear();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * @param <T>　型
	 * @param key キー
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getWebData(Enum<?> key){
		return (T)webData.get(key.name());
	}
	
	/**
	 * @param <T> 型
	 * @param key キー
	 * @param value 値
	 */
	protected void putWebData(Enum<?> key, Object value){
		webData.put(key.name(), value);
	}

	/**
	 * @return ロケール
	 */
	public Locale getLocale(){
		return locale;
	}
	
	/**
	 * @param locale ロケール
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

}
