package com.mobius.software.amqp.parser.header.impl;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class AMQPPing extends AMQPHeader
{
	public static final AMQPPing instance = new AMQPPing();

	public AMQPPing()
	{
		super(HeaderCode.PING, 2, 0, 0);
	}

	@Override
	public TLVList toArgumentsList()
	{
		return null;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{
	}
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
