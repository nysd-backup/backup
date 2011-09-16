/**
 * 
 */
package framework.api.dto;

import java.io.Serializable;

/**
 * クラインと層からのリクエスト情報.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class ClientRequestBean implements Serializable,Cloneable{

	private static final long serialVersionUID = -5810188274130263782L;

	/** トランザクションID */
	private String transactionId = null;
	
	/**
	 * @return トランザクションID
	 */
	public String getTransactionId(){
		return transactionId;
	}
	
	/**
	 * @param トランザクションID
	 */
	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public ClientRequestBean clone(){
		try {
			return (ClientRequestBean)(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}
