package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.AMQPOpen;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestOpenExceptions
{
	private AMQPOpen header;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		header = AMQPOpen.builder().build();
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testGetContainerIdNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillContainerIdNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testInvalidListSize()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVNull());
		header.fromArgumentsList(list);
	}
}
