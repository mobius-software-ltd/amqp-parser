package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

public enum RoleCode
{
	SENDER(false), RECEIVER(true);

	private static Map<Boolean, RoleCode> map = new HashMap<Boolean, RoleCode>();
	static
	{
		for (RoleCode legEnum : RoleCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private boolean type;

	public boolean getType()
	{
		return type;
	}

	private RoleCode(final boolean leg)
	{
		type = leg;
	}

	public static RoleCode valueOf(boolean code)
	{
		return map.get(code);
	}
}
