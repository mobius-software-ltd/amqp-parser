package com.mobius.software.amqp.parser.state;

public enum ConnectionState
{
	START, HDR_CRV, YDR_SENT, HDR_EXCH, OPEN_PIPE, OC_PIPE, OPEN_RCVD, OPEN_SENT, CLOSE_PIPE, OPENED, CLOSE_RCVD, CLOSE_SENT, DISCARDING, END;
}
