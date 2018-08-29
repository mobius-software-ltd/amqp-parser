package com.mobius.software.amqp.parser.tlv.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class AMQPRejected implements AMQPOutcome, AMQPState
{
	private AMQPError error;

	public AMQPRejected()
	{
	}

	public AMQPRejected(AMQPError error)
	{
		this.error = error;
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (error != null)
			list.addElement(0, error.toArgumentsList());

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x25 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{
		if (list.getList().size() > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (!element.isNull())
			{
				AMQPType code = element.getCode();
				if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
					throw new IllegalArgumentException("Expected type 'ERROR' - received: " + element.getCode());
				error = AMQPError.builder().build();
				error.fromArgumentsList((TLVList) element);
			}
		}
	}

	@Override
	public String toString()
	{
		return "AMQPRejected [error=" + error + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AMQPRejected other = (AMQPRejected) obj;
		if (error == null)
		{
			if (other.error != null)
				return false;
		}
		else if (!error.equals(other.error))
			return false;
		return true;
	}

	public AMQPError getError()
	{
		return error;
	}

	public void setError(AMQPError error)
	{
		this.error = error;
	}
}
