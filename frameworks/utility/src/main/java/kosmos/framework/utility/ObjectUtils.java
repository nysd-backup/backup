/**
 * Copyright 2011 the original author
 */
package kosmos.framework.utility;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.SerializationUtils;

/**
 * Utility for Object.
 * 
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public final class ObjectUtils implements Utils.ObjectScope {
	/**
	 * コンストラクタ.
	 */
	private ObjectUtils() {
	}

	/**
	 * オブジェクトを比較する
	 * <p>
	 * ※どちらか一方でも<code>null</code>であれば<code>false</code>を返す
	 * </p>
	 * 
	 * <pre>
	 * ObjectUtils.equals(null, null)			-&gt; true
	 * ObjectUtils.equals(null, &quot;&quot;)			-&gt; false
	 * ObjectUtils.equals(&quot;&quot;, null)			-&gt; false
	 * ObjectUtils.equals(&quot;&quot;, &quot;&quot;)			-&gt; true
	 * ObjectUtils.equals(Boolean.TRUE, null)		-&gt; false
	 * ObjectUtils.equals(Boolean.TRUE, &quot;&quot;)		-&gt; false
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.TRUE)	-&gt; true
	 * ObjectUtils.equals(Boolean.TRUE, Boolean.FALSE)	-&gt; false
	 * </pre>
	 * 
	 * @param object1 the first object, may be <code>null</code>
	 * @param object2 the second object, may be <code>null</code>
	 * @return <code>true</code> if the values of both objects are the same
	 */
	public static boolean equals(Object object1, Object object2) {
		if (object1 == object2) {
			return true;
		}
		if ((object1 == null) || (object2 == null)) {
			return false;
		}
		return object1.equals(object2);
	}

	/**
	 * NULLチェックを行う
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isNull(null)	-&gt; true
	 * ObjectUtils.isNull(&quot;abc&quot;)	-&gt; false
	 * ObjectUtils.isNull(&quot;&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param obj オブジェクト
	 * @return <code>true</code>:<code>nullの場合</code>,<code>false</code>:<code>null</code>ではない場合
	 */
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * NULLチェックを行う
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isNotNull(null)	-&gt; false
	 * ObjectUtils.isNotNull(&quot;abc&quot;)	-&gt; true
	 * ObjectUtils.isNotNull(&quot;&quot;)	-&gt; true
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param obj オブジェクト
	 * @return <code>true</code>:<code>nullでないの場合</code>,<code>false</code>:<code>null</code>の場合
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	/**
	 * NULLチェックを行う
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isNotNull(null)	-&gt; false
	 * ObjectUtils.isNotNull(&quot;abc&quot;)	-&gt; true
	 * ObjectUtils.isNotNull(&quot;&quot;)	-&gt; true
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param obj オブジェクト
	 * @return <code>true</code>:<code>nullでないの場合</code>,<code>false</code>:<code>null</code>の場合
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object obj) {
		if(obj instanceof String){
			return !isNull(obj) && !((String)obj).isEmpty();
		}else if( obj instanceof Collection ){
			return !isNull(obj) && !((Collection)obj).isEmpty();
		}else if( obj instanceof Map){
			return !isNull(obj) && !((Map)obj).isEmpty();
		}
		return !isNull(obj);
	}

	/**
	 * NULLチェックを行う
	 * <p>
	 * ※引数で渡されたすべてのオブジェクトが<code>null</code>かどうかをチェックする<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isAllNull(null, null, null)-&gt; true
	 * ObjectUtils.isAllNull(&quot;&quot;, null, null)	-&gt; true
	 * ObjectUtils.isAllNull(&quot;&quot;, null, &quot;abc&quot;)	-&gt; true
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param objects オブジェクト配列（可変長引数）
	 * @return <code>true</code>:すべて<code>null</code>
	 */
	public static boolean isAllNull(Object... objects) {
		for (Object obj : objects) {
			if (!isNull(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * NULLまたは空文字チェックを行う
	 * <p>
	 * ※引数で渡されたすべての文字列が<code>null</code>または空文字かどうかをチェックする<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isAllEmpty(null, &quot;&quot;, null)		-&gt; true
	 * ObjectUtils.isAllEmpty(null, &quot;abc&quot;, &quot;&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param strings <code>String</code>配列（可変長引数）
	 * @return <code>true</code>:すべてNULLまたは空文字
	 */
	public static boolean isAllEmpty(String... strings) {
		for (String str : strings) {
			if (!StringUtils.isEmpty(str)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * NULLチェックを行う
	 * <p>
	 * ※引数で渡されたオブジェクトが少なくとも１つが<code>null</code>かどうかをチェックする<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isLeastNull(null, null, null)	-&gt; true
	 * ObjectUtils.isLeastNull(null, &quot;abc&quot;, &quot;def&quot;)	-&gt; true
	 * ObjectUtils.isLeastNull(&quot;&quot;, &quot;abc&quot;, &quot;def&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param objects オブジェクト配列（可変長引数）
	 * @return <code>true</code>:少なくとも１つのオブジェクトが<code>null</code>
	 */
	public static boolean isLeastNull(Object... objects) {
		for (Object obj : objects) {
			if (isNull(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * NULLまたは空文字チェックを行う
	 * <p>
	 * ※引数で渡されたオブジェクトが少なくとも１つが<code>null</code>または空文字かどうかをチェックする<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.isLeastEmpty(&quot;&quot;, &quot;&quot;, &quot;&quot;)		-&gt; true
	 * ObjectUtils.isLeastEmpty(&quot;&quot;, &quot;abc&quot;, null)	-&gt; true
	 * ObjectUtils.isLeastEmpty(&quot;abc&quot;, &quot;def&quot;, null)	-&gt; true
	 * ObjectUtils.isLeastEmpty(&quot;abc&quot;, &quot;def&quot;)		-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param strings <code>String</code>配列（可変長引数）
	 * @return <code>true</code>:少なくとも１つの文字列が<code>null</code>または空文字
	 */
	public static boolean isLeastEmpty(String... strings) {
		for (String str : strings) {
			if (StringUtils.isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * オブジェクトの文字列表現を取得する
	 * <p>
	 * ※<code>obj</code>が<code>null</code>の場合は空文字(&quot;&quot)を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * ObjectUtils.toString(null)		-&gt; &quot;&quot;
	 * ObjectUtils.toString(&quot;&quot;)			-&gt; &quot;&quot;
	 * ObjectUtils.toString(&quot;bat&quot;)		-&gt; &quot;bat&quot;
	 * ObjectUtils.toString(Boolean.TRUE)	-&gt; &quot;true&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param obj the Object to <code>toString</code>, may be null
	 * @return the passed in Object's toString, or nullStr if <code>null</code> input
	 * @since 2.0
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * コピー元のオブジェクトを指定して、新規にオブジェクトを作成する
	 * 
	 * @param <T> テンプレート
	 * @param source コピー元オブジェクト
	 * @param cls コピー元オブジェクトクラスタイプ
	 * @return 生成したオブジェクト
	 */
	public static <T> T createObject(Object source, Class<T> cls) {
		return createObject(source, cls, source.getClass());
	}

	/**
	 * コピー元のオブジェクトを指定して、新規にオブジェクトを作成する
	 * 
	 * @param <T> テンプレート
	 * @param source コピー元オブジェクト
	 * @param cls コピー元オブジェクトクラスタイプ
	 * @param sourceClass コピーするプロパティを持つクラス
	 * @return 生成したオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createObject(Object source, Class<T> cls, Class<?> sourceClass) {
		Object dest = null;
		try {
			dest = cls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(cls.getSimpleName() + " has't default constractor.", e);
		}

		return copyObject(source, (T) dest, sourceClass);
	}

	/**
	 * コピー元のオブジェクトを指定して、生成されたオブジェクトへコピーする
	 * 
	 * @param <T> テンプレート
	 * @param source コピー元オブジェクト
	 * @param dest コピー先オブジェクト（生成しておく事）
	 * @return 生成したオブジェクト
	 */
	public static <T> T copyObject(Object source, T dest) {
		return copyObject(source, dest, source.getClass());
	}

	/**
	 * コピー元のオブジェクトとコピーするプロパティを持つクラスを指定して、 生成したオブジェクトへコピーする
	 * 
	 * @param <T> テンプレート
	 * @param source コピー元オブジェクト
	 * @param dest コピー先オブジェクト（生成しておく事）
	 * @param sourceClass コピーするプロパティを持つクラス
	 * @return 生成したオブジェクト
	 */
	public static <T> T copyObject(Object source, T dest, Class<?> sourceClass) {
		PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

		PropertyDescriptor[] propertyDescriptors = propertyUtilsBean.getPropertyDescriptors(sourceClass);

		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String name = propertyDescriptor.getName();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			// リストはコピーしない
			if (Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
				continue;
			}

			if (propertyUtilsBean.isReadable(source, name) && propertyUtilsBean.isWriteable(dest, name)) {
				try {
					Object value = propertyUtilsBean.getSimpleProperty(source, name);
					// Longなどのラッパー型ではnullの場合デフォルト値で埋められるため、nullコピー
					if (value == null && propertyDescriptor.getPropertyType().equals(Long.class)) {
						propertyUtilsBean.setSimpleProperty(dest, name, null);
						continue;
					}

					BeanUtils.copyProperty(dest, name, value);
				} catch (ConversionException e) {
					continue;// Converterの変換エラーは無視する
				} catch (Exception e) {
					// noop
				}
			}
		}

		return dest;
	}

	/**
	 * オブジェクトのクローンを作成する（DeepCopy）
	 * <p>
	 * ※createObject、copyObjectとは異なり、内部のパラメータ及び リストも含めてコピーする
	 * </p>
	 * 
	 * @param <T> 型
	 * @param src コピー元オブジェクト
	 * @return コピー先オブジェクト
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneObject(T src) {
		return (T) SerializationUtils.clone(src);
	}

	/**
	 * リストのクローンを作成する（DeepCopy）
	 * <p>
	 * ※リストの要素をDeepCopyする
	 * </p>
	 * 
	 * @param <T> 型
	 * @param srcList コピー元のリスト
	 * @return コピー先のリスト
	 */
	public static <T extends Serializable> List<T> cloneList(List<T> srcList) {
		List<T> cloneList = new ArrayList<T>();
		for (T src : srcList) {
			cloneList.add(cloneObject(src));
		}

		return cloneList;
	}
}
