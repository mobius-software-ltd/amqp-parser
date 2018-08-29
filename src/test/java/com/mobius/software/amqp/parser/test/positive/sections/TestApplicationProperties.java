package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.ApplicationProperties;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TestApplicationProperties
{
	private ApplicationProperties section;

	@Before
	public void setUp()
	{
		section = ApplicationProperties.builder()//
				.addProperty("key1", "string value")//
				.addProperty("key2", false)//
				.addProperty("key3", Long.valueOf(10))//
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
		{ 0x00, 0x53, 0x74, (byte) 0xc1 };
		Assert.assertArrayEquals("Invalid ApplicationProperties value constructor bytes: ", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid ApplicationProperties string property: ", "string value", section.getProperties().get("key1"));
		assertEquals("Invalid ApplicationProperties boolean property: ", false, section.getProperties().get("key2"));
		assertEquals("Invalid ApplicationProperties string property: ", Long.valueOf(10), section.getProperties().get("key3"));
		assertEquals("Invalid ApplicationProperties properties size: ", 3, section.getProperties().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid ApplicationProperties code: ", SectionCode.APPLICATION_PROPERTIES, section.getCode());
	}
}
