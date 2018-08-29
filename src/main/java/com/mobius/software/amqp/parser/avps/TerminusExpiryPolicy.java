package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum TerminusExpiryPolicy
{
	LINK_DETACH("link-detach"), SESSION_END("session-end"), CONNETION_CLOSE("connection-close"), NEVER("never");

	private static Map<String, TerminusExpiryPolicy> map = new HashMap<String, TerminusExpiryPolicy>();
	static
	{
		for (TerminusExpiryPolicy legEnum : TerminusExpiryPolicy.values())
		{
			map.put(legEnum.policy, legEnum);
		}
	}

	private String policy;

	public String getPolicy()
	{
		return policy;
	}

	private TerminusExpiryPolicy(final String leg)
	{
		policy = leg;
	}

	public static TerminusExpiryPolicy getPolicy(String policy)
	{
		TerminusExpiryPolicy code = map.get(policy);
		if (code == null)
			throw new InvalidCodeException("Unrecognized Terminus-expiry-policy: " + policy);
		return code;
	}
}
