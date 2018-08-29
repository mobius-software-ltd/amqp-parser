package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;

public class TestArgumentCodes
{
	@Test
	public void testOpen()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x10), HeaderCode.OPEN, HeaderCode.valueOf(0x10));
	}

	@Test
	public void testBegin()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x11), HeaderCode.BEGIN, HeaderCode.valueOf(0x11));
	}

	@Test
	public void testAttach()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x12), HeaderCode.ATTACH, HeaderCode.valueOf(0x12));
	}

	@Test
	public void testFlow()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x13), HeaderCode.FLOW, HeaderCode.valueOf(0x13));
	}

	@Test
	public void testTransfer()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x14), HeaderCode.TRANSFER, HeaderCode.valueOf(0x14));
	}

	@Test
	public void testDisposition()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x15), HeaderCode.DISPOSITION, HeaderCode.valueOf(0x15));
	}

	@Test
	public void testDetach()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x16), HeaderCode.DETACH, HeaderCode.valueOf(0x16));
	}

	@Test
	public void testEnd()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x17), HeaderCode.END, HeaderCode.valueOf(0x17));
	}

	@Test
	public void testClose()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x18), HeaderCode.CLOSE, HeaderCode.valueOf(0x18));
	}

	@Test
	public void testMechanisms()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x40), HeaderCode.MECHANISMS, HeaderCode.valueOf(0x40));
	}

	@Test
	public void testInit()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x41), HeaderCode.INIT, HeaderCode.valueOf(0x41));
	}

	@Test
	public void testChallenge()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x42), HeaderCode.CHALLENGE, HeaderCode.valueOf(0x42));
	}

	@Test
	public void testResponse()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x43), HeaderCode.RESPONSE, HeaderCode.valueOf(0x43));
	}

	@Test
	public void testOutcome()
	{
		assertEquals(String.format("Code %d was not accepted: ", 0x44), HeaderCode.OUTCOME, HeaderCode.valueOf(0x44));
	}

}
