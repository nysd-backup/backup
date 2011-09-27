/**
 * Copyright 2011 the original author
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
 * @version 2011/08/31 created.
 */
public abstract class BeanUtils{
	
	/**
	 * @param <T> 型
	 * @param source コピー元
	 * @param targetClass コピー先クラス
	 * @param ignoreProperties 無視するフィールド名
	 * @return コピーされたオブジェクト
	 */
	public <T> T copyProperties(Object source, Class<T> targetClass,String... ignoreProperties){
		return copyProperties(source, targetClass,null,ignoreProperties);	
	}

	/**
	 * @param <T> 型
	 * @param source コピー元
	 * @param target コピー先
	 * @param ignoreProperties 無視するフィールド名
	 * @return コピーされたオブジェクト
	 */
	public static <T> T copyProperties(Object source, T target, String... ignoreProperties)	 {
		return copyProperties(source, target, null, ignoreProperties);
	}
	
	/**
	 * @param <T> 型
	 * @param source コピー元
	 * @param targetClass コピー先クラス
	 * @param converter 型変換エンジン
	 * @param ignoreProperties 無視するフィールド名
	 * @return コピーされたオブジェクト
	 */
	public static <T> T copyProperties(Object source, Class<T> targetClass,TypeConverter converter ,String... ignoreProperties){
		T target = ClassUtils.newInstance(targetClass);
		copyProperties(source, target,converter, ignoreProperties);
		return target;
	}

	/**
	 * @param <T> 型
	 * @param source コピー元
	 * @param target コピー先
	 * @param converter 型変換エンジン
	 * @param ignoreProperties 無視するフィールド名
	 * @return コピーされたオブジェクト
	 */
	public static <T> T copyProperties(Object source, T target, TypeConverter converter ,String... ignoreProperties){
		
		Method[] tms = target.getClass().getMethods();
		Method[] sms = source.getClass().getMethods();
		
		//sourceのgetter取得
		Map<String , Method> smsmap = new HashMap<String,Method>();
		Pattern ptget = Pattern.compile("^[get]([0-9a-zA-Z]+)");
		for(Method m : sms){
			Matcher matcher = ptget.matcher(m.getName());
			if (matcher.find()) {
				smsmap.put(matcher.group(1),m);
			}
		}

		//targetのsetter取得
		Pattern ptset = Pattern.compile("^[set]([0-9a-zA-Z]+)");
		for(Method setMethod : tms ){
			
			Matcher matcher = ptset.matcher(setMethod.getName());
			
			if (!matcher.find()) continue;
			
			String key = matcher.group(1);
			
			if( !smsmap.containsKey(key) )continue;
			
			//sourceのgetterで取得した値を変換してtargetのsetterで設定させる
			Method getMethod = smsmap.get(key);
			Object value;
			try {
				value = getMethod.invoke(source);
				if( converter != null){
					Object convertedValue = converter.convert(value, setMethod.getParameterTypes()[0]);	
					setMethod.invoke(target, convertedValue);			
				}
			} catch (Exception e) {
				throw new IllegalStateException("Failed to copy properties " ,e);
			}
		}
		
		return target;
	}

}
