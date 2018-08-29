package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.AMQPType;

public class TestFieldCodes
{
	@Test
	public void testSource()
	{
		assertEquals(String.format("Code %d not accepted", 0x28), AMQPType.SOURCE, AMQPType.valueOf(0x28));
	}

	@Test
	public void testTarget()
	{
		assertEquals(String.format("Code %d not accepted", 0x29), AMQPType.TARGET, AMQPType.valueOf(0x29));
	}

	@Test
	public void testError()
	{
		assertEquals(String.format("Code %d not accepted", 0x1D), AMQPType.ERROR, AMQPType.valueOf(0x1D));
	}

	@Test
	public void testNull()
	{
		assertEquals(String.format("Code %d not accepted", 0x40), AMQPType.NULL, AMQPType.valueOf(0x40));
	}

	@Test
	public void testBoolean()
	{
		assertEquals(String.format("Code %d not accepted", 0x56), AMQPType.BOOLEAN, AMQPType.valueOf(0x56));
	}

	@Test
	public void testBooleanTrue()
	{
		assertEquals(String.format("Code %d not accepted", 0x41), AMQPType.BOOLEAN_TRUE, AMQPType.valueOf(0x41));
	}

	@Test
	public void testBooleanFalse()
	{
		assertEquals(String.format("Code %d not accepted", 0x42), AMQPType.BOOLEAN_FALSE, AMQPType.valueOf(0x42));
	}

	@Test
	public void testUByte()
	{
		assertEquals(String.format("Code %d not accepted", 0x50), AMQPType.UBYTE, AMQPType.valueOf(0x50));
	}

	@Test
	public void testUShort()
	{
		assertEquals(String.format("Code %d not accepted", 0x60), AMQPType.USHORT, AMQPType.valueOf(0x60));
	}

	@Test
	public void test()
	{
		assertEquals(String.format("Code %d not accepted", 0x70), AMQPType.UINT, AMQPType.valueOf(0x70));
	}

	@Test
	public void testSmallUInt()
	{
		assertEquals(String.format("Code %d not accepted", 0x52), AMQPType.SMALL_UINT, AMQPType.valueOf(0x52));
	}

	@Test
	public void testUInt_0()
	{
		assertEquals(String.format("Code %d not accepted", 0x43), AMQPType.UINT_0, AMQPType.valueOf(0x43));
	}

	@Test
	public void testULong()
	{
		assertEquals(String.format("Code %d not accepted", 0x80), AMQPType.ULONG, AMQPType.valueOf(0x80));
	}

	@Test
	public void testSmallULong()
	{
		assertEquals(String.format("Code %d not accepted", 0x53), AMQPType.SMALL_ULONG, AMQPType.valueOf(0x53));
	}

	@Test
	public void testULong_0()
	{
		assertEquals(String.format("Code %d not accepted", 0x44), AMQPType.ULONG_0, AMQPType.valueOf(0x44));
	}

	@Test
	public void testByte()
	{
		assertEquals(String.format("Code %d not accepted", 0x51), AMQPType.BYTE, AMQPType.valueOf(0x51));
	}

	@Test
	public void testShort()
	{
		assertEquals(String.format("Code %d not accepted", 0x61), AMQPType.SHORT, AMQPType.valueOf(0x61));
	}

	@Test
	public void testInt()
	{
		assertEquals(String.format("Code %d not accepted", 0x71), AMQPType.INT, AMQPType.valueOf(0x71));
	}

	@Test
	public void testSmallInt()
	{
		assertEquals(String.format("Code %d not accepted", 0x54), AMQPType.SMALL_INT, AMQPType.valueOf(0x54));
	}

	@Test
	public void testLong()
	{
		assertEquals(String.format("Code %d not accepted", 0x81), AMQPType.LONG, AMQPType.valueOf(0x81));
	}

	@Test
	public void testSmallLong()
	{
		assertEquals(String.format("Code %d not accepted", 0x55), AMQPType.SMALL_LONG, AMQPType.valueOf(0x55));
	}

	@Test
	public void testFloat()
	{
		assertEquals(String.format("Code %d not accepted", 0x72), AMQPType.FLOAT, AMQPType.valueOf(0x72));
	}

	@Test
	public void testDouble()
	{
		assertEquals(String.format("Code %d not accepted", 0x82), AMQPType.DOUBLE, AMQPType.valueOf(0x82));
	}

	@Test
	public void testDecimal32()
	{
		assertEquals(String.format("Code %d not accepted", 0x74), AMQPType.DECIMAL_32, AMQPType.valueOf(0x74));
	}

	@Test
	public void testDecimal64()
	{
		assertEquals(String.format("Code %d not accepted", 0x84), AMQPType.DECIMAL_64, AMQPType.valueOf(0x84));
	}

	@Test
	public void testDecimal128()
	{
		assertEquals(String.format("Code %d not accepted", 0x94), AMQPType.DECIMAL_128, AMQPType.valueOf(0x94));
	}

	@Test
	public void testChar()
	{
		assertEquals(String.format("Code %d not accepted", 0x73), AMQPType.CHAR, AMQPType.valueOf(0x73));
	}

	@Test
	public void testTimestamp()
	{
		assertEquals(String.format("Code %d not accepted", 0x83), AMQPType.TIMESTAMP, AMQPType.valueOf(0x83));
	}

	@Test
	public void testUuid()
	{
		assertEquals(String.format("Code %d not accepted", 0x98), AMQPType.UUID, AMQPType.valueOf(0x98));
	}

	@Test
	public void testBinary8()
	{
		assertEquals(String.format("Code %d not accepted", 0xA0), AMQPType.BINARY_8, AMQPType.valueOf(0xA0));
	}

	@Test
	public void testBinary32()
	{
		assertEquals(String.format("Code %d not accepted", 0xB0), AMQPType.BINARY_32, AMQPType.valueOf(0xB0));
	}

	@Test
	public void testString8()
	{
		assertEquals(String.format("Code %d not accepted", 0xA1), AMQPType.STRING_8, AMQPType.valueOf(0xA1));
	}

	@Test
	public void testString32()
	{
		assertEquals(String.format("Code %d not accepted", 0xB1), AMQPType.STRING_32, AMQPType.valueOf(0xB1));
	}

	@Test
	public void testSymbol8()
	{
		assertEquals(String.format("Code %d not accepted", 0xA3), AMQPType.SYMBOL_8, AMQPType.valueOf(0xA3));
	}

	@Test
	public void testSymbol32()
	{
		assertEquals(String.format("Code %d not accepted", 0xB3), AMQPType.SYMBOL_32, AMQPType.valueOf(0xB3));
	}

	@Test
	public void testList0()
	{
		assertEquals(String.format("Code %d not accepted", 0x45), AMQPType.LIST_0, AMQPType.valueOf(0x45));
	}

	@Test
	public void testList8()
	{
		assertEquals(String.format("Code %d not accepted", 0xC0), AMQPType.LIST_8, AMQPType.valueOf(0xC0));
	}

	@Test
	public void testList32()
	{
		assertEquals(String.format("Code %d not accepted", 0xD0), AMQPType.LIST_32, AMQPType.valueOf(0xD0));
	}

	@Test
	public void testMap8()
	{
		assertEquals(String.format("Code %d not accepted", 0xC1), AMQPType.MAP_8, AMQPType.valueOf(0xC1));
	}

	@Test
	public void testMap32()
	{
		assertEquals(String.format("Code %d not accepted", 0xD1), AMQPType.MAP_32, AMQPType.valueOf(0xD1));
	}

	@Test
	public void testArray8()
	{
		assertEquals(String.format("Code %d not accepted", 0xE0), AMQPType.ARRAY_8, AMQPType.valueOf(0xE0));
	}

	@Test
	public void testArray32()
	{
		assertEquals(String.format("Code %d not accepted", 0xF0), AMQPType.ARRAY_32, AMQPType.valueOf(0xF0));
	}
}
