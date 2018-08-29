package com.mobius.software.amqp.parser.test.negative.types;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestUnwrapperExceptions
{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	private TLVAmqp tlv;

	@Before
	public void setUp()
	{
		expectedEx.expect(IllegalArgumentException.class);
		tlv = new TLVFixed(AMQPType.BYTE, new byte[]
		{ 0 });
	}

	@After
	public void tearDown()
	{
		expectedEx = null;
		tlv = null;
	}

	@Test
	public void testUnsupportedType()
	{
		ArrayList<TLVAmqp> list = new ArrayList<>();
		AMQPUnwrapper.unwrapMap(new TLVList(AMQPType.SOURCE, list));
	}

	@Test
	public void testUByte()
	{
		AMQPUnwrapper.unwrapUByte(tlv);
	}

	@Test
	public void testByte()
	{
		AMQPUnwrapper.unwrapByte(new TLVFixed(AMQPType.UBYTE, new byte[]
		{ 0 }));
	}

	@Test
	public void testUShort()
	{
		AMQPUnwrapper.unwrapUShort(tlv);
	}

	@Test
	public void testShort()
	{
		AMQPUnwrapper.unwrapShort(tlv);
	}

	@Test
	public void testUInt()
	{
		AMQPUnwrapper.unwrapUInt(tlv);
	}

	@Test
	public void testInt()
	{
		AMQPUnwrapper.unwrapInt(tlv);
	}

	@Test
	public void testULong()
	{
		AMQPUnwrapper.unwrapULong(tlv);
	}

	@Test
	public void testLong()
	{
		AMQPUnwrapper.unwrapLong(tlv);
	}

	@Test
	public void testFloat()
	{
		AMQPUnwrapper.unwrapFloat(tlv);
	}

	@Test
	public void testDouble()
	{
		AMQPUnwrapper.unwrapDouble(tlv);
	}

	@Test
	public void testBoolean()
	{
		AMQPUnwrapper.unwrapBool(tlv);
	}

	@Test
	public void testChar()
	{
		AMQPUnwrapper.unwrapChar(tlv);
	}

	@Test
	public void testTimestamp()
	{
		AMQPUnwrapper.unwrapTimestamp(tlv);
	}

	@Test
	public void testUuid()
	{
		AMQPUnwrapper.unwrapUuid(tlv);
	}

	@Test
	public void testDecimal()
	{
		AMQPUnwrapper.unwrapDecimal(tlv);
	}

	@Test
	public void testBinary()
	{
		AMQPUnwrapper.unwrapBinary(tlv);
	}

	@Test
	public void testString()
	{
		AMQPUnwrapper.unwrapString(tlv);
	}

	@Test
	public void testSymbol()
	{
		AMQPUnwrapper.unwrapSymbol(tlv);
	}

	@Test
	public void testList()
	{
		AMQPUnwrapper.unwrapList(tlv);
	}

	@Test
	public void testMap()
	{
		AMQPUnwrapper.unwrapMap(tlv);
	}

	@Test
	public void testArray()
	{
		AMQPUnwrapper.unwrapArray(tlv);
	}

}
