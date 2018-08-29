package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.RoleCode;

public class TestRoleCodes
{
	@Test
	public void testSender()
	{
		assertEquals(String.format("Code %d not accepted", 0), RoleCode.SENDER, RoleCode.valueOf(false));
	}

	@Test
	public void testReceived()
	{
		assertEquals(String.format("Code %d not accepted", 1), RoleCode.RECEIVER, RoleCode.valueOf(true));
	}
}
