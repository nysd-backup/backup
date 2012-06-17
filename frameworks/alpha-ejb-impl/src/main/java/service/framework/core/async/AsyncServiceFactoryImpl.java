package service.framework.core.async;

import java.lang.annotation.Annotation;

import javax.ejb.Asynchronous;

import service.framework.core.async.AbstractAsyncServiceFactory;



/**
 * the factory to create asynchronous service.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class AsyncServiceFactoryImpl extends AbstractAsyncServiceFactory {

	/**
	 * @see service.framework.core.async.AbstractAsyncServiceFactory#getAnnotation()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return Asynchronous.class;
	}

}
