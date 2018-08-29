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

public class SASLChallenge extends AMQPHeader
{
	private byte[] challenge;

	public SASLChallenge()
	{
		super(HeaderCode.CHALLENGE, 2, 1, 0);
	}

	public SASLChallenge(HeaderCode code, int doff, int type, int channel, byte[] challenge)
	{
		super(code, doff, type, channel);
		this.challenge = challenge;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private byte[] challenge;

		public Builder()
		{
			super();
			this.type = 1;
		}

		public SASLChallenge build()
		{
			return new SASLChallenge(HeaderCode.CHALLENGE, doff, type, channel, challenge);
		}

		public Builder challenge(byte[] challenge)
		{
			this.challenge = challenge;
			return this;
		}
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (challenge == null)
			throw new MalformedHeaderException("SASL-Challenge header's challenge can't be null");
		list.addElement(0, AMQPWrapper.wrap(challenge));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x42 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size == 0)
			throw new MalformedHeaderException("Received malformed SASL-Challenge header: challenge can't be null");

		if (size > 1)
			throw new MalformedHeaderException("Received malformed SASL-Challenge header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed SASL-Challenge header: challenge can't be null");
			challenge = AMQPUnwrapper.unwrapBinary(element);
		}
	}

	@Override
	public String toString()
	{
		return "SASLChallenge [challenge=" + Arrays.toString(challenge) + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(challenge);
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
		SASLChallenge other = (SASLChallenge) obj;
		if (!Arrays.equals(challenge, other.challenge))
			return false;
		return true;
	}

	public byte[] getChallenge()
	{
		return challenge;
	}

	public void setChallenge(byte[] challenge)
	{
		this.challenge = challenge;
	}
}
