package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.AMQPValue;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TestValue
{
	private AMQPValue section;

	@Before
	public void setUp()
	{
		section = new AMQPValue("some string");
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
		{ 0x00, 0x53, 0x77, (byte) 0xa1 };
		Assert.assertArrayEquals("Invalid Value value constructor bytes", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testValue()
	{
		assertEquals("Invalid Value value: ", "some string", section.getVal());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Value code: ", SectionCode.VALUE, section.getCode());
	}
}
