package com.mobius.software.amqp.parser.tlv.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;

public class AMQPReleased implements AMQPOutcome, AMQPState
{
	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();
		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x26 }));
		list.setConstructor(constructor);
		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{
	}

	@Override
	public String toString()
	{
		return "AMQPReleased []";
	}

	@Override
	public int hashCode()
	{
		return 31;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
