/**
 * Copyright 2011 the original author
 */
package framework.service.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Utility for query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class QueryUtils {

	/** the date format */
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	/**
	 * @param params　the parameter
	 * @param sqlString the SQL for <code>PreparedStatement</code>
	 * @return the SQL binded value to ?
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
