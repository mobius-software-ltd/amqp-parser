package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum LifetimePolicy
{
	DELETE_ON_CLOSE(0x2b), DELETE_ON_NO_LINKS(0x2c), DELETE_ON_NO_MESSAGES(0x2d), DELETE_ON_NO_LINKS_OR_MESSAGES(0x2e);

	private static Map<Integer, LifetimePolicy> map = new HashMap<Integer, LifetimePolicy>();
	static
	{
		for (LifetimePolicy legEnum : LifetimePolicy.values())
		{
			map.put(legEnum.policy, legEnum);
		}
	}

	private int policy;

	public int getPolicy()
	{
		return policy;
	}

	private LifetimePolicy(final int leg)
	{
		policy = leg;
	}

	public static LifetimePolicy valueOf(int code)
	{
		LifetimePolicy result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized lifetime-policy code: " + code);
		return result;
	}
}
