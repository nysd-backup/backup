/**
 * 
 */
package org.coder.gear.fluentd.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.Test;

/**
 * @author yoshida-n
 *
 */
public class FluentdAppenderTest {

	@Test
	public void test(){
		MDC.put("requestId", System.currentTimeMillis());
		Logger log = Logger.getLogger("DEBUG");
		log.debug("デバッグメッセージ");
	}
}
