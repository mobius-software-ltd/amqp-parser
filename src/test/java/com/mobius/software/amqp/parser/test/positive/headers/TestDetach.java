package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPDetach;
import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestDetach
{
	private AMQPDetach header;

	@Before
	public void setUp()
	{
		header = AMQPDetach.builder()//
				.closed(true)//
				.error(AMQPError.builder().build())//
				.handle(1000L)//
				.build();
		TLVList list = header.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x16, (byte) 0xc0 };
		assertTrue("invalid Detach arguments constructor bytes: ", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testClosed()
	{
		assertTrue("Invalid Detach closed", header.getClosed());
	}

	@Test
	public void testError()
	{
		assertNotNull("Invalid Detach error", header.getError());
	}

	@Test
	public void testHandle()
	{
		assertEquals("Invalid Detach handle: ", Long.valueOf(1000), header.getHandle());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Detach code: ", HeaderCode.DETACH, header.getCode());
	}

}
