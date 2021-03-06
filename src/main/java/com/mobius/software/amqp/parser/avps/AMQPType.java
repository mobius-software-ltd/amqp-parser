package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum AMQPType
{
	SOURCE(0x28), TARGET(0x29), ERROR(0x1D), NULL(0x40), BOOLEAN(0x56), BOOLEAN_TRUE(0x41), BOOLEAN_FALSE(0x42), UBYTE(0x50), USHORT(0x60), UINT(0x70), SMALL_UINT(0x52), UINT_0(0x43), ULONG(0x80), SMALL_ULONG(0x53), ULONG_0(0x44), BYTE(0x51), SHORT(0x61), INT(0x71), SMALL_INT(0x54), LONG(0x81), SMALL_LONG(0x55), FLOAT(0x72), DOUBLE(0x82), DECIMAL_32(0x74), DECIMAL_64(0x84), DECIMAL_128(0x94), CHAR(0x73), TIMESTAMP(0x83), UUID(0x98), BINARY_8(0xA0), BINARY_32(0xB0), STRING_8(0xA1), STRING_32(0xB1), SYMBOL_8(0xA3), SYMBOL_32(0xB3), LIST_0(0x45), LIST_8(0xC0), LIST_32(0xD0), MAP_8(0xC1), MAP_32(0xD1), ARRAY_8(0xE0), ARRAY_32(0xF0);

	private int type;

	private static Map<Integer, AMQPType> map = new HashMap<Integer, AMQPType>();
	static
	{
		for (AMQPType legEnum : AMQPType.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	public int getType()
	{
		return type;
	}

	private AMQPType(final int leg)
	{
		type = leg;
	}

	public static AMQPType valueOf(int code)
	{
		AMQPType result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized amqp-type code: " + code);
		return result;
	}
}
