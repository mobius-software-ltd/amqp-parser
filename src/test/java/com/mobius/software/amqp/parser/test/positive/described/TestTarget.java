package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.TerminusDurability;
import com.mobius.software.amqp.parser.avps.TerminusExpiryPolicy;
import com.mobius.software.amqp.parser.terminus.AMQPTarget;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestTarget
{
	private AMQPTarget target;
	private String value = "value";
	private String capability = "capability";

	@Before
	public void setUp()
	{
		target = AMQPTarget.builder()//
				.address("localhost")//
				.durable(TerminusDurability.UNSETTLED_STATE)//
				.dynamic(true)//
				.expiryPeriod(TerminusExpiryPolicy.CONNETION_CLOSE)//
				.timeout(60000L)//
				.addCapability(capability)//
				.addCapability(capability)//
				.addDynamicNodeProperty("key1", value)//
				.addDynamicNodeProperty("key2", value)//
				.addDynamicNodeProperty("key3", value)//
				.build();
		TLVList list = target.toArgumentsList();
		target.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		target = null;
		value = null;
		capability = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x29, (byte) 0xc0 };
		assertTrue("Invalid Target constructor bytes: ", Arrays.equals(expectedArray, target.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testAddress()
	{
		assertEquals("Invalid Target address: ", "localhost", target.getAddress());
	}

	@Test
	public void testDurability()
	{
		assertEquals("Invalid Target durability: ", TerminusDurability.UNSETTLED_STATE, target.getDurable());
	}

	@Test
	public void testDynamic()
	{
		assertTrue("Invalid Target dynamic: ", target.getDynamic());
	}

	@Test
	public void testExpiryPeriod()
	{
		assertEquals("Invalid Target expiryPeriod: ", TerminusExpiryPolicy.CONNETION_CLOSE, target.getExpiryPeriod());
	}

	@Test
	public void testTimeout()
	{
		assertEquals("Invalid Target timeout: ", Long.valueOf(60000), target.getTimeout());
	}

	@Test
	public void testCapabilities()
	{
		assertEquals("Invalid Target capability: ", new AMQPSymbol(capability), target.getCapabilities().get(0));
		assertEquals("Invalid Target capability: ", new AMQPSymbol(capability), target.getCapabilities().get(1));
		assertEquals("Invalid Target capabilities size: ", 2, target.getCapabilities().size());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid Target property: ", value, target.getDynamicNodeProperties().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Target property: ", value, target.getDynamicNodeProperties().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Target property: ", value, target.getDynamicNodeProperties().get(new AMQPSymbol("key3")));
		assertEquals("Invalid Target properties size: ", 3, target.getDynamicNodeProperties().size());
	}

}
