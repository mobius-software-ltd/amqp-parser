package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.RoleCode;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.AMQPAttach;
import com.mobius.software.amqp.parser.terminus.AMQPSource;
import com.mobius.software.amqp.parser.terminus.AMQPTarget;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestAttachExceptions
{
	private AMQPAttach header;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		header = AMQPAttach.builder().build();
	}

	@After
	public void tearDown()
	{
		header = null;
	}

	@Test
	public void testGetNameNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillNameNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		list.addElement(2, new TLVFixed(AMQPType.BOOLEAN_FALSE, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
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
		list.addElement(0, new TLVFixed(AMQPType.STRING_8, new byte[1]));
		list.addElement(2, new TLVFixed(AMQPType.BOOLEAN_FALSE, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
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
		list.addElement(0, new TLVFixed(AMQPType.STRING_8, new byte[1]));
		list.addElement(1, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

	@Test
	public void testGetInitialDeliveryCountNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setRole(RoleCode.SENDER);
		header.toArgumentsList();
	}

	@Test
	public void testFillInitialDeliveryCountNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setName("name");
		header.setHandle(1000L);
		header.setRole(RoleCode.SENDER);
		header.setInitialDeliveryCount(1L);
		TLVList list = header.toArgumentsList();
		list.setElement(9, new TLVNull());
		header = AMQPAttach.builder().build();
		header.fromArgumentsList(list);
	}

	@Test
	public void testFillInvalidListSize()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setName("name");
		header.setHandle(1000L);
		header.setRole(RoleCode.SENDER);
		header.setInitialDeliveryCount(1L);
		TLVList list = header.toArgumentsList();
		list.addElement(14, new TLVNull());
		header = AMQPAttach.builder().build();
		header.fromArgumentsList(list);
	}

	@Test
	public void testFillInvalidSource()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setName("name");
		header.setHandle(1000L);
		header.setRole(RoleCode.SENDER);
		header.setInitialDeliveryCount(1L);
		AMQPSource source = AMQPSource.builder().build();
		header.setSource(source);
		TLVList list = header.toArgumentsList();
		list.setElement(5, new TLVFixed(AMQPType.UBYTE, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

	@Test
	public void testFillInvalidTarget()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.setName("name");
		header.setHandle(1000L);
		header.setRole(RoleCode.SENDER);
		header.setInitialDeliveryCount(1L);
		AMQPTarget target = AMQPTarget.builder().build();
		header.setTarget(target);
		TLVList list = header.toArgumentsList();
		list.setElement(6, new TLVFixed(AMQPType.UBYTE, new byte[]
		{ 0 }));
		header.fromArgumentsList(list);
	}

}
