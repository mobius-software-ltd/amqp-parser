package com.mobius.software.amqp.parser.sections;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class AMQPValue implements AMQPSection
{
	private Object value;

	public AMQPValue()
	{
	}

	public AMQPValue(Object value)
	{
		this.value = value;
	}

	@Override
	public TLVAmqp getValue()
	{

		TLVAmqp val = value != null ? AMQPWrapper.wrap(value) : new TLVNull();

		DescribedConstructor constructor = new DescribedConstructor(val.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x77 }));
		val.setConstructor(constructor);

		return val;
	}

	@Override
	public void fill(TLVAmqp value)
	{
		if (!value.isNull())
			this.value = AMQPUnwrapper.unwrap(value);
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.VALUE;
	}

	@Override
	public String toString()
	{
		return "AMQPValue [value=" + value + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		AMQPValue other = (AMQPValue) obj;
		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}

	public Object getVal()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

}
