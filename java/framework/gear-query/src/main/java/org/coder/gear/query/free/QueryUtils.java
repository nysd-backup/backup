/**

 * Copyright 2012 the original author
 */
package org.coder.gear.query.free;

import java.beans.PropertyDescriptor;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * Utility.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class QueryUtils {

	/**
	 * Sets the parameters
	 * @param values values
	 * @param arguments the arguments
	 */
	public static void setParameter(Map<String,Object> values ,Object arguments){
		PropertyUtilsBean bean = new PropertyUtilsBean();
		PropertyDescriptor[] properties = bean.getPropertyDescriptors(arguments.getClass());
		for(PropertyDescriptor p : properties){
			String name = p.getName();
			if("class".equals(name)){
				continue;
			}
			if(bean.isReadable(arguments, name)){
				try {
					values.put(name, p.getReadMethod().invoke(arguments));
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}
	}
}
