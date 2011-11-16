/**
 * Copyright 2011 the original author
 */
package kosmos.framework.web.core.api.service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * Injects the <code>BusinessDelegate</code> to service declaring the <code>@ServiceFacade</code>.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ServceFacadeInjector {
	
	/**
	 * Initialize the bean.
	 * 
	 * @param bean the created bean
	 * @param beanName the name of bean
	 * @return bean
	 */
	public Object inject(Object bean, String beanName) {
		
		if(bean == null){
			return bean;
		}
		
		if(bean.getClass().getAnnotation(ServiceCallable.class) == null){
			return bean;
		}
		
		for (Class<?> cls = bean.getClass(); cls != Object.class; cls = cls.getSuperclass()) {

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
				
				ServiceFacade a = f.getAnnotation(ServiceFacade.class) ;
				if(a == null){
					continue;
				}
				
				f.setAccessible(true);
				
				//プロキシの設定
				BusinessDelegate handler = createBusinessDelegate();							
				if( !a.alias().isEmpty()){
					handler.setAlias(a.alias());
				}
				try {
					f.set(bean, Proxy.newProxyInstance(f.getType().getClassLoader(), new Class[]{f.getType()}, handler));				
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}

		}	
		return bean;
	}

	/**
	 * @return the BusinessDelegate
	 */
	protected BusinessDelegate createBusinessDelegate(){
		return new DefaultBusinessDelegate();
	}
}
