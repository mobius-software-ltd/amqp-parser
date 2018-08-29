package com.mobius.software.amqp.parser.state;

import com.mobius.software.amqp.parser.avps.HeaderCode;

public enum SASLState
{
	NONE, MECHANISMS_SENT, INIT_RECEIVED, CHALLENGE_SENT, RESPONSE_RECEIVED, NEGOTIATED;

	public boolean validate(HeaderCode code)
	{
		switch (code)
		{
		case MECHANISMS:
			return this == NONE;
		case INIT:
			return this == MECHANISMS_SENT;
		case CHALLENGE:
			return this == INIT_RECEIVED;
		case RESPONSE:
			return this == CHALLENGE_SENT;
		case OUTCOME:
			return this == RESPONSE_RECEIVED;
		default:
			return this == NEGOTIATED;
		}
	}
}
