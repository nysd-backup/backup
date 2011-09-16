package framework.web.core.rest;

import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.core.spi.component.ioc.IoCManagedComponentProvider;
import framework.web.core.loader.ComponentLocator;

/**
 * コンポーネントプロバイダ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ComponentProviderImpl implements IoCManagedComponentProvider {

	private final Class<?> resource;

	/**
	 * @param context Springのサービスロケータ
	 * @param resource リソース
	 */
	ComponentProviderImpl(Class<?> resource) {
		this.resource = resource;
	}

	@Override
	public Object getInstance() {
		return ComponentLocator.lookupByInterface(resource);
	}

	@Override
	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}

	@Override
	public Object getInjectableInstance(Object o) {
		return o;
	}

}
