package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.AMQPData;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TestData
{
	private AMQPData section;

	@Before
	public void setUp()
	{
		section = new AMQPData(new byte[]
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
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
		{ 0x00, 0x53, 0x75, (byte) 0xa0 };
		Assert.assertArrayEquals("Invalid Data value constructor bytes", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testData()
	{
		Assert.assertArrayEquals("Invalid Data value", new byte[]
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, section.getData());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Data code", SectionCode.DATA, section.getCode());
	}

}
