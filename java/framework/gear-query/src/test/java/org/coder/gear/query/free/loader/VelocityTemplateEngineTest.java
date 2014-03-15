/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.loader;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.coder.gear.query.free.ConstantCache;
import org.junit.Assert;
import org.junit.Test;

/**
 * VelocityTemplateEngineTest.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class VelocityTemplateEngineTest extends Assert{

	/**
	 * load
	 */
	@Test
	public void load() {
		StringBuilder builder = new StringBuilder();
		builder.append("/* cmnt */").append("\n");
		builder.append("SELECT /* queryId */ FROM TEST WHERE TEST = $test").append("\n");
		builder.append("--% if($a == $b)").append("\n");
		builder.append("--% end");

		VelocityTemplateEngine engine = new VelocityTemplateEngine();
		ByteArrayInputStream stream = new ByteArrayInputStream(
				builder.toString().getBytes());
		String sql = engine.load(stream);

		StringBuilder expected = new StringBuilder();
		expected.append("/* cmnt */").append("\n");
		expected.append("SELECT /* queryId */ FROM TEST WHERE TEST = $test").append("\n");
		expected.append("#if($a == $b)").append("\n");
		expected.append("#end");

		assertEquals(expected.toString(),sql);
	}

	/**
	 * convert
	 */
	@Test
	public void evaluate() {
		StringBuilder builder = new StringBuilder();
		builder.append("/* コメント */").append("\n");
		builder.append("SELECT /* queryId */ FROM TEST WHERE TEST = $test").append("\n");
		builder.append("#if($a == $b)").append("\n");
		builder.append("#end");

		ConstantCache.put("test", "TEST_V");
		ConstantCache.put("C_a", "10");
		ConstantCache.put("b", "10_V");
		VelocityTemplateEngine engine = new VelocityTemplateEngine();
		

		Map<String,Object> value = new HashMap<String,Object>();
		value.put("test", "TEST");
		value.put("C_a", "10");
		value.put("b", "10_V");
		String sql = engine.evaluate(builder.toString(),value);

		StringBuilder expected = new StringBuilder();
		expected.append("/* コメント */").append("\n");
		expected.append("SELECT /* queryId */ FROM TEST WHERE TEST = TEST").append("\n");

		assertEquals(expected.toString(),sql);
	}
}