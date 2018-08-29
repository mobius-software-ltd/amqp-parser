package com.mobius.software.amqp.parser.header.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class AMQPEnd extends AMQPHeader
{
	private AMQPError error;

	public AMQPEnd()
	{
		super(HeaderCode.END, 2, 0, 0);
	}

	private AMQPEnd(HeaderCode code, int doff, int type, int channel, AMQPError error)
	{
		super(code, doff, type, channel);
		this.error = error;
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (error != null)
			list.addElement(0, error.toArgumentsList());
		else
			list.addElement(0, new TLVNull());

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));
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

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private AMQPError error;

		public AMQPEnd build()
		{
			return new AMQPEnd(HeaderCode.END, doff, type, channel, error);
		}

		public Builder error(AMQPError error)
		{
			this.error = error;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPEnd [error=" + error + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		return result;
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
		AMQPEnd other = (AMQPEnd) obj;
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
