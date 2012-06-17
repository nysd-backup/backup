/**
 * Copyright 2011 the original author
 */
package service.client.messaging;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A selector for JMS destination from name of package. 
 * 
 * <pre>
 * 例:jms/org/xxx/core/service
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class DestinationSelectorImpl implements DestinationSelector{
	
	/** the pattern */
	private Pattern pattern;
	
	/**
	 * @param expression the expression for pattern
	 */
	public void setPattern(String expression){
		pattern = Pattern.compile(expression);
	}

	/**
	 * @see service.client.messaging.DestinationSelector#createDestinationName(java.lang.reflect.Method, java.io.Serializable[])
	 */
	@Override
	public String createDestinationName(Method target,Serializable[] parameter) {
		
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
			return String.format("jms/%s/%s/%s/%s",prefix,dst,target.getDeclaringClass().getSimpleName(),target.getName());
		}else{
			return String.format("jms/%s/%s/%s",dst,target.getDeclaringClass().getSimpleName(),target.getName());
		}
	}

}
