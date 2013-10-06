/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.alpha.query.free.loader;

import java.io.InputStream;
import java.util.Map;

/**
 * The template engine to evaluate the SQL
 *
 * @author yoshida-n
 * @version	1.0
 */
public interface TemplateEngine {

	/**
	 * @param rowString the SQL before loading.
	 * @return the SQL
	 */
	String load(InputStream rowString);
	
	/**
	 * @param rowString the SQL after loading.
	 * @param parameter the parameter to evaluate if-statement
	 * @return the evaluated SQL
	 */
	String evaluate(String rowString,Map<String,Object> parameter);
}
