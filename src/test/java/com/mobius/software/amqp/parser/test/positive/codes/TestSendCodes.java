package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SendCode;

public class TestSendCodes
{
	@Test
	public void testUnsettled()
	{
		assertEquals(String.format("Code %d not accepted", 0), SendCode.UNSETTLED, SendCode.valueOf((byte) 0));
	}

	@Test
	public void testSettled()
	{
		assertEquals(String.format("Code %d not accepted", 1), SendCode.SETTLED, SendCode.valueOf((byte) 1));
	}

	@Test
	public void testMixed()
	{
		assertEquals(String.format("Code %d not accepted", 2), SendCode.MIXED, SendCode.valueOf((byte) 2));
	}

}
