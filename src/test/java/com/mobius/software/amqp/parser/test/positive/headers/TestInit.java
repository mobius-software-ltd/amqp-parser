package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.SASLInit;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestInit
{
	private SASLInit header;
	private byte[] responseArray = new byte[]
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	@Before
	public void setUp()
	{
		header = new SASLInit();
		SASLInit in = new SASLInit();
		in.setHostName("localhost");
		in.setInitialResponse(responseArray);
		in.setMechanism("some mechanism");
		TLVList list = in.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
		responseArray = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x41, (byte) 0xc0 };
		assertTrue("Invalid Init arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testHostname()
	{
		assertEquals("Invalid Init Hostname: ", "localhost", header.getHostName());
	}

	@Test
	public void testInitialResponse()
	{
		assertTrue("Invalid Init initialResponse: ", Arrays.equals(responseArray, header.getInitialResponse()));
	}

	@Test
	public void testMechanism()
	{
		assertEquals("Invalid Init mechanism: ", "some mechanism", header.getMechanism());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Init code: ", HeaderCode.INIT, header.getCode());
	}
}
