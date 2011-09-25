/**
 * Use is subject to license terms.
 */
package framework.logics.builder;

import java.util.Locale;

import framework.core.message.BuildedMessage;
import framework.core.message.DefinedMessage;
import framework.core.message.MessageBean;

/**
 * メッセージビルダー.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public interface MessageBuilder {

	/**
	 * メッセージロード
	 * @param bean メッセージビーン
	 * @param locale ロケール
	 * @return メッセージ
	 */
	public DefinedMessage load(MessageBean bean, Locale locale);
	
	/**
	 * メッセージロード
	 * @param bean メッセージビーン
	 * @param locale ロケール
	 * @param baseFileName メッセージファイル名
	 * @return メッセージ
	 */
	public DefinedMessage load(MessageBean bean, Locale locale, String baseFileName);

	/**
	 * メッセージビルド
	 * @param defined メッセージ定義
	 * @return 出力されたメッセージ
	 */
	public BuildedMessage build(DefinedMessage defined);
}
