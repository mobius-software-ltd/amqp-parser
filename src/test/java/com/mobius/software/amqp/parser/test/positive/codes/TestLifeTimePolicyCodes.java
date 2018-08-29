package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.LifetimePolicy;

public class TestLifeTimePolicyCodes
{
	@Test
	public void testDeleteOnClose()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x2b), LifetimePolicy.DELETE_ON_CLOSE, LifetimePolicy.valueOf(0x2b));
	}

	@Test
	public void testDeleteOnNoLinks()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x2c), LifetimePolicy.DELETE_ON_NO_LINKS, LifetimePolicy.valueOf(0x2c));
	}

	@Test
	public void testDeleteOnNoMessages()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x2d), LifetimePolicy.DELETE_ON_NO_MESSAGES, LifetimePolicy.valueOf(0x2d));
	}

	@Test
	public void testDeleteOnNoLinksOrMessages()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x2e), LifetimePolicy.DELETE_ON_NO_LINKS_OR_MESSAGES, LifetimePolicy.valueOf(0x2e));
	}

}
