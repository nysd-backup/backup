/**
 * 
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
 * @version	2011/05/08 created.
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
     * Return the specified class.  Checks the ThreadContext classloader first,
     * then uses the System classloader.  Should replace all calls to
     * <code>Class.forName( claz )</code> (which only calls the System class
     * loader) when the class might be in a different classloader (e.g. in a
     * webapp).
     *
     * @param clazz the name of the class to instantiate
     * @return the requested Class object
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
	public static <T> Class<T> loadClass(String clazz) throws ClassNotFoundException
    {
        /**
         * Use the Thread context classloader if possible
         */
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null)
        {
            try
            {
                return (Class<T>)(Class.forName(clazz, true, loader));
            }
            catch (ClassNotFoundException E)
            {
                /**
                 * If not found with ThreadContext loader, fall thru to
                 * try System classloader below (works around bug in ant).
                 */
            }
        }
        /**
         * Thread context classloader isn't working out, so use system loader.
         */
        return (Class<T>)(Class.forName(clazz));
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
			ReflectionUtils.handleReflectionException(ex);
		}
		return object;
	}
	


    /**
     * Finds a resource with the given name.  Checks the Thread Context
     * classloader, then uses the System classloader.  Should replace all
     * calls to <code>Class.getResourceAsString</code> when the resource
     * might come from a different classloader.  (e.g. a webapp).
     * @param claz Class to use when getting the System classloader (used if no Thread
     * Context classloader available or fails to get resource).
     * @param name name of the resource
     * @return InputStream for the resource.
     */
    public static InputStream getResourceAsStream(Class<?> claz, String name)
    {
        InputStream result = null;

        /**
         * remove leading slash so path will work with classes in a JAR file
         */
        while (name.startsWith("/"))
        {
            name = name.substring(1);
        }

        ClassLoader classLoader = Thread.currentThread()
                                    .getContextClassLoader();

        if (classLoader == null)
        {
            classLoader = claz.getClassLoader();
            result = classLoader.getResourceAsStream( name );
        }
        else
        {
            result= classLoader.getResourceAsStream( name );

            /**
            * for compatibility with texen / ant tasks, fall back to
            * old method when resource is not found.
            */

            if (result == null)
            {
                classLoader = claz.getClassLoader();
                if (classLoader != null)
                    result = classLoader.getResourceAsStream( name );
            }
        }

        return result;

    }
}
