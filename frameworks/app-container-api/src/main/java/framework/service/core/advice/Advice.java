/**
 * Copyright 2011 the original author
 */
package framework.service.core.advice;

/**
 * AOP用のアドバイス.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface Advice {
	
	/**
	 * 前処理.
	 * @param target　JoinPointの対象オブジェクト
	 * @param methodName JoinPointの対象メソッド
	 * @param argments メソッドの引数
	 */
	public void before(Object target , String methodName , Object[] argments);
	
	
	/**
	 * 後処理
	 * @param target　JoinPointの対象オブジェクト
	 * @param methodName JoinPointの対象メソッド
	 * @param argments メソッドの引数
	 * @param result メソッド実行結果
	 */
	public void after(Object target ,String methodName , Object[] argments, Object result);

}
