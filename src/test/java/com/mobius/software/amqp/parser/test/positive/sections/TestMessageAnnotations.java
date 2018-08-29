package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.MessageAnnotations;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestMessageAnnotations
{
	private MessageAnnotations section;

	@Before
	public void setUp()
	{
		section = MessageAnnotations.builder()//
				.addAnnotation("key1", new AMQPSymbol("x-opt-test"))//
				.addAnnotation("key2", (int) -1000)//
				.addAnnotation("key3", (long) -10000)//
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
		{ 0x00, 0x53, 0x72, (byte) 0xc1 };
		Assert.assertArrayEquals("Invalid MessageAnnotations value constructor bytes: ", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testAnnotations()
	{
		assertEquals("Invalid MessageAnnotations symbol property: ", new AMQPSymbol("x-opt-test"), section.getAnnotations().get(new AMQPSymbol("key1")));
		assertEquals("Invalid MessageAnnotations symbol property: ", (int) -1000, section.getAnnotations().get(new AMQPSymbol("key2")));
		assertEquals("Invalid MessageAnnotations symbol property: ", (long) -10000, section.getAnnotations().get(new AMQPSymbol("key3")));
		assertEquals("Invalid MessageAnnotations annotations size: ", 3, section.getAnnotations().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid MessageAnnotations code: ", SectionCode.MESSAGE_ANNOTATIONS, section.getCode());
	}
}
