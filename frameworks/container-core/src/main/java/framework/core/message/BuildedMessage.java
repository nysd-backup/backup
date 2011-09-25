/**
 * Use is subject to license terms.
 */
package framework.core.message;

/**
 * 完成したメッセージ.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class BuildedMessage{

	private static final long serialVersionUID = 1L;

	/**　完成したメッセージ */
	private final String buildedMessage;

	/** メッセージ定義 */
	private final DefinedMessage defined;
	
	/**
	 * @param defined 定義されたメッセージ 
	 * @param buildedMessage 完成したメッセージ
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
