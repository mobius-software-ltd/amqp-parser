package com.mobius.software.amqp.parser.header.impl;

import java.util.Arrays;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class SASLInit extends AMQPHeader
{
	private AMQPSymbol mechanism;
	private byte[] initialResponse;
	private String hostName;

	public SASLInit()
	{
		super(HeaderCode.INIT, 2, 1, 0);
	}

	private SASLInit(HeaderCode code, int doff, int type, int channel, AMQPSymbol mechanism, byte[] initialResponse, String hostName)
	{
		super(code, doff, type, channel);
		this.mechanism = mechanism;
		this.initialResponse = initialResponse;
		this.hostName = hostName;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private AMQPSymbol mechanism;
		private byte[] initialResponse;
		private String hostName;

		public Builder()
		{
			super();
			this.type = 1;
		}

		public SASLInit build()
		{
			return new SASLInit(HeaderCode.INIT, doff, type, channel, mechanism, initialResponse, hostName);
		}

		public Builder mechanism(String mechanism)
		{
			this.mechanism = new AMQPSymbol(mechanism);
			return this;
		}

		public Builder initialResponse(byte[] initialResponse)
		{
			this.initialResponse = initialResponse;
			return this;
		}

		public Builder hostName(String hostName)
		{
			this.hostName = hostName;
			return this;
		}
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (mechanism == null)
			throw new MalformedHeaderException("SASL-Init header's mechanism can't be null");
		list.addElement(0, AMQPWrapper.wrap(mechanism));

		if (initialResponse != null)
			list.addElement(1, AMQPWrapper.wrap(initialResponse));
		if (hostName != null)
			list.addElement(2, AMQPWrapper.wrap(hostName));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x41 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size == 0)
			throw new MalformedHeaderException("Received malformed SASL-Init header: mechanism can't be null");

		if (size > 3)
			throw new MalformedHeaderException("Received malformed SASL-Init header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed SASL-Init header: mechanism can't be null");
			mechanism = AMQPUnwrapper.unwrapSymbol(element);
		}

		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				initialResponse = AMQPUnwrapper.unwrapBinary(element);
		}

		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				hostName = AMQPUnwrapper.unwrapString(element);
		}
	}

	@Override
	public String toString()
	{
		return "SASLInit [mechanism=" + mechanism + ", initialResponse=" + Arrays.toString(initialResponse) + ", hostName=" + hostName + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + Arrays.hashCode(initialResponse);
		result = prime * result + ((mechanism == null) ? 0 : mechanism.hashCode());
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
		SASLInit other = (SASLInit) obj;
		if (hostName == null)
		{
			if (other.hostName != null)
				return false;
		}
		else if (!hostName.equals(other.hostName))
			return false;
		if (!Arrays.equals(initialResponse, other.initialResponse))
			return false;
		if (mechanism == null)
		{
			if (other.mechanism != null)
				return false;
		}
		else if (!mechanism.equals(other.mechanism))
			return false;
		return true;
	}

	public String getMechanism()
	{
		return mechanism.getValue();
	}

	public void setMechanism(String mechanism)
	{
		this.mechanism = new AMQPSymbol(mechanism);
	}

	public byte[] getInitialResponse()
	{
		return initialResponse;
	}

	public void setInitialResponse(byte[] initialResponse)
	{
		this.initialResponse = initialResponse;
	}

	public String getHostName()
	{
		return hostName;
	}

	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

}
