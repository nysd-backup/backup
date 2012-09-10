/**
 * Copyright 2011 the original author
 */
package gen;

import kosmos.tools.generator.query.processor.Facade;

import org.junit.Test;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OutputQuery {

	/**
	 * Oracle用出力
	 */
	@Test
	public void output() throws Exception{
		//String queryDir = System.getProperty("queryDir");		
		new Facade().output("C:/Project/Personal/workspace/gitrepository/personal-remote/tool/entity-generator/query");		
	}
}
