package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.ReceiveCode;
import com.mobius.software.amqp.parser.avps.RoleCode;
import com.mobius.software.amqp.parser.avps.SendCode;
import com.mobius.software.amqp.parser.header.impl.AMQPAttach;
import com.mobius.software.amqp.parser.terminus.AMQPSource;
import com.mobius.software.amqp.parser.terminus.AMQPTarget;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestAttach
{
	private AMQPAttach header;
	private String capability = "capability";
	private String value = "value";

	@Before
	public void setUp()
	{
		header = AMQPAttach.builder()//
				.handle(10000L)//
				.incompleteUnsettled(true)//
				.initialDeliveryCount(1000L)//
				.maxMessageSize(40000000000L)//
				.name("userName")//
				.rcvSettleMode(ReceiveCode.FIRST)//
				.role(RoleCode.RECEIVER)//
				.sndSettleMode(SendCode.MIXED)//
				.source(AMQPSource.builder().build())//
				.target(AMQPTarget.builder().build())//
				.addDesiredCapability(capability)//
				.addDesiredCapability(capability)//
				.addOfferedCapability(capability)//
				.addOfferedCapability(capability)//
				.addProperty("key1", value)//
				.addProperty("key2", value)//
				.addUnsettled("key1", value)//
				.addUnsettled("key2", value)//
				.build();
		TLVList list = header.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
		capability = null;
		value = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x12, (byte) 0xc0 };
		assertTrue("Invalid Attach arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testHandle()
	{
		assertEquals("Invalid Attach handle: ", Long.valueOf(10000), header.getHandle());
	}

	@Test
	public void testIncompleteUnsettled()
	{
		assertTrue("Invalid Attach incompleteUnsettled", header.getIncompleteUnsettled());
	}

	@Test
	public void testInitialDeliveryCount()
	{
		assertEquals("Invalid Attach initialDeliveryCount: ", Long.valueOf(1000), header.getInitialDeliveryCount());
	}

	@Test
	public void testMaxMessageSize()
	{
		assertEquals("Invalid Attach maxMessageSize: ", BigInteger.valueOf(40000000000L), header.getMaxMessageSize());
	}

	@Test
	public void testName()
	{
		assertEquals("Invalid Attach name: ", "userName", header.getName());
	}

	@Test
	public void testRcvSettleMode()
	{
		assertEquals("Invalid Attach rcvSettleMode: ", ReceiveCode.FIRST, header.getRcvSettleMode());
	}

	@Test
	public void testRole()
	{
		assertEquals("Invalid Attach role: ", RoleCode.RECEIVER, header.getRole());
	}

	@Test
	public void testSndSettleMode()
	{
		assertEquals("Invalid Attach sndSettleMode: ", SendCode.MIXED, header.getSndSettleMode());
	}

	@Test
	public void testSource()
	{
		assertNotNull("Invalid Attach source", header.getSource());
	}

	@Test
	public void testTarget()
	{
		assertNotNull("Invalid Attach target", header.getTarget());
	}

	@Test
	public void testDesiredCapabilities()
	{
		assertEquals("Invalid desired capability", new AMQPSymbol(capability), header.getDesiredCapabilities().get(0));
		assertEquals("Invalid desired capability", new AMQPSymbol(capability), header.getDesiredCapabilities().get(1));
		assertEquals("Invalid desiredCapabilities", 2, header.getDesiredCapabilities().size());
	}

	@Test
	public void testOfferedCapabilities()
	{
		assertEquals("Invalid offered capability", new AMQPSymbol(capability), header.getOfferedCapabilities().get(0));
		assertEquals("Invalid offered capability", new AMQPSymbol(capability), header.getOfferedCapabilities().get(1));
		assertEquals("Invalid desiredCapabilities", 2, header.getOfferedCapabilities().size());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid property", value, header.getProperties().get(new AMQPSymbol("key1")));
		assertEquals("Invalid property", value, header.getProperties().get(new AMQPSymbol("key2")));
		assertEquals("Invalid properties size: ", 2, header.getProperties().size());
	}

	@Test
	public void testOutcomes()
	{
		assertEquals("Invalid unsettled", value, header.getUnsettled().get(new AMQPSymbol("key1")));
		assertEquals("Invalid unsettled", value, header.getUnsettled().get(new AMQPSymbol("key2")));
		assertEquals("Invalid unsettled size: ", 2, header.getUnsettled().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Attach code: ", HeaderCode.ATTACH, header.getCode());
	}

}
