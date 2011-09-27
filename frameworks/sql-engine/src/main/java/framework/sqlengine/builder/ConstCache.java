/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定数キャッシュ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ConstCache {

	/** キャッシュ保持用 */
	private static final Map<String,Object> cache = new ConcurrentHashMap<String,Object>();
	
	/**
	 * キャッシュに追加
	 * @param name 定数名
	 * @param value 定数値
	 */
	public static void put(String name , Object value){
		if(cache.containsKey(name)){
			throw new IllegalArgumentException(String.format("%s is already exists",name));
		}else{
			cache.put(name, value);
		}
	}
	
	/**
	 * キャッシュから取得
	 * @param name 名称
	 * @return 値
	 */
	public static Object get(String name){
		return cache.get(name);
	}
	
	/**
	 * キャッシュ有無
	 * @param name　名称
	 * @return true:存在する
	 */
	public static boolean containsKey(String name){
		return cache.containsKey(name);
	}
	
	/**
	 * 削除
	 */
	public static void destroy(){
		cache.clear();
	}
	
}
