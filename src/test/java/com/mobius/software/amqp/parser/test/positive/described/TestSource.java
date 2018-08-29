package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.DistributionMode;
import com.mobius.software.amqp.parser.avps.TerminusDurability;
import com.mobius.software.amqp.parser.avps.TerminusExpiryPolicy;
import com.mobius.software.amqp.parser.terminus.AMQPSource;
import com.mobius.software.amqp.parser.tlv.impl.AMQPModified;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestSource
{
	private AMQPSource source;
	private String capability = "capability";
	private String outcome = "outcome";
	private String value = "value";

	@Before
	public void setUp()
	{
		source = AMQPSource.builder()//
				.address("localhost")//
				.withDefaultOutcomeModified(AMQPModified.builder().build())//
				.distributionMode(DistributionMode.COPY)//
				.durable(TerminusDurability.CONFIGURATION)//
				.dynamic(true)//
				.expiryPeriod(TerminusExpiryPolicy.LINK_DETACH)//
				.timeout(60000L)//
				.addCapability(capability)//
				.addCapability(capability)//
				.addFilter("key1", value)//
				.addFilter("key2", value)//
				.addOutcome(outcome)//
				.addOutcome(outcome)//
				.addDynamicNodeProperty("key1", value)//
				.addDynamicNodeProperty("key2", value)//
				.build();
		TLVList list = source.toArgumentsList();
		source.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		source = null;
		capability = null;
		outcome = null;
		value = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x28, (byte) 0xc0 };
		assertTrue("Invalid Source constructor bytes: ", Arrays.equals(expectedArray, source.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testAddress()
	{
		assertEquals("Invalid Source address: ", "localhost", source.getAddress());
	}

	@Test
	public void testDefaultOutcome()
	{
		assertNotNull("Invalid Source defaultOutcome", source.getDefaultOutcome());
	}

	@Test
	public void testDistributionmode()
	{
		assertEquals("Invalid Source distributionMode: ", DistributionMode.COPY, source.getDistributionMode());
	}

	@Test
	public void testDurability()
	{
		assertEquals("Invalid Source durability: ", TerminusDurability.CONFIGURATION, source.getDurable());
	}

	@Test
	public void testDynamic()
	{
		assertTrue("Invalid Source dynamic", source.getDynamic());
	}

	@Test
	public void testExpiryPeriod()
	{
		assertEquals("Invalid Source expiryPeriod: ", TerminusExpiryPolicy.LINK_DETACH, source.getExpiryPeriod());
	}

	@Test
	public void testTimeout()
	{
		assertEquals("Invalid Source timeout: ", Long.valueOf(60000), source.getTimeout());
	}

	@Test
	public void testCapabilities()
	{
		assertEquals("Invalid Source capability: ", new AMQPSymbol(capability), source.getCapabilities().get(0));
		assertEquals("Invalid Source capability: ", new AMQPSymbol(capability), source.getCapabilities().get(1));
		assertEquals("Invalid Source capabilities size: ", 2, source.getCapabilities().size());
	}

	@Test
	public void testFilted()
	{
		assertEquals("Invalid Source filter", value, source.getFilter().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Source filter", value, source.getFilter().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Source filter size: ", 2, source.getFilter().size());
	}

	@Test
	public void testOutcomes()
	{
		assertEquals("Invalid Source outcome: ", new AMQPSymbol(outcome), source.getOutcomes().get(0));
		assertEquals("Invalid Source outcome: ", new AMQPSymbol(outcome), source.getOutcomes().get(1));
		assertEquals("Invalid Source outcome size: ", 2, source.getOutcomes().size());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid Source properties", value, source.getDynamicNodeProperties().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Source properties", value, source.getDynamicNodeProperties().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Source properties size: ", 2, source.getDynamicNodeProperties().size());
	}
}
