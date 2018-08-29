package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.SASLMechanisms;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestMechanisms
{
	private SASLMechanisms header;

	@Before
	public void setUp()
	{
		header = SASLMechanisms.builder()//
				.addMechanism("NATIVE")//
				.addMechanism("CRAM-MD5")//
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
		{ 0x00, 0x53, 0x40, (byte) 0xc0 };
		assertTrue("Invalid Mechanisms arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testMechanisms()
	{
		assertEquals("Invalid Mechanisms element: ", new AMQPSymbol("NATIVE"), header.getMechanisms().get(0));
		assertEquals("Invalid Mechanisms element: ", new AMQPSymbol("CRAM-MD5"), header.getMechanisms().get(1));
		assertEquals("Invalid Mechanisms mechanisms size: ", 2, header.getMechanisms().size());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Mechanisms code: ", HeaderCode.MECHANISMS, header.getCode());
	}

}
