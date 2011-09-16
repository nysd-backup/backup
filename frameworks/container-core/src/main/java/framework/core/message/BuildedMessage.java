/**
 * 
 */
package framework.core.message;

/**
 * メッセージ.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class BuildedMessage{

	private static final long serialVersionUID = 1L;

	/** 生成されたメッセージ */
	private final String buildedMessage;

	/** メッセージ定義 */
	private final DefinedMessage defined;
	
	/**
	 * @param code コード
	 * @param message メッセージ
	 * @param level レベル
	 */
	public BuildedMessage(DefinedMessage defined, String buildedMessage){
		this.defined = defined;
		this.buildedMessage = buildedMessage;
	}

	/**
	 * @return the buildedMessage
	 */
	public String getBuildedMessage() {
		return buildedMessage;
	}

	/**
	 * @return the defined
	 */
	public DefinedMessage getDefined() {
		return defined;
	}

}
