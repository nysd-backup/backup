/**
 * Use is subject to license terms.
 */
package framework.service.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * クエリ用ユーティリティ.
 *
 * @author yoshida-n
 * @version	2011/06/21 created.
 */
public class QueryUtils {

	/** 日付用フォーマット */
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	/**
	 * @param params　パラメータ
	 * @param sqlString preparedStatement用のSQL
	 * @return ?がバインドパラメータに置換された完全SQL
	 */
	public static final String applyValues(List<Object> params , String sqlString){

		Iterator<Object> ite = params.iterator();
		String converted = sqlString;
		
		//?にパラメータを埋め込む
		while(converted.contains("?")){
			if( !ite.hasNext() ){
				throw new IllegalStateException("count of ? is different from parameter count");
			}
			Object v = ite.next();
			if( v instanceof String ){
				converted = StringUtils.replaceOnce(converted, "?", "\'" + v.toString() + "\'");
			} else if (v instanceof Date) {
				converted = StringUtils.replaceOnce(converted, "?", format.format((Date) v));			
			}else{
				converted = StringUtils.replaceOnce(converted, "?", String.valueOf(v));
			}	
		}	
		return converted;
	}
}
