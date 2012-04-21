/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Utility for Reflection.
 *
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public final class ReflectionUtils {

	/**
	 * インスタンス生成不可.
	 */
	private ReflectionUtils() {
	}
	
	/**
	 * @param m
	 * @return
	 */
	public static boolean isSetter(Method m){
		if( Modifier.isPublic(m.getModifiers()) ){
			return m.getName().startsWith("set");			
		}
		return false;
	}
	
	/**
	 * @param m
	 * @return
	 */
	public static String getPropertyNameFromGetter(Method m){
		if(!isGetter(m)){
			throw new IllegalArgumentException();
		}
		String name = m.getName();
		int position = name.startsWith("is") ? 2 : 3; 
		return StringUtils.uncapitalize(name.substring(position));
	}
	
	/**
	 * @param m
	 * @return
	 */
	public static String getPropertyNameFromSetter(Method m){
		if(!isSetter(m)){
			throw new IllegalArgumentException();
		}
		String name = m.getName();
		return StringUtils.uncapitalize(name.substring(3));
	}
	

	/**
	 * @param m
	 * @return
	 */
	public static boolean isGetter(Method m){
		String name = m.getName();
		if( name.startsWith("get")){
			return true;
		}
		if( m.getReturnType().getSimpleName().equals("boolean") || m.getReturnType().getSimpleName().equals("Boolean")){
			if(name.startsWith("is")){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param <T> 型
	 * @param t Class<T>
	 * @return T
	 */
	public static <T> T newInstance(Class<T> t) {
		try {
			return t.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(t.getName(), e);
		}
	}
	
	/**
	 * @param clazz
	 * @param an
	 * @return
	 */
	public static List<Method> getAnotatedGetter(Class<?> clazz , Class<? extends Annotation> an){
		Method[] ms = clazz.getDeclaredMethods();
		List<Method> mList = new ArrayList<Method>();
		for(Method m : ms){
			if(m.getAnnotation(an) != null && isGetter(m)){
				mList.add(m);
			}
			
		}
		return mList;
	}
	
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with the supplied
	 * arguments. The target object can be <code>null</code> when invoking a static {@link Method}.
	 * <p>
	 * Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * 
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}
}
