/**
 * Use is subject to license terms.
 */
package framework.sqlclient.api.free;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * クエリを示すアノテーション.
 * NamedQuery/NamedNativeQueryとは排他となる
 *
 * @author yoshida-n
 * @version	2011/06/21 created.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousQuery {

	/** SQLおよびSQLファイルパス */
    String query();

    /** 結果格納クラス (Any Bean or Map is available). */
    @SuppressWarnings("rawtypes")
	Class resultClass() default void.class; 
    
}
