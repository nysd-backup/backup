package framework.service.ext.async;

import java.lang.annotation.Annotation;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

import framework.service.core.async.AbstractAsyncServiceFactory;

/**
 * 非同期処理用のアノテーション.
 *
 * @author yoshida-n
 * @version	2011/06/15 created.
 */
@Stateless
public class AsyncServiceFactoryImpl extends AbstractAsyncServiceFactory {

	/**
	 * @see framework.service.core.async.AbstractAsyncServiceFactory#getAnnotation()
	 */
	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return Asynchronous.class;
	}

}
