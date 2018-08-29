package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.AMQPRejected;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestRejected
{
	private AMQPRejected actual = new AMQPRejected();

	@Before
	public void setUp()
	{
		AMQPRejected in = new AMQPRejected();
		AMQPError error = AMQPError.builder().build();
		in.setError(error);
		TLVList list = in.toArgumentsList();
		actual.fromArgumentsList(list);
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
		{ 0x00, 0x53, 0x25, (byte) 0xc0 };
		assertTrue("Invalid Rejected-state constructor bytes: ", Arrays.equals(expectedArray, actual.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testError()
	{
		assertNotNull("Invalid Rejected-outcome error: ", actual.getError());
	}
}
