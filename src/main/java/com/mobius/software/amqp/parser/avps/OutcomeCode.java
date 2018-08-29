package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum OutcomeCode
{
	OK((short) 0), AUTH((short) 1), SYS((short) 2), SYS_PERM((short) 3), SYS_TEMP((short) 4);

	private static Map<Short, OutcomeCode> map = new HashMap<Short, OutcomeCode>();
	static
	{
		for (OutcomeCode legEnum : OutcomeCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private short type;

	public short getType()
	{
		return type;
	}

	private OutcomeCode(final short leg)
	{
		type = leg;
	}

	public static OutcomeCode valueOf(short code)
	{
		OutcomeCode result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized outcome-code: " + code);
		return result;
	}

}
