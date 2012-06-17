/**
 * Copyright 2011 the original author
 */
package core.logics.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Utility for Class.
 *
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public final class ClassUtils {

	/**
	 * インスタンス生成不可.
	 */
	private ClassUtils() {
	}

	/**
	 * @param <I> 型
	 * @param <R> <I>を継承した型
	 * @param f フィールド
	 * @param inherits クラス
	 * @return クラス<R>
	 */
	@SuppressWarnings("unchecked")
	public static <I, R extends I> Class<R> getGenericType(Field f, Class<I> inherits) {
		ParameterizedType t = (ParameterizedType) f.getGenericType();
		Type[] types = t.getActualTypeArguments();
		for (Type type : types) {
			if (inherits.isAssignableFrom((Class<?>) type)) {
				return (Class<R>) type;
			}
		}
		return null;
	}

	/**
	 * @param <I> 型
	 * @param <R> <I>を継承した型
	 * @param cls クラス
	 * @param inherits クラス<I>
	 * @return クラス<R>
	 */
	@SuppressWarnings("unchecked")
	public static <I, R extends I> Class<R> getGenericType(Class<?> cls, Class<I> inherits) {
		ParameterizedType t = (ParameterizedType) cls.getGenericSuperclass();
		Type[] types = t.getActualTypeArguments();
		for (Type type : types) {
			if (inherits.isAssignableFrom((Class<?>) type)) {
				return (Class<R>) type;
			}
		}
		throw new IllegalArgumentException("Poor implementation : no generic type found. : inherits = " + inherits + " target = " + cls);
	}
	
	/**
	 * Gets the resource as string.
	 * 
	 * @param filePath the file path
	 * @param encode the character set
	 * @return the value
	 */
	public static String getResourceString(Class<?> clazz , String filePath , String encode) throws IOException{
		InputStream stream = getResourceAsStream(clazz, filePath);
		BufferedReader reader = null;
		String temp = null;
		StringBuilder builder = new StringBuilder();
		try{
			reader = new BufferedReader( new InputStreamReader(stream,encode));
			while( ( temp = reader.readLine()) != null){
				builder.append(temp);
			}
			return builder.toString();
		}catch(IOException ioe){
			throw ioe;
		}finally{
			if ( reader != null){
				reader.close();
			}
		}
	}


    /**
     * Gets the resource as stream.
     * 
     * @param clazz the class
     * @param path the path
     * @return the stream
     */
    public static InputStream getResourceAsStream(Class<?> clazz, String path){
        InputStream result = null;

        while (path.startsWith("/")){
        	path = path.substring(1);
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null){
            classLoader = clazz.getClassLoader();
            result = classLoader.getResourceAsStream( path );
        }else{
            result= classLoader.getResourceAsStream( path );

            if (result == null){
                classLoader = clazz.getClassLoader();
                if (classLoader != null){
                    result = classLoader.getResourceAsStream( path );
                }
            }
        }

        return result;

    }
}
