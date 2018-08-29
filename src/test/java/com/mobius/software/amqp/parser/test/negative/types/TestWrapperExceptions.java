package com.mobius.software.amqp.parser.test.negative.types;

import java.math.BigInteger;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.header.api.AMQPWrapper;

public class TestWrapperExceptions
{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		expectedEx.expect(IllegalArgumentException.class);
	}

	@After
	public void tearDown()
	{
		expectedEx = null;
	}

	@Test
	public void testUnsupportedObject()
	{
		AMQPWrapper.wrap(new HashSet<>());
	}

	@Test
	public void testUbyteNegative()
	{
		AMQPWrapper.wrapUByte((short) -1);
	}

	@Test
	public void testUIntNegative()
	{
		AMQPWrapper.wrapUInt(-1L);
	}

	@Test
	public void testULongNegative()
	{
		AMQPWrapper.wrapULong(BigInteger.valueOf(-1));
	}

	@Test
	public void testULongNull()
	{
		AMQPWrapper.wrapULong(null);
	}

	@Test
	public void testBinaryNull()
	{
		AMQPWrapper.wrapBinary(null);
	}

	@Test
	public void testUuidNull()
	{
		AMQPWrapper.wrapUuid(null);
	}

	@Test
	public void testUShortNegative()
	{
		AMQPWrapper.wrapUShort(-1);
	}

	@Test
	public void testTimestampNull()
	{
		AMQPWrapper.wrapTimestamp(null);
	}

	@Test
	public void testDecimal32Null()
	{
		AMQPWrapper.wrapDecimal32(null);
	}

	@Test
	public void testDecimal64Null()
	{
		AMQPWrapper.wrapDecimal64(null);
	}

	@Test
	public void testDecimal128Null()
	{
		AMQPWrapper.wrapDecimal128(null);
	}

	@Test
	public void testStringNull()
	{
		AMQPWrapper.wrapString(null);
	}

	@Test
	public void testSymbolNull()
	{
		AMQPWrapper.wrapSymbol(null);
	}

	@Test
	public void testListNull()
	{
		AMQPWrapper.wrapList(null);
	}

	@Test
	public void testMapNull()
	{
		AMQPWrapper.wrapMap(null);
	}

	@Test
	public void testArrayNull()
	{
		AMQPWrapper.wrapArray(null);
	}
}
