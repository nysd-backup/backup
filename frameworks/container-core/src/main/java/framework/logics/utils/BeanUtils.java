/**
 * 
 */
package framework.logics.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.logics.converter.TypeConverter;


/**
 * Bean操作.
 *
 * @author yoshida-n
 * @version	2011/02/21 created.
 */
public abstract class BeanUtils{
	
	/**
	 * Copy the property values of the given source bean into the target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target bean
	 * @ if the copying failed
	 * @see BeanWrapper
	 */
	public <T> T copyProperties(Object source, Class<T> targetClass,String... ignoreProperties){
		T target = ReflectionUtils.instantiate(targetClass);
		copyProperties(source, target,ignoreProperties);
		return target;
	}

	/**
	 * Copy the property values of the given source bean into the given target bean,
	 * ignoring the given "ignoreProperties".
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target bean
	 * @param ignoreProperties array of property names to ignore
	 * @ if the copying failed
	 * @see BeanWrapper
	 */
	public static <T> T copyProperties(Object source, T target, String... ignoreProperties)	 {
		copyProperties(source, target, null, ignoreProperties);
		return target;
	}
	
	/**
	 * Copy the property values of the given source bean into the target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * <p>This is just a convenience method. For more complex transfer needs,
	 * consider using a full BeanWrapper.
	 * @param source the source bean
	 * @param target the target bean
	 * @ if the copying failed
	 * @see BeanWrapper
	 */
	public static <T> T copyProperties(Object source, Class<T> targetClass,Class<? extends TypeConverter> converter ,String... ignoreProperties){
		T target = ReflectionUtils.instantiate(targetClass);
		copyProperties(source, target,converter, ignoreProperties);
		return target;
	}

	/**
	 * Copy the property values of the given source bean into the given target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * @param source the source bean
	 * @param target the target bean
	 * @param editable the class (or interface) to restrict property setting to
	 * @param ignoreProperties array of property names to ignore
	 * @ if the copying failed
	 * @see BeanWrapper
	 */
	public static <T> T copyProperties(Object source, T target, Class<? extends TypeConverter> converterClass ,String... ignoreProperties){

		TypeConverter converter = null;
		if( converterClass != null){
			converter = ReflectionUtils.instantiate(converterClass);
		}
		
		Method[] tms = target.getClass().getMethods();
		Method[] sms = source.getClass().getMethods();
		Map<String , Method> smsmap = new HashMap<String,Method>();
		Pattern p = Pattern.compile("^[get]([0-9a-zA-Z]+)");
		for(Method m : sms){
			Matcher matcher = p.matcher(m.getName());
			if (matcher.find()) {
				smsmap.put(matcher.group(1),m);
			}
		}

		Pattern ptget = Pattern.compile("^[set]([0-9a-zA-Z]+)");
		for(Method m : tms ){
			
			Matcher matcher = ptget.matcher(m.getName());
			
			if (!matcher.find()) continue;
			
			String key = matcher.group(1);
			
			if( !smsmap.containsKey(key) )continue;
			
			//sourceのgetterで取得した値を変換してtargetのsetterで設定させる
			Method getMethod = smsmap.get(key);
			Object value = ReflectionUtils.invokeMethod(getMethod, source);
			if( converter != null){
				Object convertedValue = converter.convert(value, m.getParameterTypes()[0]);	
				ReflectionUtils.invokeMethod(m, target , convertedValue);
			}
		}
		
		return target;
	}

}
