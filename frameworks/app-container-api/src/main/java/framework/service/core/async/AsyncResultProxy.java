/**
 * Copyright 2011 the original author
 */
package framework.service.core.async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 非同期結果取得用プロキシ.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class AsyncResultProxy implements InvocationHandler{
	
	/** 非同期処理未来結果 */
	private Future<Object> returnValue = null;
	
	/**
	 * @param returnValue　非同期処理未来結果
	 */
	public AsyncResultProxy(Future<Object> returnValue){
		this.returnValue = returnValue;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
		try{
			
			//値取得（処理終了していなければブロック）
			Object instance = returnValue.get();

			return method.invoke(instance, args);
			
			//キャンセル
		}catch(CancellationException ce){
			throw new IllegalStateException(ce);
			
			//実行時例外
		}catch(ExecutionException ee){
			Throwable target = ee.getCause();
			if(target instanceof RuntimeException ){
				throw (RuntimeException)target;
			}else{
				throw new IllegalStateException(target);
			}
		} catch (InterruptedException ie) {
			throw new IllegalStateException(ie);
		}
	}

}
