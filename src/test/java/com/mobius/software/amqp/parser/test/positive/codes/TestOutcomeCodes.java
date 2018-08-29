package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.OutcomeCode;

public class TestOutcomeCodes
{
	@Test
	public void testOk()
	{
		assertEquals(String.format("Code %d not accepted", 0), OutcomeCode.OK, OutcomeCode.valueOf((short) 0));
	}

	@Test
	public void testAuth()
	{
		assertEquals(String.format("Code %d not accepted", 1), OutcomeCode.AUTH, OutcomeCode.valueOf((short) 1));
	}

	@Test
	public void testSys()
	{
		assertEquals(String.format("Code %d not accepted", 2), OutcomeCode.SYS, OutcomeCode.valueOf((short) 2));
	}

	@Test
	public void testSysPerm()
	{
		assertEquals(String.format("Code %d not accepted", 3), OutcomeCode.SYS_PERM, OutcomeCode.valueOf((short) 3));
	}

	@Test
	public void testSysTemp()
	{
		assertEquals(String.format("Code %d not accepted", 4), OutcomeCode.SYS_TEMP, OutcomeCode.valueOf((short) 4));
	}
}
