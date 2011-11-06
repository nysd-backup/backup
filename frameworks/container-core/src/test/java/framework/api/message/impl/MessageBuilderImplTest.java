/**
 * Copyright 2011 the original author
 */
package framework.api.message.impl;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import framework.core.message.ErrorMessage;
import framework.logics.builder.MessageBuilder;
import framework.logics.builder.impl.MessageBuilderImpl;

/**
 * function.
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class MessageBuilderImplTest{
	
	/**
	 * リソースバンドル取征E
	 * @throws Exception
	 */
	@Test
	public void load() throws Exception{
		
		MessageBuilder builder = new MessageBuilderImpl();
		String defined = builder.load(new ErrorMessage(100),null);
		Assert.assertEquals("TEST{0}",defined);
		
	}
	
	/**
	 * ロケール持E���Eリソースバンドル取征E
	 * @throws Exception
	 */
	@Test
	public void loadWithLocale() throws Exception{
		MessageBuilder builder = new MessageBuilderImpl();
		String defined = builder.load(new ErrorMessage(100), new Locale("en"));
		Assert.assertEquals("LOCALE_TEST{0}",defined);
	}
	
	/**
	 * ロケール持E���Eリソースバンドル取征E
	 * @throws Exception
	 */
	@Test
	public void build() throws Exception{
		MessageBuilder builder = new MessageBuilderImpl();
		String defined = builder.load(new ErrorMessage(100), new Locale("en"));
		String builded = builder.build(defined,100);
		Assert.assertEquals("LOCALE_TEST100",builded);
	}

}
