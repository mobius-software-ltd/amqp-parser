package com.mobius.software.amqp.parser.header.impl;

import java.util.Arrays;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.OutcomeCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class SASLOutcome extends AMQPHeader
{
	private OutcomeCode outcomeCode;
	private byte[] additionalData;

	public SASLOutcome()
	{
		super(HeaderCode.OUTCOME, 2, 0, 0);
	}

	private SASLOutcome(HeaderCode code, int doff, int type, int channel, OutcomeCode outcomeCode, byte[] additionalData)
	{
		super(code, doff, type, channel);
		this.outcomeCode = outcomeCode;
		this.additionalData = additionalData;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (outcomeCode == null)
			throw new MalformedHeaderException("SASL-Outcome header's code can't be null");
		list.addElement(0, AMQPWrapper.wrap(outcomeCode.getType()));

		if (additionalData != null)
			list.addElement(1, AMQPWrapper.wrap(additionalData));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x44 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size == 0)
			throw new MalformedHeaderException("Received malformed SASL-Outcome header: code can't be null");

		if (size > 2)
			throw new MalformedHeaderException("Received malformed SASL-Outcome header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed SASL-Outcome header: code can't be null");
			outcomeCode = OutcomeCode.valueOf(AMQPUnwrapper.unwrapUByte(element));
		}

		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				additionalData = AMQPUnwrapper.unwrapBinary(element);
		}
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private OutcomeCode outcomeCode;
		private byte[] additionalData;

		public Builder()
		{
			super();
			this.type = 1;
		}
		
		public SASLOutcome build()
		{
			return new SASLOutcome(HeaderCode.OUTCOME, doff, type, channel, outcomeCode, additionalData);
		}

		public Builder outcomeCode(OutcomeCode outcomeCode)
		{
			this.outcomeCode = outcomeCode;
			return this;
		}

		public Builder additionalData(byte[] additionalData)
		{
			this.additionalData = additionalData;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "SASLOutcome [outcomeCode=" + outcomeCode + ", additionalData=" + Arrays.toString(additionalData) + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(additionalData);
		result = prime * result + ((outcomeCode == null) ? 0 : outcomeCode.hashCode());
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
		SASLOutcome other = (SASLOutcome) obj;
		if (!Arrays.equals(additionalData, other.additionalData))
			return false;
		if (outcomeCode != other.outcomeCode)
			return false;
		return true;
	}

	public OutcomeCode getOutcomeCode()
	{
		return outcomeCode;
	}

	public void setOutcomeCode(OutcomeCode outcomeCode)
	{
		this.outcomeCode = outcomeCode;
	}

	public byte[] getAdditionalData()
	{
		return additionalData;
	}

	public void setAdditionalData(byte[] additionalData)
	{
		this.additionalData = additionalData;
	}
}
