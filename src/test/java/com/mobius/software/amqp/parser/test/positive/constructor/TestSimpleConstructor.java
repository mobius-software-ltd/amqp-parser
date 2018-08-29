package com.mobius.software.amqp.parser.test.positive.constructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.SimpleConstructor;

public class TestSimpleConstructor
{
	private SimpleConstructor constructor;

	@Before
	public void setUp()
	{
		constructor = new SimpleConstructor(AMQPType.LIST_8);
	}

	@After
	public void tearDown()
	{
		constructor = null;
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid constructor code: ", AMQPType.LIST_8, constructor.getCode());
	}

	@Test
	public void testBytes()
	{
		assertTrue("Invalid constructor bytes", Arrays.equals(new byte[]
		{ (byte) 0xc0 }, constructor.getBytes()));
	}

	@Test
	public void testLength()
	{
		assertEquals("Invalid constructor length: ", 1, constructor.getLength());
	}

}
