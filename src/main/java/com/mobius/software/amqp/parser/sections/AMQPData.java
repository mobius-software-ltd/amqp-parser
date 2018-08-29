package com.mobius.software.amqp.parser.sections;

import java.util.Arrays;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class AMQPData implements AMQPSection
{
	private byte[] data;

	public AMQPData()
	{
	}

	public AMQPData(byte[] data)
	{
		this.data = data;
	}

	@Override
	public TLVAmqp getValue()
	{

		TLVAmqp bin = null;
		if (data != null)
			bin = AMQPWrapper.wrap(data);
		else
			bin = new TLVNull();

		DescribedConstructor constructor = new DescribedConstructor(bin.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x75 }));
		bin.setConstructor(constructor);

		return bin;
	}

	@Override
	public void fill(TLVAmqp value)
	{
		if (!value.isNull())
			data = AMQPUnwrapper.unwrapBinary(value);
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.DATA;
	}

	@Override
	public String toString()
	{
		return "AMQPData [data=" + Arrays.toString(data) + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
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
		AMQPData other = (AMQPData) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setValue(byte[] value)
	{
		this.data = value;
	}
}
