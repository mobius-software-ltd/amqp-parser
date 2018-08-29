package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.AMQPFlow;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestFlowExceptions
{
	private AMQPFlow header;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		header = AMQPFlow.builder().build();
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testGetNextOutgoingIdNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillNextOutgoingIdNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(2, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(3, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetIncomingWindowIdNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillIncomingWindowNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(3, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetOutgoingWindowNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillOutgoingWindowNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(2, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(4, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetDeliveryCountNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setDeliveryCount(2L);
		header.toArgumentsList();
	}

	@Test
	public void testFillDeliveryCountNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setDeliveryCount(2L);
		TLVList list = header.toArgumentsList();
		header = AMQPFlow.builder().build();
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetLinkCreditNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setLinkCredit(1L);
		header.toArgumentsList();
	}

	@Test
	public void testFillLinkCreditNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setLinkCredit(2L);
		TLVList list = header.toArgumentsList();
		header = AMQPFlow.builder().build();
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetAvaliableNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setAvaliable(2L);
		header.toArgumentsList();
	}

	@Test
	public void testFillAvaliableNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setAvaliable(2L);
		TLVList list = header.toArgumentsList();
		header = AMQPFlow.builder().build();
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetDrainNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setDrain(true);
		header.toArgumentsList();
	}

	@Test
	public void testFillDrainNotNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setIncomingWindow(1L);
		header.setOutgoingWindow(1L);
		header.setNextOutgoingId(1L);
		header.setDrain(false);
		TLVList list = header.toArgumentsList();
		header = AMQPFlow.builder().build();
		header.fromArgumentsList(list);
	}

	@Test
	public void testInvalidListSize()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(2, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(3, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(12, new TLVNull());
		header.fromArgumentsList(list);
	}
}
