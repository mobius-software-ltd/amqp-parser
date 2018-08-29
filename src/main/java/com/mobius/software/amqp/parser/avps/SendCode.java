package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum SendCode
{
	UNSETTLED((short) 0), SETTLED((short) 1), MIXED((short) 2);

	private static Map<Short, SendCode> map = new HashMap<Short, SendCode>();
	static
	{
		for (SendCode legEnum : SendCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private short type;

	public short getType()
	{
		return type;
	}

	private SendCode(final short leg)
	{
		type = leg;
	}

	public static SendCode valueOf(short code)
	{
		SendCode result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized send-code: " + code);
		return result;
	}
}
