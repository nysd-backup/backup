/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

import java.io.InputStream;
import java.util.Map;

/**
 * The template engine to evaluate the SQL
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface TemplateEngine {

	/**
	 * @param rowString the SQL before loading.
	 * @return the SQL
	 */
	public String load(InputStream rowString);
	
	/**
	 * @param rowString the SQL after loading.
	 * @param parameter the parameter to evaluate if-statement
	 * @return the evaluated SQL
	 */
	public String evaluate(String rowString,Map<String,Object> parameter);
}
