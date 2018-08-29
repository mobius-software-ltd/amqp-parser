package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.ErrorCode;
import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestError
{
	private AMQPError error;
	String value;

	@Before
	public void setUp()
	{
		value = "value";
		error = AMQPError.builder()//
				.condition(ErrorCode.CONNECTION_FORCED)//
				.description("some description")//
				.addInfo("key1", value)//
				.addInfo("key2", value)//
				.addInfo("key3", value)//
				.build();
		TLVList list = error.toArgumentsList();
		error.fromArgumentsList(list);
	}

	@After
	public void tearDown()
	{
		error = null;
		value = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x1d, (byte) 0xc0 };
		assertTrue("Invalid Error constructor bytes: ", Arrays.equals(expectedArray, error.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testCondition()
	{
		assertEquals("Invalid Error condition: ", ErrorCode.CONNECTION_FORCED, error.getCondition());
	}

	@Test
	public void testDescription()
	{
		assertEquals("Invalid Error description: ", "some description", error.getDescription());
	}

	@Test
	public void testInfo()
	{
		assertEquals("Invalid Error info element: ", value, error.getInfo().get(new AMQPSymbol("key1")));
		assertEquals("Invalid Error info element: ", value, error.getInfo().get(new AMQPSymbol("key2")));
		assertEquals("Invalid Error info element: ", value, error.getInfo().get(new AMQPSymbol("key3")));
		assertEquals("Invalid Error info size: ", 3, error.getInfo().size());
	}
}
