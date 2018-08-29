package com.mobius.software.amqp.parser.tlv.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.SimpleConstructor;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TLVNull extends TLVAmqp
{
	public TLVNull()
	{
		super(new SimpleConstructor(AMQPType.NULL));
	}

	@Override
	public byte[] getBytes()
	{
		return constructor.getBytes();
	}

	@Override
	public int getLength()
	{
		return 1;
	}

	@Override
	public byte[] getValue()
	{
		return null;
	}

	@Override
	public String toString()
	{
		return "NULL";
	}

}
