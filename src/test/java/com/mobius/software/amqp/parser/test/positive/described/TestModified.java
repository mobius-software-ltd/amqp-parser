package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.tlv.impl.AMQPModified;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestModified
{
	private AMQPModified actual;

	private String key,
			value;

	@Before
	public void setUp()
	{
		key = "x-opt-someAnnotation";
		value = "123";
		actual = AMQPModified.builder()//
				.undeliverableHere(true)//
				.addMessageAnnotation(key, value)//
				.build();
		//in.setDeliveryFailed(false);
		TLVList list = actual.toArgumentsList();
		actual.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		actual = null;
		key = null;
		value = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x27, (byte) 0xc0 };
		assertTrue("Invalid modified constructor bytes: ", Arrays.equals(expectedArray, actual.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testDeliveryFailed()
	{
		assertFalse("Invalid Modified deliveryFailed: ", actual.getDeliveryFailed());
	}

	@Test
	public void testUndeliverableHere()
	{
		assertTrue("Invalid Modified undeliverableHere: ", actual.getUndeliverableHere());
	}

	@Test
	public void testMessageAnnotations()
	{
		assertEquals("Invalid Modified messageAnnotaion value: ", value, actual.getMessageAnnotations().get(new AMQPSymbol(key)));
		assertEquals("Invalid Modified messageAnnotations size: ", 1, actual.getMessageAnnotations().size());
	}

}
