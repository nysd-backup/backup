package framework.web.core.rest;

import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.core.spi.component.ioc.IoCManagedComponentProvider;
import framework.web.core.loader.ComponentLocator;

/**
 * コンポーネントプロバイダ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class ComponentProviderImpl implements IoCManagedComponentProvider {

	private final Class<?> resource;

	/**
	 * @param resource リソース
	 */
	ComponentProviderImpl(Class<?> resource) {
		this.resource = resource;
	}

	/**
	 * @see com.sun.jersey.core.spi.component.ComponentProvider#getInstance()
	 */
	@Override
	public Object getInstance() {
		return ComponentLocator.lookupByInterface(resource);
	}

	/**
	 * @see com.sun.jersey.core.spi.component.ioc.IoCManagedComponentProvider#getScope()
	 */
	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}

	/**
	 * @see com.sun.jersey.core.spi.component.ioc.IoCInstantiatedComponentProvider#getInjectableInstance(java.lang.Object)
	 */
	@Override
	public Object getInjectableInstance(Object o) {
		return o;
	}

}
