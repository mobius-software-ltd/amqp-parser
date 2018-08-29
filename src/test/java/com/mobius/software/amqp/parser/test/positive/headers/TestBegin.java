package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPBegin;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestBegin
{
	private AMQPBegin header;
	private String capability = "capability";
	private String value = "value";

	@Before
	public void setUp()
	{
		header = AMQPBegin.builder()//
				.handleMax(1000L)//
				.incomingWindow(10000L)//
				.nextOutgoingId(100000L)//
				.outgoingWindow(1000000L)//
				.remoteChannel(60000)//
				.addDesiredCapability(capability)//
				.addDesiredCapability(capability)//
				.addOfferedCapability(capability)//
				.addOfferedCapability(capability)//
				.addProperty("key1", value)//
				.addProperty("key2", value)//
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
		{ 0x00, 0x53, 0x11, (byte) 0xc0 };
		assertTrue("Invalid Begin constructor bytes: ", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testHandleMax()
	{
		assertEquals("Invalid Begin handleMax: ", Long.valueOf(1000), header.getHandleMax());
	}

	@Test
	public void testIncomingWindow()
	{
		assertEquals("Invalid Begin incomingWindow: ", Long.valueOf(10000), header.getIncomingWindow());
	}

	@Test
	public void testNextOutgoingId()
	{
		assertEquals("Invalid Begin nextOutgoingId: ", Long.valueOf(100000), header.getNextOutgoingId());
	}

	@Test
	public void testOutgoingWindow()
	{
		assertEquals("Invalid Begin outgoingWindow: ", Long.valueOf(1000000), header.getOutgoingWindow());
	}

	@Test
	public void testRemoteChannel()
	{
		assertEquals("Invalod Begin remoteChannel: ", Integer.valueOf(60000), header.getRemoteChannel());
	}

	@Test
	public void testDesiredCapabilities()
	{
		assertEquals("Invalid Begin desired capability: ", new AMQPSymbol(capability), header.getDesiredCapabilities().get(0));
		assertEquals("Invalid Begin desired capability: ", new AMQPSymbol(capability), header.getDesiredCapabilities().get(1));
		assertEquals("Invalid Begin desiredCapabilities size: ", 2, header.getDesiredCapabilities().size());
	}

	@Test
	public void testOfferedCapabilities()
	{
		assertEquals("Invalid Begin offered capability: ", new AMQPSymbol(capability), header.getOfferedCapabilities().get(0));
		assertEquals("Invalid Begin offered capability: ", new AMQPSymbol(capability), header.getOfferedCapabilities().get(1));
		assertEquals("Invalid Begin offeredCapabilities size: ", 2, header.getOfferedCapabilities().size());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid Begin property", value, header.getProperties().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Begin property", value, header.getProperties().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Begin properties size: ", 2, header.getProperties().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Begin code: ", HeaderCode.BEGIN, header.getCode());
	}
}
