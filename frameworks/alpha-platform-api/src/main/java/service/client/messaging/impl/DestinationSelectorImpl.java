/**
 * Copyright 2011 the original author
 */
package service.client.messaging.impl;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import service.client.messaging.DestinationPrefix;
import service.client.messaging.DestinationSelector;
import service.client.messaging.MessagingProperty;

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
	
	public static final String DESTIONATION_ALIAS = "alpha.destination.alias";
	
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
	public String createDestinationName(Method target,MessagingProperty property) {
		
		String alias = property.getDynamicDestinatonName();
		if(StringUtils.isNotEmpty(alias)){
			return alias;
		}
		
		//正規表現指定の場合
		String dst = target.getDeclaringClass().getPackage().getName().replace('.', '/');
		if(pattern != null){
			Matcher matcher = pattern.matcher(dst);
			if( matcher.find()){
				dst = matcher.group(1);
			}
		}
		
		//プリフィクス指定の場合
		DestinationPrefix prefix = target.getAnnotation(DestinationPrefix.class);
		if( prefix != null){
			return String.format("jms/%s/%s/%s/%s",prefix,dst,target.getDeclaringClass().getSimpleName(),target.getName());
		}else{
			return String.format("jms/%s/%s/%s",dst,target.getDeclaringClass().getSimpleName(),target.getName());
		}
	}

}
