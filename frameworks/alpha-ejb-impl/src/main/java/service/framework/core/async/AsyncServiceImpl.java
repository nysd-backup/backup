package service.framework.core.async;

import java.lang.reflect.Method;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;

import service.framework.core.activation.ServiceLocator;



/**
 * An asynchoronous service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@Asynchronous
@Singleton
public class AsyncServiceImpl implements AsyncService{

	/**
	 * @see service.framework.core.async.AsyncService#execute(java.lang.Object, java.lang.reflect.Method, java.lang.Object)
	 */
	public Future<Object> execute(Object proxy, Method method , Object args) throws Exception{	
		
		Object service = ServiceLocator.getService(method.getDeclaringClass());
		
		Object value  = null;
		if(method.getParameterTypes() == null || method.getParameterTypes().length == 0){
			value = method.invoke(service);
		}else{
			value = method.invoke(service, args);
		}
		return new AsyncResult<Object>(value);
	}
}
