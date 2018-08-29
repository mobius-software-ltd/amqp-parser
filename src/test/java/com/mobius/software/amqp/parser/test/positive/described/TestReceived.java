package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.tlv.impl.AMQPReceived;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestReceived
{
	private AMQPReceived actual = new AMQPReceived();

	@Before
	public void setUp()
	{
		AMQPReceived in = new AMQPReceived();
		in.setSectionNumber(10L);
		in.setSectionOffset(BigInteger.valueOf(20));
		TLVList stateList = in.toArgumentsList();
		actual.fromArgumentsList(stateList);
	}

	@After
	public void tearDown()
	{
		actual = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x23, (byte) 0xc0 };
		assertTrue("Invalid Received constructor bytes: ", Arrays.equals(expectedArray, actual.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testSectionNumber()
	{
		assertEquals("Invalid section number value: ", Long.valueOf(10), actual.getSectionNumber());
	}

	@Test
	public void testSectionOffset()
	{
		assertEquals("Invalid section offset value: ", BigInteger.valueOf(20), actual.getSectionOffset());
	}

}
