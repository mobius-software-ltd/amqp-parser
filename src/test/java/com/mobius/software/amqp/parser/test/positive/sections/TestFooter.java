package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.AMQPFooter;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestFooter
{
	private AMQPFooter section;
	private String value = "value";

	@Before
	public void setUp()
	{
		section = AMQPFooter.builder()//
				.addAnnotation("key1", value)//
				.addAnnotation("key2", value)//
				.addAnnotation("key3", value)//
				.build();
		TLVAmqp value = section.getValue();
		section.fill(value);
	}

	@After
	public void tearDown()
	{
		section = null;
		value = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x78, (byte) 0xc1 };
		Assert.assertArrayEquals("Invalid Footer value constructor bytes", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testAnnotations()
	{
		assertEquals("Invalid Footer annotation", value, section.getAnnotations().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Footer annotation", value, section.getAnnotations().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Footer annotation", value, section.getAnnotations().get(new AMQPSymbol("key3")));
		assertEquals("Invalid Footer annotations size: ", 3, section.getAnnotations().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Footer code: ", SectionCode.FOOTER, section.getCode());
	}

}
