package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.DeliveryAnnotations;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestDeliveryAnnotations
{
	private DeliveryAnnotations section;

	@Before
	public void setUp()
	{
		section = DeliveryAnnotations.builder()//
				.addAnnotation("key1", (short) -10)//
				.addAnnotation("key2", UUID.fromString("1eeeded2-ef6a-4104-89e3-2873ac090f0e"))//
				.addAnnotation("key3", (byte) 0x10)//
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
		{ 0x00, 0x53, 0x71, (byte) 0xc1 };
		Assert.assertArrayEquals("Invalid DeliveryAnnotations value constructor bytes: ", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testAnnotations()
	{
		assertEquals("Invalid DeliveryAnnotations short annotation: ", (short) -10, section.getAnnotations().get(new AMQPSymbol("key1")));
		assertEquals("Invalid DeliveryAnnotations UUID annotation: ", UUID.fromString("1eeeded2-ef6a-4104-89e3-2873ac090f0e"), section.getAnnotations().get(new AMQPSymbol("key2")));
		assertEquals("Invalid DeliveryAnnotations UByte annotation: ", (byte) 0x10, section.getAnnotations().get(new AMQPSymbol("key3")));
		assertEquals("Invalid DeliveryAnnotations annotations size: ", 3, section.getAnnotations().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid DeliveryAnnotations code: ", SectionCode.DELIVERY_ANNOTATIONS, section.getCode());
	}
}
