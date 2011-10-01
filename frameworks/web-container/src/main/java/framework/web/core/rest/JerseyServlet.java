/**
 * Copyright 2011 the original author
 */
package framework.web.core.rest;

import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Jersey-Spring Servlet.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
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
