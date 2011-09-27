/**
 * Copyright 2011 the original author
 */
package framework.logics.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class操作.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class ClassUtils {
	
	/**
	 * クラスパスファイルからリソースを取得する.
	 * 
	 * @param filePath ファイルパス
	 * @param encode 文字コード
	 * @return 内容
	 */
	public static String getResourceString(Class<?> clazz , String filePath , String encode) throws IOException{
		InputStream stream = ClassUtils.getResourceAsStream(clazz, filePath);
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
	 * インスタンス生成
	 * @param <T>
	 * @param clazz
	 */
	public static <T> T newInstance(Class<T> clazz){
		T object = null;
		try{
			object = clazz.newInstance();
		}catch(Exception ex){
			throw new IllegalStateException("Failed to instantiate class ",ex);
		}
		return object;
	}
	


    /**
     * ストリームを取得する.
     * 
     * @param clazz クラス
     * @param path パス
     * @return ストリーム
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
