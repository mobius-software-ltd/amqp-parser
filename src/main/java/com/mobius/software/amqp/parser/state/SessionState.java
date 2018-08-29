package com.mobius.software.amqp.parser.state;

public enum SessionState
{
	UNMAPPED, BEGIN_SENT, BEGIN_RCVD, MAPPED, END_SENT, END_RCVD, DISCARDING;
}
