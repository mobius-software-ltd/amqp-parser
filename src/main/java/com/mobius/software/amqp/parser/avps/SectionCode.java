package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum SectionCode
{
	HEADER(0x70), DELIVERY_ANNOTATIONS(0x71), MESSAGE_ANNOTATIONS(0x72), PROPERTIES(0x73), APPLICATION_PROPERTIES(0x74), DATA(0x75), SEQUENCE(0x76), VALUE(0x77), FOOTER(0x78);

	private static Map<Integer, SectionCode> map = new HashMap<Integer, SectionCode>();
	static
	{
		for (SectionCode legEnum : SectionCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private int type;

	public Integer getType()
	{
		return type;
	}

	private SectionCode(final int leg)
	{
		type = leg;
	}

	public static SectionCode valueOf(int code)
	{
		SectionCode result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized section-code: " + code);
		return result;
	}

}
