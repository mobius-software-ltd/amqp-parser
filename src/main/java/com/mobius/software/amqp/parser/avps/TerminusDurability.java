package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum TerminusDurability
{
	NONE(0), CONFIGURATION(1), UNSETTLED_STATE(2);

	private static Map<Long, TerminusDurability> map = new HashMap<Long, TerminusDurability>();
	static
	{
		for (TerminusDurability legEnum : TerminusDurability.values())
		{
			map.put(legEnum.code, legEnum);
		}
	}

	private long code;

	public Long getCode()
	{
		return code;
	}

	private TerminusDurability(final long leg)
	{
		code = leg;
	}

	public static TerminusDurability valueOf(long code)
	{
		TerminusDurability result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized Terminus-durability code: " + code);
		return result;
	}
}
