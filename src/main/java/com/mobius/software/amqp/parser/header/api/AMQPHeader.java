package com.mobius.software.amqp.parser.header.api;

import com.mobius.software.amqp.parser.avps.HeaderCode;

public abstract class AMQPHeader implements Parsable
{
	protected HeaderCode code;
	protected int doff;
	protected int type;
	protected int channel;

	public AMQPHeader(HeaderCode code, int doff, int type, int channel)
	{
		this.code = code;
		this.doff = doff;
		this.channel = channel;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	protected static class AMQPHeaderBuilder<B extends AMQPHeaderBuilder<B>>
	{
		protected HeaderCode code;
		protected int doff = 2;
		protected int type = 0;
		protected int channel = 0;

		public B code(HeaderCode code)
		{
			this.code = code;
			return (B) this;
		}

		public B doff(int doff)
		{
			this.doff = doff;
			return (B) this;
		}

		public B type(int type)
		{
			this.type = type;
			return (B) this;
		}

		public B channel(int channel)
		{
			this.channel = channel;
			return (B) this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPHeader [code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + channel;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + doff;
		result = prime * result + type;
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
		AMQPHeader other = (AMQPHeader) obj;
		if (channel != other.channel)
			return false;
		if (code != other.code)
			return false;
		if (doff != other.doff)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public int getDoff()
	{
		return doff;
	}

	public void setDoff(int doff)
	{
		this.doff = doff;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getChannel()
	{
		return channel;
	}

	public void setChannel(int channel)
	{
		this.channel = channel;
	}

	public HeaderCode getCode()
	{
		return code;
	}
}
