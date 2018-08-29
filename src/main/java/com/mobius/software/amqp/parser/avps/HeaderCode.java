package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.impl.*;

public enum HeaderCode
{
	OPEN(0x10), BEGIN(0x11), ATTACH(0x12), FLOW(0x13), TRANSFER(0x14), DISPOSITION(0x15), DETACH(0x16), END(0x17), CLOSE(0x18), MECHANISMS(0x40), INIT(0x41), CHALLENGE(0x42), RESPONSE(0x43), OUTCOME(0x44), PING(0xff), PROTO(0xfe);

	private static Map<Integer, HeaderCode> map = new HashMap<Integer, HeaderCode>();
	static
	{
		for (HeaderCode legEnum : HeaderCode.values())
		{
			map.put(legEnum.type, legEnum);
		}
	}

	private int type;

	public int getType()
	{
		return type;
	}

	private HeaderCode(final int leg)
	{
		type = leg;
	}

	public static HeaderCode valueOf(int code)
	{
		HeaderCode result = map.get(code);
		if (result == null)
			throw new InvalidCodeException("Unrecognized header argument code: " + code);
		return result;
	}

	public AMQPHeader emptyHeader()
	{
		switch (this)
		{
		case ATTACH:
			return AMQPAttach.builder().build();
		case BEGIN:
			return AMQPBegin.builder().build();
		case CLOSE:
			return AMQPClose.builder().build();
		case DETACH:
			return AMQPDetach.builder().build();
		case DISPOSITION:
			return AMQPDisposition.builder().build();
		case END:
			return AMQPEnd.builder().build();
		case FLOW:
			return AMQPFlow.builder().build();
		case OPEN:
			return AMQPOpen.builder().build();
		case TRANSFER:
			return new AMQPTransfer();
		default:
			throw new MalformedHeaderException("Received amqp-header with unrecognized performative");
		}
	}

	public AMQPHeader emptySASL()
	{
		switch (this)
		{
		case CHALLENGE:
			return new SASLChallenge();
		case INIT:
			return new SASLInit();
		case MECHANISMS:
			return SASLMechanisms.builder().build();
		case OUTCOME:
			return SASLOutcome.builder().build();
		case RESPONSE:
			return new SASLResponse();
		default:
			throw new MalformedHeaderException("Received sasl-header with unrecognized arguments code");
		}
	}
}
