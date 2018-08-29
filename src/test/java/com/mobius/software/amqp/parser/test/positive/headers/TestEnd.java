package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPEnd;
import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestEnd
{
	private AMQPEnd header;

	@Before
	public void setUp()
	{
		header = AMQPEnd.builder()//
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
		{ 0x00, 0x53, 0x17, (byte) 0xc0 };
		assertTrue("Invalid End arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testError()
	{
		assertNotNull("Invalid End error", header.getError());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid End code: ", HeaderCode.END, header.getCode());
	}
}
