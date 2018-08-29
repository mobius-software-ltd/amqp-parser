package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum ReceiveCode
{
	FIRST((short) 0), SECOND((short) 1);

	private static Map<Short, ReceiveCode> map = new HashMap<Short, ReceiveCode>();
	static
	{
		for (ReceiveCode legEnum : ReceiveCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private short type;

	public short getType()
	{
		return type;
	}

	private ReceiveCode(final short leg)
	{
		type = leg;
	}

	public static ReceiveCode valueOf(short code)
	{
		ReceiveCode result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized receive-code: " + code);
		return result;
	}

}
