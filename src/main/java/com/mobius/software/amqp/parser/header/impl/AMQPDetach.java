package com.mobius.software.amqp.parser.header.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.AMQPError;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class AMQPDetach extends AMQPHeader
{
	private Long handle;
	private Boolean closed;
	private AMQPError error;

	public AMQPDetach()
	{
		super(HeaderCode.DETACH, 2, 0, 0);
	}

	private AMQPDetach(HeaderCode code, int doff, int type, int channel, Long handle, Boolean closed, AMQPError error)
	{
		super(code, doff, type, channel);
		this.handle = handle;
		this.closed = closed;
		this.error = error;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (handle == null)
			throw new MalformedHeaderException("Detach header's handle can't be null");
		list.addElement(0, AMQPWrapper.wrap(handle));

		if (closed != null)
			list.addElement(1, AMQPWrapper.wrap(closed));

		if (error != null)
			list.addElement(2, error.toArgumentsList());

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size == 0)
			throw new MalformedHeaderException("Received malformed Detach header: handle can't be null");

		if (size > 3)
			throw new MalformedHeaderException("Received malformed Detach header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Detach header: handle can't be null");
			handle = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				closed = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
			{
				AMQPType code = element.getCode();
				if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
					throw new MalformedHeaderException("Expected type 'ERROR' - received: " + element.getCode());
				error = AMQPError.builder().build();
				error.fromArgumentsList((TLVList) element);
			}
		}
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private Long handle;
		private Boolean closed;
		private AMQPError error;

		public AMQPDetach build()
		{
			return new AMQPDetach(HeaderCode.DETACH, doff, type, channel, handle, closed, error);
		}

		public Builder handle(Long handle)
		{
			this.handle = handle;
			return this;
		}

		public Builder closed(boolean closed)
		{
			this.closed = closed;
			return this;
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
		return "AMQPDetach [handle=" + handle + ", closed=" + closed + ", error=" + error + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((closed == null) ? 0 : closed.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((handle == null) ? 0 : handle.hashCode());
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
		AMQPDetach other = (AMQPDetach) obj;
		if (closed == null)
		{
			if (other.closed != null)
				return false;
		}
		else if (!closed.equals(other.closed))
			return false;
		if (error == null)
		{
			if (other.error != null)
				return false;
		}
		else if (!error.equals(other.error))
			return false;
		if (handle == null)
		{
			if (other.handle != null)
				return false;
		}
		else if (!handle.equals(other.handle))
			return false;
		return true;
	}

	public Long getHandle()
	{
		return handle;
	}

	public void setHandle(Long handle)
	{
		this.handle = handle;
	}

	public Boolean getClosed()
	{
		return closed;
	}

	public void setClosed(Boolean closed)
	{
		this.closed = closed;
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
