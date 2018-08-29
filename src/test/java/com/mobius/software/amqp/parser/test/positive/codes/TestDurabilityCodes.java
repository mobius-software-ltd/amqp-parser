package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.TerminusDurability;

public class TestDurabilityCodes
{
	@Test
	public void testNone()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0), TerminusDurability.NONE, TerminusDurability.valueOf(0));
	}

	@Test
	public void testConfiguration()
	{
		assertEquals(String.format("Code %d was not accepted: ", 1), TerminusDurability.CONFIGURATION, TerminusDurability.valueOf(1));
	}

	@Test
	public void testUnsettledState()
	{
		assertEquals(String.format("Code %d was not accepted: ", 2), TerminusDurability.UNSETTLED_STATE, TerminusDurability.valueOf(2));
	}

}
