package com.mobius.software.amqp.parser.tlv.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.LifetimePolicy;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;

public class AMQPLifetimePolicy
{
	private LifetimePolicy code;

	public AMQPLifetimePolicy(LifetimePolicy code)
	{
		this.code = code;
	}

	public TLVList getList()
	{

		TLVList list = new TLVList();
		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getPolicy() }));
		list.setConstructor(constructor);

		return list;
	}

	public void fill(TLVList list)
	{
		if (!list.isNull())
		{
			DescribedConstructor constructor = (DescribedConstructor) list.getConstructor();
			code = LifetimePolicy.valueOf(constructor.getDescriptorCode() & 0xff);
		}
	}

	public LifetimePolicy getCode()
	{
		return code;
	}

}
