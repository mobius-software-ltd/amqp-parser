package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

public enum ConnectionProperty
{
	PLATFORM("platform"), PRODUCT("product"), QPID_CLIENT_PID("qpid.client_pid"), QPID_CLIENT_PPID("qpid.client_ppid"), QPID_CLIENT_PROCESS("qpid.client_process"), VERSION("version");

	private static Map<String, ConnectionProperty> map = new HashMap<String, ConnectionProperty>();
	static
	{
		for (ConnectionProperty legEnum : ConnectionProperty.values())
		{
			map.put(legEnum.name, legEnum);
		}
	}

	private String name;

	public String getName()
	{
		return name;
	}

	private ConnectionProperty(final String leg)
	{
		name = leg;
	}

	public static ConnectionProperty checkName(String policy)
	{
		return map.get(policy);
	}
}
