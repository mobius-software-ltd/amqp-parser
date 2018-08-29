package com.mobius.software.amqp.parser.test.positive.tlv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestFixedTLV
{
	@Test
	public void testNull()
	{
		TLVNull n = new TLVNull();
		assertNull("Invalid 'null' value: ", n.getValue());
		assertTrue("Invalid 'null' bytes: ", Arrays.equals(new byte[]
		{ 0x40 }, n.getBytes()));
		assertEquals("Invalid 'null' length: ", 1, n.getLength());
	}

	@Test
	public void testBoolean()
	{
		TLVAmqp b = new TLVFixed(AMQPType.BOOLEAN, new byte[]
		{ 0 });
		assertFalse("Invalid boolean value: ", AMQPUnwrapper.unwrapBool(b));
		assertTrue("Invalid boolean bytes: ", Arrays.equals(new byte[]
		{ 0x56, 0 }, b.getBytes()));
		assertEquals("Invalid boolean length: ", 2, b.getLength());
		b = new TLVFixed(AMQPType.BOOLEAN, new byte[]
		{ 1 });
		assertTrue("Invalid boolean value: ", AMQPUnwrapper.unwrapBool(b));
		assertTrue("Invalid boolean bytes: ", Arrays.equals(new byte[]
		{ 0x56, 1 }, b.getBytes()));
		assertEquals("Invalid boolean length: ", 2, b.getLength());
	}

	@Test
	public void testBooleanTrue()
	{
		TLVAmqp b = AMQPWrapper.wrap(true);
		assertTrue("Invalid boolean_true value: ", AMQPUnwrapper.unwrapBool(b));
		assertTrue("Invalid boolean_true bytes: ", Arrays.equals(new byte[]
		{ 0x41 }, b.getBytes()));
		assertEquals("Invalid boolean_true length: ", 1, b.getLength());
	}

	@Test
	public void testBooleanFalse()
	{
		TLVAmqp b = AMQPWrapper.wrap(false);
		assertFalse("Invalid boolean_false value: ", AMQPUnwrapper.unwrapBool(b));
		assertTrue("Invalid boolean_false bytes: ", Arrays.equals(new byte[]
		{ 0x42 }, b.getBytes()));
		assertEquals("Invalid boolean_false length: ", 1, b.getLength());
	}

	@Test
	public void testUByte()
	{
		TLVAmqp b = null;
		for (int i = 0; i < 256; i++)
		{
			b = AMQPWrapper.wrap((short) i);
			assertEquals("Invalid ubyte value: ", (short) i, AMQPUnwrapper.unwrapUByte(b));
			assertTrue("Invalid ubyte bytes: ", Arrays.equals(new byte[]
			{ 0x50, (byte) i }, b.getBytes()));
			assertEquals("Invalid ubyte length: ", 2, b.getLength());
		}
	}

	@Test
	public void testByte()
	{
		TLVAmqp b = null;
		for (int i = -128; i < 128; i++)
		{
			b = AMQPWrapper.wrap((byte) i);
			assertEquals("Invalid byte value: ", (byte) i, AMQPUnwrapper.unwrapByte(b));
			assertTrue("Invalid byte bytes: ", Arrays.equals(new byte[]
			{ 0x51, (byte) i }, b.getBytes()));
			assertEquals("Invalid byte length: ", 2, b.getLength());
		}
	}

	@Test
	public void testUShort()
	{
		TLVAmqp s = AMQPWrapper.wrap(60000);
		assertEquals("Invalid short value: ", 60000, AMQPUnwrapper.unwrapUShort(s));
		assertTrue("Invalid ushort bytes: ", Arrays.equals(new byte[]
		{ 0x60, (byte) 0xEA, 0x60 }, s.getBytes()));
		assertEquals("Invalid ushort length: ", 3, s.getLength());
	}

	@Test
	public void testShort()
	{
		TLVAmqp s = AMQPWrapper.wrap((short) -5536);
		assertEquals("Invalid short value: ", (short) -5536, AMQPUnwrapper.unwrapShort(s));
		assertTrue("Invalid short bytes: ", Arrays.equals(new byte[]
		{ 0x61, (byte) 0xEA, 0x60 }, s.getBytes()));
		assertEquals("Invalid short length: ", 3, s.getLength());
	}

	@Test
	public void testUInt0()
	{
		TLVAmqp i = AMQPWrapper.wrap(Long.valueOf(0));
		assertEquals("Invalid uint_0 value: ", 0L, AMQPUnwrapper.unwrapUInt(i));
		assertTrue("Invalid uint_0 bytes: ", Arrays.equals(new byte[]
		{ 0x43 }, i.getBytes()));
		assertEquals("Invalid uint_0 length: ", 1, i.getLength());
	}

	@Test
	public void testSmallUInt()
	{
		TLVAmqp i = AMQPWrapper.wrap(Long.valueOf(255));
		assertEquals("Invalid small_uint value: ", 255L, AMQPUnwrapper.unwrapUInt(i));
		assertTrue("Invalid small_uint bytes: ", Arrays.equals(new byte[]
		{ 0x52, (byte) 0xff }, i.getBytes()));
		assertEquals("Invalid small_uint length: ", 2, i.getLength());
	}

	@Test
	public void testUInt()
	{
		TLVAmqp i = AMQPWrapper.wrap(Long.valueOf(256));
		assertEquals("Invalid uint value: ", 256L, AMQPUnwrapper.unwrapUInt(i));
		assertTrue("Invalid uint bytes: ", Arrays.equals(new byte[]
		{ 0x70, 0, 0, 1, 0 }, i.getBytes()));
		assertEquals("Invalid uint length: ", 5, i.getLength());
		i = AMQPWrapper.wrap(Long.valueOf(2147483648L));
		assertEquals("Invalid uint value: ", 2147483648L, AMQPUnwrapper.unwrapUInt(i));
		assertTrue("Invalid uint bytes: ", Arrays.equals(new byte[]
		{ 0x70, (byte) 0x80, 0, 0, 0 }, i.getBytes()));
		assertEquals("Invalid uint length: ", 5, i.getLength());
	}

	@Test
	public void testSmallInt()
	{
		TLVAmqp i = AMQPWrapper.wrap(-128);
		assertEquals("Invalid small_int value: ", -128, AMQPUnwrapper.unwrapInt(i));
		assertTrue("Invalid small_int bytes: ", Arrays.equals(new byte[]
		{ 0x54, (byte) 0x80 }, i.getBytes()));
		assertEquals("Invalid small_uint length: ", 2, i.getLength());
	}

	@Test
	public void testInt()
	{
		TLVAmqp i = AMQPWrapper.wrap(-129);
		assertEquals("Invalid int value: ", -129, AMQPUnwrapper.unwrapInt(i));
		assertTrue("Invalid int bytes: ", Arrays.equals(new byte[]
		{ 0x71, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x7f }, i.getBytes()));
		assertEquals("Invalid int length: ", 5, i.getLength());
	}

	@Test
	public void testULong0()
	{
		TLVAmqp l = AMQPWrapper.wrap(BigInteger.valueOf(0));
		assertEquals("Invalid ulong_0 value: ", BigInteger.valueOf(0), AMQPUnwrapper.unwrapULong(l));
		assertTrue("Invalid ulong_0 bytes: ", Arrays.equals(new byte[]
		{ 0x44 }, l.getBytes()));
		assertEquals("Invalid ulong_0 length: ", 1, l.getLength());
	}

	@Test
	public void testSmallULong()
	{
		TLVAmqp l = AMQPWrapper.wrap(BigInteger.valueOf(255));
		assertEquals("Invalid small_ulong value: ", BigInteger.valueOf(255), AMQPUnwrapper.unwrapULong(l));
		assertTrue("Invalid small_ulong bytes: ", Arrays.equals(new byte[]
		{ 0x53, (byte) 0xff }, l.getBytes()));
		assertEquals("Invalid small_ulong length: ", 2, l.getLength());
	}

	@Test
	public void testULong()
	{
		TLVAmqp l = AMQPWrapper.wrap(BigInteger.valueOf(256));
		assertEquals("Invalid ulong value: ", BigInteger.valueOf(256), AMQPUnwrapper.unwrapULong(l));
		assertTrue("Invalid ulong bytes: ", Arrays.equals(new byte[]
		{ (byte) 0x80, 0, 0, 0, 0, 0, 0, 1, 0 }, l.getBytes()));
		assertEquals("Invalid ulong length: ", 9, l.getLength());
		l = AMQPWrapper.wrap(new BigInteger("9223372036854775808"));
		assertEquals("Invalid ulong value: ", new BigInteger("9223372036854775808"), AMQPUnwrapper.unwrapULong(l));
		assertTrue("Invalid ulong bytes: ", Arrays.equals(new byte[]
		{ (byte) 0x80, (byte) 0x80, 0, 0, 0, 0, 0, 0, 0 }, l.getBytes()));
		assertEquals("Invalid ulong length: ", 9, l.getLength());
	}

	@Test
	public void testSmallLong()
	{
		TLVAmqp l = AMQPWrapper.wrap(Long.valueOf(-128));
		assertEquals("Invalid small_long value: ", Long.valueOf(-128), AMQPUnwrapper.unwrapLong(l));
		assertTrue("Invalid small_long bytes: ", Arrays.equals(new byte[]
		{ 0x55, (byte) 0x80 }, l.getBytes()));
		assertEquals("Invalid small_long length: ", 2, l.getLength());
	}

	@Test
	public void testLong()
	{
		TLVAmqp l = AMQPWrapper.wrap(Long.valueOf(-129));
		assertEquals("Invalid long value: ", Long.valueOf(-129), AMQPUnwrapper.unwrapLong(l));
		assertTrue("Invalid long bytes: ", Arrays.equals(new byte[]
		{ (byte) 0x81, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x7f }, l.getBytes()));
		assertEquals("Invalid long length: ", 9, l.getLength());
	}

	@Test
	public void testFloat()
	{
		TLVAmqp f = AMQPWrapper.wrap(.5f);
		assertEquals("Invalid float value: ", Float.valueOf(.5f), AMQPUnwrapper.unwrapFloat(f));
		assertTrue("Invalid float bytes: ", Arrays.equals(new byte[]
		{ 0x72, 0x3f, 0, 0, 0 }, f.getBytes()));
		assertEquals("Invalid float length: ", 5, f.getLength());
	}

	@Test
	public void testDouble()
	{
		TLVAmqp f = AMQPWrapper.wrap(.5d);
		assertEquals("Invalid double value: ", Double.valueOf(.5d), AMQPUnwrapper.unwrapDouble(f));
		assertTrue("Invalid double bytes: ", Arrays.equals(new byte[]
		{ (byte) 0x82, 0x3f, (byte) 0xe0, 0, 0, 0, 0, 0, 0 }, f.getBytes()));
		assertEquals("Invalid double length: ", 9, f.getLength());
	}

	@Test
	public void testChar()
	{
		TLVAmqp c = AMQPWrapper.wrap(new Character((char) 43));
		assertEquals("Invalid char value:", 43, AMQPUnwrapper.unwrapChar(c));
		assertTrue("Invalid char bytes: ", Arrays.equals(new byte[]
		{ 0x73, 0, 0, 0, 0x2b }, c.getBytes()));
		assertEquals("Invalid char length: ", 5, c.getLength());
	}
	// TODO decimal values

}
