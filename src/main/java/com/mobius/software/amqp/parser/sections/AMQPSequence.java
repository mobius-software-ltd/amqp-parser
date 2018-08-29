package com.mobius.software.amqp.parser.sections;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class AMQPSequence implements AMQPSection
{
	private List<Object> sequence;

	public AMQPSequence()
	{
	}

	private AMQPSequence(List<Object> sequence)
	{
		this.sequence = sequence;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVAmqp getValue()
	{
		TLVList list = new TLVList();
		if (!CollectionUtils.isEmpty(sequence))
			list = AMQPWrapper.wrapList(sequence);

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x76 }));
		list.setConstructor(constructor);
		return list;
	}

	@Override
	public void fill(TLVAmqp list)
	{
		if (!list.isNull())
			sequence = AMQPUnwrapper.unwrapList(list);
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.SEQUENCE;
	}

	public static class Builder
	{
		private List<Object> sequence = new ArrayList<>();

		public AMQPSequence build()
		{
			return new AMQPSequence(sequence);
		}

		public Builder addSequence(Object sequence)
		{
			this.sequence.add(sequence);
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPSequence [sequence=" + sequence + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
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
		AMQPSequence other = (AMQPSequence) obj;
		if (sequence == null)
		{
			if (other.sequence != null)
				return false;
		}
		else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

	public List<Object> getSequence()
	{
		return sequence;
	}
}
