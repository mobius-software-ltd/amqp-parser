package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.RoleCode;
import com.mobius.software.amqp.parser.header.impl.AMQPDisposition;
import com.mobius.software.amqp.parser.tlv.impl.AMQPModified;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestDisposition
{
	private AMQPDisposition header;

	@Before
	public void setUp()
	{
		header = AMQPDisposition.builder()//
				.batchable(true)//
				.first(1000L)//
				.last(10000L)//
				.role(RoleCode.SENDER)//
				.settled(false)//
				.withStateModified(AMQPModified.builder().build())//
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
		{ 0x00, 0x53, 0x15, (byte) 0xc0 };
		assertTrue("Invalid Disposition arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testBatchable()
	{
		assertTrue("Invalid Disposition batchable", header.getBatchable());
	}

	@Test
	public void testFirst()
	{
		assertEquals("Invalid Disposition first: ", Long.valueOf(1000), header.getFirst());
	}

	@Test
	public void testLast()
	{
		assertEquals("Invalid Disposition last: ", Long.valueOf(10000), header.getLast());
	}

	@Test
	public void testRole()
	{
		assertEquals("Invalid Disposition role: ", RoleCode.SENDER, header.getRole());
	}

	@Test
	public void testSettled()
	{
		assertFalse("Invalid Disposition settled", header.getSettled());
	}

	@Test
	public void testState()
	{
		assertNotNull("Invalid Disposition state", header.getState());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Disposition code: ", HeaderCode.DISPOSITION, header.getCode());
	}

}
