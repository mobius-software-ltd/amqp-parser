package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.mobius.software.amqp.parser.tlv.impl.AMQPReleased;
import com.mobius.software.amqp.parser.tlv.impl.AMQPState;

public class TestReleased
{
	@Test
	public void testConstructor()
	{
		AMQPState actual = new AMQPReleased();
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x26, 0x45 };
		assertTrue("Invalid Accepted bytes: ", Arrays.equals(expectedArray, actual.toArgumentsList().getBytes()));
	}
}
