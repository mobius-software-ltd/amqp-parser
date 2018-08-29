package com.mobius.software.amqp.parser.header.impl;

import java.util.ArrayList;
import java.util.List;

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

public class SASLMechanisms extends AMQPHeader
{
	private List<AMQPSymbol> mechanisms;

	public SASLMechanisms()
	{
		super(HeaderCode.MECHANISMS, 2, 1, 0);
	}

	public SASLMechanisms(HeaderCode code, int doff, int type, int channel, List<AMQPSymbol> mechanisms)
	{
		super(code, doff, type, channel);
		this.mechanisms = mechanisms;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (mechanisms == null)
			throw new MalformedHeaderException("At least one SASL Mechanism must be specified");

		list.addElement(0, AMQPWrapper.wrapArray(mechanisms));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x40 }));
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
				mechanisms = AMQPUnwrapper.unwrapArray(element);
		}
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private List<AMQPSymbol> mechanisms = new ArrayList<>();

		public Builder()
		{
			super();
			this.type = 1;
		}
		
		public SASLMechanisms build()
		{
			return new SASLMechanisms(HeaderCode.MECHANISMS, doff, type, channel, mechanisms);
		}

		public Builder addMechanism(String mechanism)
		{
			this.mechanisms.add(new AMQPSymbol(mechanism));
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "SASLMechanisms [mechanisms=" + mechanisms + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((mechanisms == null) ? 0 : mechanisms.hashCode());
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
		SASLMechanisms other = (SASLMechanisms) obj;
		if (mechanisms == null)
		{
			if (other.mechanisms != null)
				return false;
		}
		else if (!mechanisms.equals(other.mechanisms))
			return false;
		return true;
	}

	public List<AMQPSymbol> getMechanisms()
	{
		return mechanisms;
	}

	public void setMechanisms(List<AMQPSymbol> mechanisms)
	{
		this.mechanisms = mechanisms;
	}
}
