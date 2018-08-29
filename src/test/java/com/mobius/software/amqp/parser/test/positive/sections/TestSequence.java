package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.AMQPSequence;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TestSequence
{
	private AMQPSequence section;

	@Before
	public void setUp()
	{
		section = AMQPSequence.builder()//
				.addSequence(123L)//
				.addSequence(BigInteger.valueOf(345))//
				.addSequence("testString")//
				.addSequence(true)//
				.build();
		TLVAmqp value = section.getValue();
		section.fill(value);
	}

	@After
	public void tearDown()
	{
		section = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x76, (byte) 0xc0 };
		Assert.assertArrayEquals("Invalid Sequence value constructor bytes", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testSections()
	{
		assertEquals("Invalid Sequence UInteger value: ", Long.valueOf(123), section.getSequence().get(0));
		assertEquals("Invalid Sequence ULong value: ", BigInteger.valueOf(345), section.getSequence().get(1));
		assertEquals("Invalid Sequence String value: ", "testString", section.getSequence().get(2));
		assertEquals("Invalid Sequence Boolean value: ", true, section.getSequence().get(3));
		assertEquals("Invalid Sequence list size: ", 4, section.getSequence().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Sequence code", SectionCode.SEQUENCE, section.getCode());
	}

}
