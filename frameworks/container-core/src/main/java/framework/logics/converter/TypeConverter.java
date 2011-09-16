/**
 * 
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
	 * @param value
	 * @param castClass
	 * @return
	 */
	public <T> T convert(Object value , Class<T> expectedType);
	
	
}
