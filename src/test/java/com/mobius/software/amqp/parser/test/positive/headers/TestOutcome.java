package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.OutcomeCode;
import com.mobius.software.amqp.parser.header.impl.SASLOutcome;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestOutcome
{
	private SASLOutcome header;
	private byte[] data = new byte[]
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	@Before
	public void setUp()
	{
		header = SASLOutcome.builder()//
				.outcomeCode(OutcomeCode.OK)//
				.additionalData(data)//
				.build();
		TLVList list = header.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
		data = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x44, (byte) 0xc0 };
		assertTrue("Invalid Outcome arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testOutcomeCode()
	{
		assertEquals("Invalid Outcome outcomeCode: ", OutcomeCode.OK, header.getOutcomeCode());
	}

	@Test
	public void testAdditionalData()
	{
		assertTrue("Invalid Outcome additionalData", Arrays.equals(data, header.getAdditionalData()));
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Outcome code: ", HeaderCode.OUTCOME, header.getCode());
	}

}
