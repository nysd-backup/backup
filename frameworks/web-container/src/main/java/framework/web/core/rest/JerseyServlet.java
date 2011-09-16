/**
 * Use is subject to license terms.
 */
package framework.web.core.rest;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Jeysey-spring連携.
 *
 * @author yoshida-n
 * @version	created.
 */
public class JerseyServlet extends ServletContainer{

	private static final long serialVersionUID = 1L;

	/**
	 * @see com.sun.jersey.spi.container.servlet.ServletContainer#initiate(com.sun.jersey.api.core.ResourceConfig, com.sun.jersey.spi.container.WebApplication)
	 */
	protected void initiate(ResourceConfig rc, WebApplication wa){
		wa.initiate(rc,new ComponentProviderFactoryImpl());
	}
}
