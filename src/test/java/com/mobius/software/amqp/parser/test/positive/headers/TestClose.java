package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPClose;
import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestClose
{
	private AMQPClose header;

	@Before
	public void setUp()
	{
		header = AMQPClose.builder()//
				.error(AMQPError.builder().build())//
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
		{ 0x00, 0x53, 0x18, (byte) 0xc0 };
		assertTrue("Invalid Close arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testError()
	{
		assertNotNull("Invalid Close error", header.getError());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Close code: ", HeaderCode.CLOSE, header.getCode());
	}

}
