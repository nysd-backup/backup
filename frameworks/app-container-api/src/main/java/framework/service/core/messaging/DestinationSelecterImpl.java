/**
 * Copyright 2011 the original author
 */
package framework.service.core.messaging;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * パッケージ名から宛先を生成する.
 * 
 * <pre>
 * 例:jms/org/xxx/framework/service
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DestinationSelecterImpl implements DestinationSelecter{
	
	/** パターン */
	private Pattern pattern;
	
	/**
	 * @param expression 正規表現
	 */
	public void setPattern(String expression){
		pattern = Pattern.compile(expression);
	}

	/**
	 * @see framework.service.core.messaging.DestinationSelecter#createDestinationName(java.lang.reflect.Method)
	 */
	@Override
	public String createDestinationName(Method target) {
		
		//正規表現指定の場合
		String dst = target.getDeclaringClass().getPackage().getName().replace('.', '/');
		if(pattern != null){
			Matcher matcher = pattern.matcher(dst);
			if( matcher.find()){
				dst = matcher.group(1);
			}
		}
		
		//プリフィクス指定の場合
		Prefix prefix = target.getAnnotation(Prefix.class);
		if( prefix != null){
			return String.format("jms/%s/%s",prefix,dst);
		}else{
			return String.format("jms/%s",dst);
		}
	}

}
