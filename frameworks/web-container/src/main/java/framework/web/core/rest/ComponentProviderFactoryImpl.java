/**
 * Copyright (C) 2010-2011 by Future Architect, Inc. Japan
 * Use is subject to license terms.
 */
package framework.web.core.rest;

import javax.ws.rs.Path;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProvider;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;

/**
 * コンポーネントプロバイダファクトリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ComponentProviderFactoryImpl implements IoCComponentProviderFactory {

	/**
	 * @see com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory#getComponentProvider(java.lang.Class)
	 */
	@Override
	public IoCComponentProvider getComponentProvider(Class<?> c) {
		return getComponentProvider(null, c);
	}

	/**
	 * @see com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory#getComponentProvider(com.sun.jersey.core.spi.component.ComponentContext, java.lang.Class)
	 */
	@Override
	public IoCComponentProvider getComponentProvider(ComponentContext cc, Class<?> c) {

		Path path = c.getAnnotation(Path.class);
		if (path == null) {
			return null;
		}
		if (!c.isInterface()) {
			throw new IllegalArgumentException("'Path' annnotation can be declare in interface only");
		}

		return new ComponentProviderImpl(c);
	}
}