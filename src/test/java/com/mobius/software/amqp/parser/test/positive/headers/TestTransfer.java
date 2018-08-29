package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.ReceiveCode;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.header.impl.AMQPTransfer;
import com.mobius.software.amqp.parser.sections.*;
import com.mobius.software.amqp.parser.tlv.impl.AMQPReceived;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPMessageFormat;

public class TestTransfer
{
	private AMQPTransfer header;

	@Before
	public void setUp()
	{
		header = AMQPTransfer.builder()//
				.aborted(true)//
				.batchable(false)//
				.deliveryId(10L)//
				.deliveryTag(new byte[]
				{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })//
				.handle(100L)//
				.messageFormat(new AMQPMessageFormat(1000L))//
				.more(true)//
				.rcvSettleMode(ReceiveCode.SECOND)//
				.resume(false)//
				.settled(true)//
				.withState(new AMQPReceived())//
				.addSection(new AMQPFooter())//
				.addSection(AMQPProperties.builder().build())//
				.addSection(ApplicationProperties.builder().build())//
				.addSection(DeliveryAnnotations.builder().build())//
				.addSection(MessageAnnotations.builder().build())//
				.addSection(MessageHeader.builder().build())//
				.addSection(new AMQPData())//
				.addSection(AMQPSequence.builder().build())//
				.addSection(new AMQPValue())//
				.build();
		TLVList list = header.toArgumentsList();
		header.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x14, (byte) 0xc0 };
		Assert.assertArrayEquals("Invalid Transfer arguments constructor bytes: ", expectedArray, header.toArgumentsList().getConstructor().getBytes());
	}

	@Test
	public void testAborted()
	{
		assertTrue("Invalid Transfer aborted", header.getAborted());
	}

	@Test
	public void testBatchable()
	{
		assertFalse("Invalid Transfer batchable", header.getBatchable());
	}

	@Test
	public void testDeliveryId()
	{
		assertEquals("Invalid Transfer deliveryId", Long.valueOf(10), header.getDeliveryId());
	}

	@Test
	public void testDeliveryTag()
	{
		Assert.assertArrayEquals("Invalid Transfer delivedyTag", new byte[]
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, header.getDeliveryTag());
	}

	@Test
	public void testHandle()
	{
		assertEquals("Invalid Transfer handle: ", Long.valueOf(100), header.getHandle());
	}

	@Test
	public void testMessageFormat()
	{
		assertEquals("Invalid Transfer messageFormat", Long.valueOf(1000), header.getMessageFormat().encode());
	}

	@Test
	public void testMore()
	{
		assertTrue("Invalid Transfer more", header.getMore());
	}

	@Test
	public void testRcvSettleMode()
	{
		assertEquals("Invalid Transfer rcvSettleMode", ReceiveCode.SECOND, header.getRcvSettleMode());
	}

	@Test
	public void testResume()
	{
		assertFalse("Invalid Transfer resume", header.getResume());
	}

	@Test
	public void testSettled()
	{
		assertTrue("Invalid Transfer settled", header.getSettled());
	}

	@Test
	public void testState()
	{
		assertNotNull("Invalid Transfer state", header.getState());
	}

	@Test
	public void testSections()
	{
		for (SectionCode code : SectionCode.values())
		{
			assertNotNull(String.format("Error while adding %s section to Transfer", code.toString()), header.getSections().get(code));
		}
	}
}
