package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPPing;

public class TestPing
{
	private AMQPPing ping;

	@Before
	public void setUp()
	{
		ping = new AMQPPing();
	}

	@After
	public void tearDown()
	{
		ping = null;
	}

	@Test
	public void testGetArguments()
	{
		assertNull("Invalid Ping arguments", ping.toArgumentsList());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Ping code: ", HeaderCode.PING, ping.getCode());
	}
}
