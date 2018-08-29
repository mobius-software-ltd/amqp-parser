package com.mobius.software.amqp.parser.test.positive.tlv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestVariableTLV
{
	@Test
	public void testString8()
	{
		TLVAmqp s = AMQPWrapper.wrap("123");
		assertEquals("Invalid string value: ", "123", AMQPUnwrapper.unwrapString(s));
		assertTrue("Invalid string bytes: ", Arrays.equals(new byte[]
		{ (byte) 0xa1, 3, 0x31, 0x32, 0x33 }, s.getBytes()));
		assertEquals("Invalid string length: ", 5, s.getLength());

	}

	@Test
	public void testString32()
	{
		TLVAmqp s = AMQPWrapper.wrap(new String(new byte[256]));
		assertEquals("Invalid string value: ", new String(new byte[256]), AMQPUnwrapper.unwrapString(s));
		byte[] checkArr = new byte[261];
		checkArr[0] = (byte) 0xb1;
		checkArr[3] = 1;
		assertTrue("Invalid string bytes: ", Arrays.equals(checkArr, s.getBytes()));
		assertEquals("Invalid string length: ", 261, s.getLength());
	}

	@Test
	public void testBinary8()
	{
		TLVAmqp b = AMQPWrapper.wrap(new byte[]
		{ 3, 2, 1 });
		assertTrue("Invalid binary value: ", Arrays.equals(new byte[]
		{ 3, 2, 1 }, AMQPUnwrapper.unwrapBinary(b)));
		assertTrue("Invalid binary bytes: ", Arrays.equals(new byte[]
		{ (byte) 0xa0, 3, 3, 2, 1 }, b.getBytes()));
		assertEquals("Invalid binary length: ", 5, b.getLength());

	}

	@Test
	public void testBinary32()
	{
		TLVAmqp b = AMQPWrapper.wrap(new byte[256]);
		assertTrue("Invalid binary value: ", Arrays.equals(new byte[256], AMQPUnwrapper.unwrapBinary(b)));
		byte[] checkArr = new byte[261];
		checkArr[0] = (byte) 0xb0;
		checkArr[3] = 1;
		assertTrue("Invalid binary bytes: ", Arrays.equals(checkArr, b.getBytes()));
		assertEquals("Invalid binary length: ", 261, b.getLength());
	}

	@Test
	public void testSymbol8()
	{
		AMQPSymbol value = new AMQPSymbol("123");
		TLVAmqp s = AMQPWrapper.wrap(value);
		assertEquals("Invalid symbol value: ", value, AMQPUnwrapper.unwrapSymbol(s));
		assertTrue("Invalid symbol bytes: ", Arrays.equals(new byte[]
		{ (byte) 0xa3, 3, 0x31, 0x32, 0x33 }, s.getBytes()));
		assertEquals("Invalid symbol length: ", 5, s.getLength());

	}

	@Test
	public void testSymbol32()
	{
		AMQPSymbol value = new AMQPSymbol(new String(new byte[256]));
		TLVAmqp s = AMQPWrapper.wrap(value);
		assertEquals("Invalid symbol value: ", value, AMQPUnwrapper.unwrapSymbol(s));
		byte[] checkArr = new byte[261];
		checkArr[0] = (byte) 0xb3;
		checkArr[3] = 1;
		assertTrue("Invalid symbol bytes: ", Arrays.equals(checkArr, s.getBytes()));
		assertEquals("Invalid symbol length: ", 261, s.getLength());
	}

}
