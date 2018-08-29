package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.StateCode;

public class TestStateCodes
{
	@Test
	public void testReceived()
	{
		assertEquals(String.format("Code %d not accepted", 0x23), StateCode.RECEIVED, StateCode.valueOf(0x23));
	}

	@Test
	public void testAccepted()
	{
		assertEquals(String.format("Code %d not accepted", 0x24), StateCode.ACCEPTED, StateCode.valueOf(0x24));
	}

	@Test
	public void testRejected()
	{
		assertEquals(String.format("Code %d not accepted", 0x25), StateCode.REJECTED, StateCode.valueOf(0x25));
	}

	@Test
	public void testReleased()
	{
		assertEquals(String.format("Code %d not accepted", 0x26), StateCode.RELEASED, StateCode.valueOf(0x26));
	}

	@Test
	public void testModified()
	{
		assertEquals(String.format("Code %d not accepted", 0x27), StateCode.MODIFIED, StateCode.valueOf(0x27));
	}
}
