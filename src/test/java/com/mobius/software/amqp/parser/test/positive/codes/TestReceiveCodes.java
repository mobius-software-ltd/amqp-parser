package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.ReceiveCode;

public class TestReceiveCodes
{
	@Test
	public void testFirst()
	{
		assertEquals(String.format("Code %d not accepted", 0), ReceiveCode.FIRST, ReceiveCode.valueOf((short) 0));
	}

	@Test
	public void testSecond()
	{
		assertEquals(String.format("Code %d not accepted", 1), ReceiveCode.SECOND, ReceiveCode.valueOf((short) 1));
	}
}
