package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.AMQPBegin;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestBeginExceptions
{
	private AMQPBegin header;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		header = AMQPBegin.builder().build();
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
		list.addElement(3, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testFillInvalidListSize()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(2, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(3, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(8, new TLVNull());
		header.fromArgumentsList(list);
	}

}
