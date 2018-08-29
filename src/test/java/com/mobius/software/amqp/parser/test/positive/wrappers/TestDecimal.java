package com.mobius.software.amqp.parser.test.positive.wrappers;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.mobius.software.amqp.parser.wrappers.AMQPDecimal;

public class TestDecimal
{
	private AMQPDecimal decimal;

	@Test
	public void testByteFromValue()
	{
		decimal = new AMQPDecimal((byte) 127);
		assertEquals("Invalid Decimal byte value: ", (byte) 127, decimal.getByte());
		Assert.assertArrayEquals("Invalid Decimal Byte encoded value: ", new byte[]
		{ 127 }, decimal.getValue());
	}

	@After
	public void tearDown()
	{
		decimal = null;
	}

	@Test
	public void testByteFromArray()
	{
		decimal = new AMQPDecimal(new byte[]
		{ 127 });
		assertEquals("Invalid Decimal byte value: ", (byte) 127, decimal.getByte());
		Assert.assertArrayEquals("Invalid Decimal Byte encoded value: ", new byte[]
		{ 127 }, decimal.getValue());
	}

	@Test
	public void testShortFromValue()
	{
		decimal = new AMQPDecimal((short) 10000);
		assertEquals("Invalid Decimal short value: ", (short) 10000, decimal.getShort());
		Assert.assertArrayEquals("Invalid Decimal Short encoded value: ", ByteBuffer.allocate(2).putShort((short) 10000).array(), decimal.getValue());
	}

	@Test
	public void testShortFromArray()
	{
		decimal = new AMQPDecimal(ByteBuffer.allocate(2).putShort((short) 10000).array());
		assertEquals("Invalid Decimal short value: ", (short) 10000, decimal.getShort());
		Assert.assertArrayEquals("Invalid Decimal Short encoded value: ", ByteBuffer.allocate(2).putShort((short) 10000).array(), decimal.getValue());
	}

	@Test
	public void testIntFromValue()
	{
		decimal = new AMQPDecimal(-20);
		assertEquals("Invalid Decimal int value: ", -20, decimal.getInt());
		Assert.assertArrayEquals("Invalid Decimal Integer encoded value: ", ByteBuffer.allocate(4).putInt(-20).array(), decimal.getValue());
	}

	@Test
	public void testIntFromArray()
	{
		decimal = new AMQPDecimal(-20);
		assertEquals("Invalid Decimal int value: ", -20, decimal.getInt());
		Assert.assertArrayEquals("Invalid Decimal Integer encoded value: ", ByteBuffer.allocate(4).putInt(-20).array(), decimal.getValue());
	}

	@Test
	public void testLongFromValue()
	{
		decimal = new AMQPDecimal(-999L);
		assertEquals("Invalid Decimal long value: ", -999L, decimal.getLong());
		Assert.assertArrayEquals("Invalid Decimal Long encoded value: ", ByteBuffer.allocate(8).putLong(-999).array(), decimal.getValue());
	}

	@Test
	public void testLongFromArray()
	{
		decimal = new AMQPDecimal(ByteBuffer.allocate(8).putLong(-999).array());
		assertEquals("Invalid Decimal long value: ", -999L, decimal.getLong());
		Assert.assertArrayEquals("Invalid Decimal Long encoded value: ", ByteBuffer.allocate(8).putLong(-999).array(), decimal.getValue());
	}

	@Test
	public void testFloatFromValue()
	{
		decimal = new AMQPDecimal(-100.1F);
		assertEquals("Invalid Decimal float value: ", -100.1F, decimal.getFloat(), 0);
		Assert.assertArrayEquals("Invalid Decimal Float encoded value: ", ByteBuffer.allocate(4).putFloat(-100.1F).array(), decimal.getValue());
	}

	@Test
	public void testFloatFromArray()
	{
		decimal = new AMQPDecimal(ByteBuffer.allocate(4).putFloat(-100.1F).array());
		assertEquals("Invalid Decimal float value: ", -100.1F, decimal.getFloat(), 0);
		Assert.assertArrayEquals("Invalid Decimal Float encoded value: ", ByteBuffer.allocate(4).putFloat(-100.1F).array(), decimal.getValue());
	}

	@Test
	public void testDoubleFromValue()
	{
		decimal = new AMQPDecimal(.888D);
		assertEquals("Invalid Decimal double value: ", .888D, decimal.getDouble(), 0);
		Assert.assertArrayEquals("Invalid Decimal Double encoded value: ", ByteBuffer.allocate(8).putDouble(.888D).array(), decimal.getValue());
	}

	@Test
	public void testDoubleFromArray()
	{
		decimal = new AMQPDecimal(ByteBuffer.allocate(8).putDouble(.888D).array());
		assertEquals("Invalid Decimal double value: ", .888D, decimal.getDouble(), 0);
		Assert.assertArrayEquals("Invalid Decimal Double encoded value: ", ByteBuffer.allocate(8).putDouble(.888D).array(), decimal.getValue());
	}
}
