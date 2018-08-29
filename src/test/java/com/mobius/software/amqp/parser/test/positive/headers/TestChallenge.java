package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.SASLChallenge;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestChallenge
{
	private SASLChallenge header;
	private byte[] value = new byte[]
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	@Before
	public void setUp()
	{
		header = new SASLChallenge();
		SASLChallenge in = new SASLChallenge();
		in.setChallenge(value);
		TLVList list = in.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
		value = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x42, (byte) 0xc0 };
		assertTrue("Invalid Chalenge constructor bytes: ", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testChallenge()
	{
		assertTrue("Invalid Challenge value", Arrays.equals(value, header.getChallenge()));
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Challenge code", HeaderCode.CHALLENGE, header.getCode());
	}

}
