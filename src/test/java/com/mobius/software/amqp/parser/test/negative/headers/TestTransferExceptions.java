package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.AMQPTransfer;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestTransferExceptions
{
	private AMQPTransfer header;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		header = new AMQPTransfer();
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testGetHandleNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillHandleNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testInvalidListSize()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(12, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testInvalidState()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(7, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

}
