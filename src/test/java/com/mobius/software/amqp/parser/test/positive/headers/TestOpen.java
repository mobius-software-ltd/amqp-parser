package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPOpen;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestOpen
{
	private AMQPOpen header;
	private String capability = "capability";
	private String locale = "locale";
	private String value = "value";

	@Before
	public void setUp()
	{
		header = AMQPOpen.builder()//
				.channelMax(60000)//
				.containerId("some container id")//
				.hostname("localhost")//
				.idleTimeout(1000L)//
				.maxFrameSize(10000L)//
				.addDesiredCapability(capability)//
				.addDesiredCapability(capability)//
				.addOfferedCapability(capability)//
				.addOfferedCapability(capability)//
				.addIncomingLocale(locale)//
				.addIncomingLocale(locale)//
				.addOutgoingLocale(locale)//
				.addOutgoingLocale(locale)//
				.addProperty("key1", value)//
				.addProperty("key2", value)//
				.addProperty("key3", value)//
				.build();
		
		TLVList list = header.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
		value = null;
		capability = null;
		locale = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x10, (byte) 0xc0 };
		Assert.assertArrayEquals("Invalid Open arguments constructor bytes", expectedArray, header.toArgumentsList().getConstructor().getBytes());
	}

	@Test
	public void testChannelMax()
	{
		assertEquals("Invalid Open channelMax: ", Integer.valueOf(60000), header.getChannelMax());
	}

	@Test
	public void testContainerId()
	{
		assertEquals("Invalid Open containerId: ", "some container id", header.getContainerId());
	}

	@Test
	public void testHostname()
	{
		assertEquals("Invalid Open hostname: ", "localhost", header.getHostname());
	}

	@Test
	public void testIdleTimeout()
	{
		assertEquals("Invalid Open idelTimeout: ", Long.valueOf(1000), header.getIdleTimeout());
	}

	@Test
	public void testMaxFrameSize()
	{
		assertEquals("Invalid Open maxFrameSize: ", Long.valueOf(10000), header.getMaxFrameSize());
	}

	@Test
	public void testDesiredCapabilities()
	{
		assertEquals("Invalid Open desired capability: ", new AMQPSymbol(capability), header.getDesiredCapabilities().get(0));
		assertEquals("Invalid Open desired capability: ", new AMQPSymbol(capability), header.getDesiredCapabilities().get(1));
		assertEquals("Invalid Open desiredCapabilities size: ", 2, header.getDesiredCapabilities().size());
	}

	@Test
	public void testOfferedCapabilities()
	{
		assertEquals("Invalid Open offered capability: ", new AMQPSymbol(capability), header.getOfferedCapabilities().get(0));
		assertEquals("Invalid Open offered capability: ", new AMQPSymbol(capability), header.getOfferedCapabilities().get(1));
		assertEquals("Invalid Open offeredCapabilities size: ", 2, header.getOfferedCapabilities().size());
	}

	@Test
	public void testIncomingLocales()
	{
		assertEquals("Invalid Open incoming locale: ", new AMQPSymbol(locale), header.getIncomingLocales().get(0));
		assertEquals("Invalid Open incoming locale: ", new AMQPSymbol(locale), header.getIncomingLocales().get(1));
		assertEquals("Invalid Open incomingLocales size: ", 2, header.getIncomingLocales().size());
	}

	@Test
	public void testOutgoingLocales()
	{
		assertEquals("Invalid Open outgoing locale: ", new AMQPSymbol(locale), header.getOutgoingLocales().get(0));
		assertEquals("Invalid Open outgoing locale: ", new AMQPSymbol(locale), header.getOutgoingLocales().get(1));
		assertEquals("Invalid Open outgoingLocales size: ", 2, header.getOutgoingLocales().size());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid Open property", value, header.getProperties().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Open property", value, header.getProperties().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Open property", value, header.getProperties().get(new AMQPSymbol("key3")));
		assertEquals("Invalid Open properties size: ", 3, header.getProperties().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Open code: ", HeaderCode.OPEN, header.getCode());
	}
}
