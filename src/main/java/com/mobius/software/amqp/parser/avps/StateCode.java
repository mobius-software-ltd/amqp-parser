package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum StateCode
{
	RECEIVED(0x23), ACCEPTED(0x24), REJECTED(0x25), RELEASED(0x26), MODIFIED(0x27);

	private static Map<Integer, StateCode> map = new HashMap<Integer, StateCode>();
	static
	{
		for (StateCode legEnum : StateCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private int type;

	public int getType()
	{
		return type;
	}

	private StateCode(final int leg)
	{
		type = leg;
	}

	public static StateCode valueOf(int code)
	{
		StateCode result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized state-code: " + code);
		return result;
	}
}
