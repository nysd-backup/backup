/**
 * Copyright 2011 the original author
 */
package kosmos.framework.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * @param target Object
	 * @param name String
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPublicFieldValue(Object target, String name) {
		Field f = findPublicField(target.getClass(), name);
		if (f == null) {
			throw new IllegalArgumentException("Field not found");
		}
		return (T) getField(f, target);
	}

	/**
	 * @param type Class<?>
	 * @param name String
	 * @return Field
	 */
	public static Field findPublicField(Class<?> type, String name) {
		try {
			return type.getField(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("No such field ", e);
		}
	}

	/**
	 * @param type クラス
	 * @param target 対象クラス
	 * @return フィールド
	 */
	public static Field[] getTypedFields(Class<?> type, Class<?> target) {
		Field[] fields = target.getFields();
		if (fields == null) {
			return null;
		}
		List<Field> ret = new ArrayList<Field>();
		for (Field f : fields) {
			if (type.isAssignableFrom(f.getType())) {
				ret.add(f);
			}
		}
		Field[] fs = new Field[0];
		return ret.toArray(fs);
	}

	/**
	 * @param <T> 型
	 * @param f Field
	 * @param target Object
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Field f, Object target) {
		f.setAccessible(true);
		return (T) getField(f, target);
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
	 * @param bean Object
	 * @param method String
	 * @return Method
	 */
	public static Method getMethod(Object bean, String method) {
		try {
			return bean.getClass().getMethod(method);
		} catch (NoSuchMethodException nsme) {
			throw new RuntimeException(bean.getClass().getName() + " : " + method, nsme);
		}
	}

	/**
	 * @param base Object
	 * @param f Field
	 * @param param Object
	 */
	public static void set(Object base, Field f, Object param) {
		f.setAccessible(true);
		setField(f, base, param);
	}

	/**
	 * スーパークラスも含め宣言されたすべてのフィールドを返却する。
	 * 
	 * @param clazz クラス
	 * @return フィールド
	 */
	public static Field[] getAllDeclaredField(Class<?> clazz) {

		return getAllAnotatedField(clazz, null);

	}

	/**
	 * スーパークラスも含め、アノテーションが設定されていない全てのフィールドを取得する。
	 * 
	 * @param clazz クラス
	 * @param an annotation
	 * @return フィールド
	 */
	public static Field[] getExceptAnotatedField(Class<?> clazz, Class<? extends Annotation> an) {
		return getAllAnnotatedTypedField(clazz, an, null, false);
	}

	/**
	 * スーパークラスも含め、アノテーションが設定された全てのフィールドを取得する。
	 * 
	 * @param clazz クラス
	 * @param an annotation
	 * @return フィールド
	 */
	public static Field[] getAllAnotatedField(Class<?> clazz, Class<? extends Annotation> an) {
		return getAllAnnotatedTypedField(clazz, an, null, true);
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

	/**
	 * スーパークラスも含め、アノテーションが設定された全てのフィールドを取得する。
	 * 
	 * @param clazz クラス
	 * @param type Class<?>
	 * @return フィールド
	 */
	public static Field[] getAllTypedField(Class<?> clazz, Class<?> type) {
		return getAllAnnotatedTypedField(clazz, null, type, true);
	}

	/**
	 * スーパークラスも含め宣言されたフィールドのうち、指定した型のフィールドを取得する
	 * 
	 * @param obj Object
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getAllFieldInMap(Object obj) {

		Class<?> clazz = obj.getClass();
		Map<String, Object> ls = new HashMap<String, Object>();
		for (Class<?> cls = clazz; cls != Object.class; cls = cls.getSuperclass()) {

			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {
				// except static
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				// except final
				if (Modifier.isFinal(f.getModifiers())) {
					continue;
				}
				// except final
				if (Modifier.isInterface(f.getModifiers())) {
					continue;
				}

				f.setAccessible(true);
				Object value = get(f, obj);
				ls.put(f.getName(), value);
			}
		}
		return ls;
	}

	/**
	 * スーパークラスも含め宣言されたフィールドのうち、指定した型のフィールドを取得する
	 * 
	 * @param obj Object
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getAllFieldInMapIncludeFinal(Object obj) {

		Class<?> clazz = obj.getClass();
		Map<String, Object> ls = new HashMap<String, Object>();
		for (Class<?> cls = clazz; cls != Object.class; cls = cls.getSuperclass()) {

			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {
				// except static
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				// except final
				if (Modifier.isInterface(f.getModifiers())) {
					continue;
				}

				f.setAccessible(true);
				Object value = get(f, obj);
				ls.put(f.getName(), value);
			}
		}
		return ls;
	}

	/**
	 * スーパークラスも含め宣言されたフィールドのうち、指定した型のフィールドを取得する.
	 * 
	 * @param clazz Class<?>
	 * @param an Class<? extends Annotation>
	 * @param type Class<?>
	 * @param equals boolean
	 * @return Field
	 */
	public static Field[] getAllAnnotatedTypedField(Class<?> clazz, Class<? extends Annotation> an, Class<?> type, boolean equals) {

		List<Field> ls = new ArrayList<Field>();

		for (Class<?> cls = clazz; cls != Object.class; cls = cls.getSuperclass()) {

			Field[] fs = cls.getDeclaredFields();
			for (Field f : fs) {

				// except static
				if (Modifier.isStatic(f.getModifiers())) {
					continue;
				}
				// except interface
				if (Modifier.isInterface(f.getModifiers())) {
					continue;
				}

				if (type != null) {
					if (f.getType().equals(type) == false) {
						continue;
					}
				}
				// アクセス可能にする
				if (an != null) {
					if (equals) {
						if (f.getAnnotation(an) == null) {
							continue;
						}
					} else {
						if (f.getAnnotation(an) != null) {
							continue;
						}
					}
				}

				f.setAccessible(true);
				ls.add(f);
			}

		}
		return ls.toArray(new Field[]{});
	}

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the supplied <code>name</code>. Searches all superclasses up to
	 * {@link Object}.
	 * 
	 * @param clazz the class to introspect
	 * @param name the name of the field
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class<?> clazz, String name) {
		return findField(clazz, name, null);
	}

	/**
	 * Attempt to find a {@link Field field} on the supplied {@link Class} with the supplied <code>name</code> and/or {@link Class type}. Searches all
	 * superclasses up to {@link Object}.
	 * 
	 * @param clazz the class to introspect
	 * @param name the name of the field (may be <code>null</code> if type is specified)
	 * @param type the type of the field (may be <code>null</code> if name is specified)
	 * @return the corresponding Field object, or <code>null</code> if not found
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Set the field represented by the supplied {@link Field field object} on the specified {@link Object target object} to the specified
	 * <code>value</code>. In accordance with {@link Field#set(Object, Object)} semantics, the new value is automatically unwrapped if the
	 * underlying field has a primitive type.
	 * <p>
	 * Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
	 * 
	 * @param field the field to set
	 * @param target the target object on which to set the field
	 * @param value the value to set; may be <code>null</code>
	 */
	public static void setField(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * Get the field represented by the supplied {@link Field field object} on the specified {@link Object target object}. In accordance with
	 * {@link Field#get(Object)} semantics, the
	 * returned value is automatically wrapped if the underlying field has a primitive type.
	 * <p>
	 * Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
	 * 
	 * @param field the field to get
	 * @param target the target object from which to get the field
	 * @return the field's current value
	 */
	public static Object getField(Field field, Object target) {
		try {
			return field.get(target);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
			throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
		}
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name and no
	 * parameters. Searches all superclasses up to <code>Object</code>.
	 * <p>
	 * Returns <code>null</code> if no {@link Method} can be found.
	 * 
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name and parameter
	 * types. Searches all superclasses up to <code>Object</code>.
	 * <p>
	 * Returns <code>null</code> if no {@link Method} can be found.
	 * 
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @param paramTypes the parameter types of the method (may be <code>null</code> to indicate any
	 *            signature)
	 * @return the Method object, or <code>null</code> if none found
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with no arguments. The
	 * target object can be <code>null</code> when invoking a static {@link Method}.
	 * <p>
	 * Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * 
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
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
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied target object with no
	 * arguments.
	 * 
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @throws SQLException the JDBC API SQLException to rethrow (if any)
	 * @see #invokeJdbcMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target) throws SQLException {
		return invokeJdbcMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified JDBC API {@link Method} against the supplied target object with the
	 * supplied arguments.
	 * 
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be <code>null</code>)
	 * @return the invocation result, if any
	 * @throws SQLException the JDBC API SQLException to rethrow (if any)
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeJdbcMethod(Method method, Object target, Object... args) throws SQLException {
		try {
			return method.invoke(target, args);
		} catch (IllegalAccessException ex) {
			handleReflectionException(ex);
		} catch (InvocationTargetException ex) {
			if (ex.getTargetException() instanceof SQLException) {
				throw (SQLException) ex.getTargetException();
			}
			handleInvocationTargetException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Handle the given reflection exception. Should only be called if no checked exception is
	 * expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of an InvocationTargetException with such a root cause. Throws an IllegalStateException
	 * with an appropriate message else.
	 * 
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no checked exception
	 * is expected to be thrown by the target method.
	 * <p>
	 * Throws the underlying RuntimeException or Error in case of such a root cause. Throws an IllegalStateException else.
	 * 
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if
	 * no checked exception is expected to be thrown by the target method.
	 * <p>
	 * Rethrows the underlying exception cast to an {@link RuntimeException} or {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * 
	 * @param ex the exception to rethrow
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if
	 * no checked exception is expected to be thrown by the target method.
	 * <p>
	 * Rethrows the underlying exception cast to an {@link Exception} or {@link Error} if appropriate; otherwise, throws an
	 * {@link IllegalStateException}.
	 * 
	 * @param ex the exception to rethrow
	 * @throws Exception the rethrown exception (in case of a checked exception)
	 */
	public static void rethrowException(Throwable ex) throws Exception {
		if (ex instanceof Exception) {
			throw (Exception) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		handleUnexpectedException(ex);
	}

	/**
	 * Throws an IllegalStateException with the given exception as root cause.
	 * 
	 * @param ex the unexpected exception
	 */
	private static void handleUnexpectedException(Throwable ex) {
		throw new IllegalStateException("Unexpected exception thrown", ex);
	}

	/**
	 * Determine whether the given method explicitly declares the given exception or one of its
	 * superclasses, which means that an exception of that type can be propagated as-is within a
	 * reflective invocation.
	 * 
	 * @param method the declaring method
	 * @param exceptionType the exception to throw
	 * @return <code>true</code> if the exception can be thrown as-is; <code>false</code> if it
	 *         needs to be wrapped
	 */
	public static boolean declaresException(Method method, Class<?> exceptionType) {		
		Class<?>[] declaredExceptions = method.getExceptionTypes();
		for (Class<?> declaredException : declaredExceptions) {
			if (declaredException.isAssignableFrom(exceptionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determine whether the given field is a "public static final" constant.
	 * 
	 * @param field the field to check
	 * @return boolean
	 */
	public static boolean isPublicStaticFinal(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
	}

	/**
	 * Determine whether the given method is an "equals" method.
	 * 
	 * @param method Method
	 * @return boolean
	 */
	public static boolean isEqualsMethod(Method method) {
		if (method == null || !method.getName().equals("equals")) {
			return false;
		}
		Class<?>[] paramTypes = method.getParameterTypes();
		return paramTypes.length == 1 && paramTypes[0] == Object.class;
	}

	/**
	 * Determine whether the given method is a "hashCode" method.
	 * 
	 * @param method Method
	 * @return boolean
	 */
	public static boolean isHashCodeMethod(Method method) {
		return method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0;
	}

	/**
	 * Determine whether the given method is a "toString" method.
	 * 
	 * @param method Method
	 * @return boolean
	 */
	public static boolean isToStringMethod(Method method) {
		return method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0;
	}

	/**
	 * Make the given field accessible, explicitly setting it accessible if necessary. The <code>setAccessible(true)</code> method is only called when
	 * actually necessary, to avoid
	 * unnecessary conflicts with a JVM SecurityManager (if active).
	 * 
	 * @param field the field to make accessible
	 * @see java.lang.reflect.Field#setAccessible
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid
	 * unnecessary conflicts with a JVM SecurityManager (if active).
	 * 
	 * @param method the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Make the given constructor accessible, explicitly setting it accessible if necessary. The <code>setAccessible(true)</code> method is only
	 * called when actually necessary, to avoid
	 * unnecessary conflicts with a JVM SecurityManager (if active).
	 * 
	 * @param ctor the constructor to make accessible
	 * @see java.lang.reflect.Constructor#setAccessible
	 */
	public static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}

	/**
	 * Perform the given callback operation on all matching methods of the given class and
	 * superclasses.
	 * <p>
	 * The same named method occurring on subclass and superclass will appear twice, unless excluded by a {@link MethodFilter}.
	 * 
	 * @param clazz class to start looking at
	 * @param mc the callback to invoke for each method
	 * @see #doWithMethods(Class, MethodCallback, MethodFilter)
	 */
	public static void doWithMethods(Class<?> clazz, MethodCallback mc) {
		doWithMethods(clazz, mc, null);
	}

	/**
	 * Perform the given callback operation on all matching methods of the given class and
	 * superclasses.
	 * <p>
	 * The same named method occurring on subclass and superclass will appear twice, unless excluded by the specified {@link MethodFilter}.
	 * 
	 * @param clazz class to start looking at
	 * @param mc the callback to invoke for each method
	 * @param mf the filter that determines the methods to apply the callback to
	 */
	public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf) {

		// Keep backing up the inheritance hierarchy.
		Class<?> targetClass = clazz;
		do {
			Method[] methods = targetClass.getDeclaredMethods();
			for (Method method : methods) {
				if (mf != null && !mf.matches(method)) {
					continue;
				}
				try {
					mc.doWith(method);
				} catch (IllegalAccessException ex) {
					throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null);
	}

	/**
	 * Get all declared methods on the leaf class and all superclasses. Leaf class methods are
	 * included first.
	 * 
	 * @param leafClass Class<?>
	 * @return Method[]
	 */
	public static Method[] getAllDeclaredMethods(Class<?> leafClass) {
		final List<Method> methods = new ArrayList<Method>(32);
		doWithMethods(leafClass, new MethodCallback() {
			public void doWith(Method method) {
				methods.add(method);
			}
		});
		return methods.toArray(new Method[methods.size()]);
	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the class hierarchy to
	 * get all declared fields.
	 * 
	 * @param clazz the target class to analyze
	 * @param fc the callback to invoke for each field
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc) {
		doWithFields(clazz, fc, null);
	}

	/**
	 * Invoke the given callback on all fields in the target class, going up the class hierarchy to
	 * get all declared fields.
	 * 
	 * @param clazz the target class to analyze
	 * @param fc the callback to invoke for each field
	 * @param ff the filter that determines the fields to apply the callback to
	 */
	public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) {

		// Keep backing up the inheritance hierarchy.
		Class<?> targetClass = clazz;
		do {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				// Skip static and final fields.
				if (ff != null && !ff.matches(field)) {
					continue;
				}
				try {
					fc.doWith(field);
				} catch (IllegalAccessException ex) {
					throw new IllegalStateException("Shouldn't be illegal to access field '" + field.getName() + "': " + ex);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);
	}

	/**
	 * Given the source object and the destination, which must be the same class or a subclass, copy
	 * all fields, including inherited fields. Designed to work on objects with public no-arg
	 * constructors.
	 * 
	 * @param src Object
	 * @param dest Object
	 */
	public static void shallowCopyFieldState(final Object src, final Object dest) {
		if (src == null) {
			throw new IllegalArgumentException("Source for field copy cannot be null");
		}
		if (dest == null) {
			throw new IllegalArgumentException("Destination for field copy cannot be null");
		}
		if (!src.getClass().isAssignableFrom(dest.getClass())) {
			throw new IllegalArgumentException("Destination class [" + dest.getClass().getName() + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
		}
		doWithFields(src.getClass(), new FieldCallback() {
			public void doWith(Field field) throws IllegalAccessException {
				makeAccessible(field);
				Object srcValue = field.get(src);
				field.set(dest, srcValue);
			}
		}, COPYABLE_FIELDS);
	}

	/**
	 * Action to take on each method.
	 */
	public interface MethodCallback {

		/**
		 * Perform an operation using the given method.
		 * 
		 * @param method the method to operate on
		 * @throws IllegalAccessException 例外
		 */
		void doWith(Method method) throws IllegalAccessException;
	}

	public interface MethodFilter {

		/**
		 * Determine whether the given method matches.
		 * 
		 * @param method the method to check
		 * @return boolean
		 */
		boolean matches(Method method);
	}

	/**
	 * Callback interface invoked on each field in the hierarchy.
	 */
	public interface FieldCallback {

		/**
		 * Perform an operation using the given field.
		 * 
		 * @param field the field to operate on
		 * @throws IllegalAccessException 例外
		 */
		void doWith(Field field) throws IllegalAccessException;
	}

	/**
	 * Callback optionally used to filter fields to be operated on by a field callback.
	 */
	public interface FieldFilter {

		/**
		 * Determine whether the given field matches.
		 * 
		 * @param field the field to check
		 * @return boolean
		 */
		boolean matches(Field field);
	}

	/**
	 * Pre-built FieldFilter that matches all non-static, non-final fields.
	 */
	public static FieldFilter COPYABLE_FIELDS = new FieldFilter() {

		public boolean matches(Field field) {
			return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
		}
	};

	/**
	 * Pre-built MethodFilter that matches all non-bridge methods.
	 */
	public static MethodFilter NON_BRIDGED_METHODS = new MethodFilter() {

		public boolean matches(Method method) {
			return !method.isBridge();
		}
	};

}
