package com.mobius.software.amqp.parser.test.positive.constructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;

public class TestDescribedConstructor
{
	private DescribedConstructor constructor;

	@Before
	public void setUp()
	{
		TLVFixed descriptor = new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x28 });
		constructor = new DescribedConstructor(AMQPType.LIST_8, descriptor);
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
		{ 0x00, 0x53, 0x28, (byte) 0xc0 }, constructor.getBytes()));
	}

	@Test
	public void testLength()
	{
		assertEquals("Invalid constructor length: ", 4, constructor.getLength());
	}

}
