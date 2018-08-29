package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.MessageHeader;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TestMessageHeader
{
	private MessageHeader section;

	@Before
	public void setUp()
	{
		section = MessageHeader.builder()//
				.deliveryCount(20L)//
				.durable(true)//
				.firstAquirer(false)//
				.milliseconds(60000L)//
				.priority((short) 50)//
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
		{ 0x00, 0x53, 0x70, (byte) 0xc0 };
		Assert.assertArrayEquals("Invalid Header value constructor bytes: ", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testDeliveryCount()
	{
		assertEquals("Invalid Header deliveryCount: ", Long.valueOf(20), section.getDeliveryCount());
	}

	@Test
	public void testDurable()
	{
		assertEquals("Invalid Header durable: ", true, section.getDurable());
	}

	@Test
	public void testFirstAquirer()
	{
		assertEquals("Invalid Header firstAquirer: ", false, section.getFirstAquirer());
	}

	@Test
	public void testMilliseconds()
	{
		assertEquals("Invalid Header milliseconds: ", Long.valueOf(60000), section.getMilliseconds());
	}

	@Test
	public void testPriority()
	{
		assertEquals("Invalid Header priority: ", Short.valueOf((short) 50), section.getPriority());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Header code: ", SectionCode.HEADER, section.getCode());
	}
}
