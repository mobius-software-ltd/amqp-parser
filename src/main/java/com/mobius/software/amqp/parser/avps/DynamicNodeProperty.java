package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

public enum DynamicNodeProperty
{
	SUPPORTED_DIST_MODES("supported-dist-modes"), DURABLE("durable"), AUTO_DELETE("auto-delete"), ALTERNATE_EXCHANGE("alternate-exchange"), EXCHANGE_TYPE("exchange-type");

	private static Map<String, DynamicNodeProperty> map = new HashMap<String, DynamicNodeProperty>();
	static
	{
		for (DynamicNodeProperty legEnum : DynamicNodeProperty.values())
		{
			map.put(legEnum.name, legEnum);
		}
	}

	private String name;

	public String getName()
	{
		return name;
	}

	private DynamicNodeProperty(final String leg)
	{
		name = leg;
	}

	public static DynamicNodeProperty checkName(String policy)
	{
		return map.get(policy);
	}
}
