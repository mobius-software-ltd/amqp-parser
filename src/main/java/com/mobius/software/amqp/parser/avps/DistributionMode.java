package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum DistributionMode
{
	MOVE("move"), COPY("copy");

	private static Map<String, DistributionMode> map = new HashMap<String, DistributionMode>();
	static
	{
		for (DistributionMode legEnum : DistributionMode.values())
		{
			map.put(legEnum.mode, legEnum);
		}
	}

	private String mode;

	public String getMode()
	{
		return mode;
	}

	private DistributionMode(final String leg)
	{
		mode = leg;
	}

	public static DistributionMode getMode(String mode)
	{
		DistributionMode code = map.get(mode);
		if (code == null)
			throw new InvalidCodeException("Unrecignized Distribution-mode: " + mode);
		return code;
	}
}
