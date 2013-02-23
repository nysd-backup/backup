/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;



/**
 * A selector for JMS destination from name of package. 
 * 
 * <pre>
 * 例:jms/org/xxx/org.coder.alpha.framework.core/alpha.domain
 * </pre>
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PackageDestinationNameResolver implements DestinationNameResolver{
	
	/** the pattern */
	private Pattern pattern;
	
	/**
	 * @param expression the expression for pattern
	 */
	public void setPattern(String expression){
		pattern = Pattern.compile(expression);
	}

	/**
	 * @see org.coder.alpha.framework.messaging.DestinationNameResolver#createDestinationName(java.lang.reflect.Method, java.io.Serializable[])
	 */
	@Override
	public String createDestinationName(Method target,MessagingProperty property) {
		
		//名称動的指定の場合
		String alias = property.getDynamicDestinationName();
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
		String name = property.getDynamicDestinationName();
		String prefix = property.getDestinationPrefix();
		if(StringUtils.isNotEmpty(name)){
			return String.format(name);
		}else {
			if(StringUtils.isNotEmpty(prefix)){
				return String.format("jms/%s/%s/%s/%s",prefix,dst,target.getDeclaringClass().getSimpleName(),target.getName());		
			}
		}			
		return String.format("jms/%s/%s/%s",dst,target.getDeclaringClass().getSimpleName(),target.getName());
	}
}
