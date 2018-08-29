package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;

public class TestSectionCodes
{
	@Test
	public void testHeader()
	{
		assertEquals(String.format("Code %d not accepted", 0x70), SectionCode.HEADER, SectionCode.valueOf(0x70));
	}

	@Test
	public void testDeliveryAnnotations()
	{
		assertEquals(String.format("Code %d not accepted", 0x71), SectionCode.DELIVERY_ANNOTATIONS, SectionCode.valueOf(0x71));
	}

	@Test
	public void testMessageAnnotations()
	{
		assertEquals(String.format("Code %d not accepted", 0x72), SectionCode.MESSAGE_ANNOTATIONS, SectionCode.valueOf(0x72));
	}

	@Test
	public void testProperties()
	{
		assertEquals(String.format("Code %d not accepted", 0x73), SectionCode.PROPERTIES, SectionCode.valueOf(0x73));
	}

	@Test
	public void testApplicationProperties()
	{
		assertEquals(String.format("Code %d not accepted", 0x74), SectionCode.APPLICATION_PROPERTIES, SectionCode.valueOf(0x74));
	}

	@Test
	public void testData()
	{
		assertEquals(String.format("Code %d not accepted", 0x75), SectionCode.DATA, SectionCode.valueOf(0x75));
	}

	@Test
	public void testSequence()
	{
		assertEquals(String.format("Code %d not accepted", 0x76), SectionCode.SEQUENCE, SectionCode.valueOf(0x76));
	}

	@Test
	public void testValue()
	{
		assertEquals(String.format("Code %d not accepted", 0x77), SectionCode.VALUE, SectionCode.valueOf(0x77));
	}

	@Test
	public void testFooter()
	{
		assertEquals(String.format("Code %d not accepted", 0x78), SectionCode.FOOTER, SectionCode.valueOf(0x78));
	}
}
