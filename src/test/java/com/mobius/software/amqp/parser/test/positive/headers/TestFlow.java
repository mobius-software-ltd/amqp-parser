package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPFlow;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestFlow
{
	private AMQPFlow header;
	private String value = "value";

	@Before
	public void setUp()
	{
		header = AMQPFlow.builder()//
				.avaliable(10L)//
				.deliveryCount(100L)//
				.drain(true)//
				.echo(false)//
				.handle(1000L)//
				.incomingWindow(10000L)//
				.linkCredit(100000L)//
				.nextIncomingId(1000000L)//
				.nextOutgoingId(10000000L)//
				.outgoingWindow(100000000L)//
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
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x13, (byte) 0xc0 };
		assertTrue("Invalid Flow constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testAvaliable()
	{
		assertEquals("Invalid Flow avaliable", Long.valueOf(10), header.getAvaliable());
	}

	@Test
	public void testDelivedyCount()
	{
		assertEquals("Invalid Flow deliveryCount: ", Long.valueOf(100), header.getDeliveryCount());
	}

	@Test
	public void testDrain()
	{
		assertTrue("Invalid Flow drain", header.getDrain());
	}

	@Test
	public void testEcho()
	{
		assertFalse("Invalid Flow echo", header.getEcho());
	}

	@Test
	public void testHandle()
	{
		assertEquals("Invalid Flow handle: ", Long.valueOf(1000), header.getHandle());
	}

	@Test
	public void testIncomingWindow()
	{
		assertEquals("Invalid Flow incomingWindow: ", Long.valueOf(10000), header.getIncomingWindow());
	}

	@Test
	public void testLinkCredit()
	{
		assertEquals("Invalid Flow linkCredit: ", Long.valueOf(100000), header.getLinkCredit());
	}

	@Test
	public void testNextIncomingId()
	{
		assertEquals("Invalid Flow nextIncomingId: ", Long.valueOf(1000000), header.getNextIncomingId());
	}

	@Test
	public void testNextOutgoingId()
	{
		assertEquals("Invalid Flow nextOutgoingId: ", Long.valueOf(10000000), header.getNextOutgoingId());
	}

	@Test
	public void testOutgoingWindow()
	{
		assertEquals("Invalid Flow outgoingWindow: ", Long.valueOf(100000000), header.getOutgoingWindow());
	}

	@Test
	public void testProperties()
	{
		assertEquals("Invalid Flow property", value, header.getProperties().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Flow property", value, header.getProperties().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Flow property", value, header.getProperties().get(new AMQPSymbol("key3")));
		assertEquals("Invalid Flow properties size: ", 3, header.getProperties().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Flow code: ", HeaderCode.FLOW, header.getCode());
	}

}
