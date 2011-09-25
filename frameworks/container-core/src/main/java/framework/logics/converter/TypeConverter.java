/**
 * Use is subject to license terms.
 */
package framework.logics.converter;

/**
 * 型変換エンジン.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public interface TypeConverter {

	
	/**
	 * @param <T>　型
	 * @param value　値
	 * @param expectedType キャスト対象のクラス
	 * @return 変換後の値
	 */
	public <T> T convert(Object value , Class<T> expectedType);
	
	
}
