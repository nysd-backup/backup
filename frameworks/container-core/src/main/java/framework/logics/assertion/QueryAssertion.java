/**
 * Use is subject to license terms.
 */
package framework.logics.assertion;

import java.util.List;

import framework.core.entity.AbstractEntity;
import framework.core.message.MessageBean;
import framework.sqlclient.api.free.NativeResult;

/**
 * クエリのアサーション.
 *
 * @author yoshida-n
 * @version	2011/05/12 created.
 */
public interface QueryAssertion {

	/**
	 * 0件時業務エラーとする.
	 * 
	 * @param result　検索結果
	 * @param message メッセージ
	 */
	public void throwIfEmpty(AbstractEntity result , MessageBean message);
	
	/**
	 * 0件時業務エラーとする.
	 * 
	 * @param result　検索結果
	 * @param message メッセージ
	 */
	public void throwIfEmpty(List<AbstractEntity> result , MessageBean message);
	
	/**
	 * 存在する時業務エラーとする.
	 * 
	 * @param result　検索結果
	 * @param message メッセージ
	 */
	public void throwIfExists(AbstractEntity result , MessageBean message);
	
	/**
	 * 存在する時業務エラーとする.
	 * 
	 * @param result　検索結果
	 * @param message メッセージ
	 */
	public void throwIfExists(List<AbstractEntity> result , MessageBean message);
	
	/**
	 * 検索結果超過時業務エラーとする.
	 * 
	 * @param result　検索結果
	 * @param message メッセージ
	 */
	public void throwIfLimitted(NativeResult<?> result , MessageBean message);
	
	
}
