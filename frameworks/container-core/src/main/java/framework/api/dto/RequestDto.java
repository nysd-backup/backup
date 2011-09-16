/**
 * 
 */
package framework.api.dto;

import java.io.Serializable;


/**
 * クライアントからのリクエスト.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class RequestDto implements Serializable{

	private static final long serialVersionUID = -5671768150819629678L;
	
	/** パラメータ */
	private final Serializable[] parameter;

	/** 実行対象クラス */
	private final Class<?> targetClass;
	
	/** 別名*/
	private final String alias;
	
	/** メソッド名 */
	private final String methodName;
	
	/** パラメータタイプ */
	private final Class<?>[] parameterTypes;
	
	/** クライアントとのセッション情報 */
	private final ClientSessionBean clientSessionBean;
	
	/** クライアントとのリクエスト情報 */
	private final ClientRequestBean webRequestBean;
	
	/**
	 * @param targetClass サービスインターフェース
	 * @param param パラメータ
	 * @param serviceName サービス名
	 * @param methodName メソッド名
	 * @param clientSessionBean クライアントセッション
	 * @param webRequestBean WEB層のパラメータ
	 */
	public RequestDto(Class<?> targetClass ,Class<?>[] parameterTypes , Serializable[] param , String alias , String methodName ,ClientSessionBean clientSessionBean,ClientRequestBean webRequestBean){
		this.parameter = param;
		this.parameterTypes = parameterTypes;
		this.targetClass = targetClass;
		this.alias = alias;
		this.methodName = methodName;	
		this.clientSessionBean = clientSessionBean != null ? clientSessionBean.cloneExceptWebData() : clientSessionBean;
		this.webRequestBean = webRequestBean != null ? webRequestBean.clone() : webRequestBean;
	}
	
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
	 * @return the clientSessionBean
	 */
	public ClientSessionBean getClientSessionBean() {
		return clientSessionBean;
	}

	/**
	 * @return the clientRequestBean
	 */
	public ClientRequestBean getClientRequestBean() {
		return webRequestBean;
	}

	/**
	 * @return the targetClass
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}
}
