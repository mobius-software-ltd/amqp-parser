package com.mobius.software.amqp.parser.test.positive.wrappers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestSymbol
{
	@Test
	public void testValue()
	{
		AMQPSymbol symbol = new AMQPSymbol("test");
		assertEquals("Invalid symbol value: ", "test", symbol.getValue());
	}

	@Test
	public void testEquals()
	{
		AMQPSymbol expected = new AMQPSymbol("test");
		AMQPSymbol actual = new AMQPSymbol("test");
		assertEquals("Invalid symbol equals: ", expected, actual);
	}

}
