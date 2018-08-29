package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.AMQPDisposition;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestDispositionExceptions
{
	private AMQPDisposition header;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		header = AMQPDisposition.builder().build();
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testGetRoleNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillRoleNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetFirstNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillFirstNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVFixed(AMQPType.BOOLEAN_FALSE, new byte[]
		{ 0 }));
		list.addElement(2, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testFillInvalidListSize()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVFixed(AMQPType.BOOLEAN_FALSE, new byte[]
		{ 0 }));
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(6, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testInvalidState()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVFixed(AMQPType.BOOLEAN_FALSE, new byte[]
		{ 0 }));
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(4, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

}
