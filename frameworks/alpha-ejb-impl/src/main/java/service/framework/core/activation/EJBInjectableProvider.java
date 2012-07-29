/**
 * Copyright 2011 the original author
 */
package service.framework.core.activation;

import java.lang.reflect.Type;

import javax.ejb.EJB;

import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

/**
 * Combine Jersey and SessionBean @EJB is available in JAX-RS service. 
 *
 * @author yoshida-n
 * @version	created.
 */
public class EJBInjectableProvider implements InjectableProvider<EJB, Type>{

	/**
	 * @see com.sun.jersey.spi.inject.InjectableProvider#getInjectable(com.sun.jersey.core.spi.component.ComponentContext, java.lang.annotation.Annotation, java.lang.Object)
	 */
	@Override
	public Injectable<Object> getInjectable(ComponentContext context, EJB ejb, Type type) {

	    if (!(type instanceof Class)) { 
	    	return null;	
	    }
	
	    final Class<?> c = (Class<?>)type;			 	
        return new Injectable<Object>() {
        	public Object getValue() {
        		return ServiceLocator.getService(c);	
           }
        };	
	}

	/**
	 * @see com.sun.jersey.spi.inject.InjectableProvider#getScope()
	 */
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}

}
