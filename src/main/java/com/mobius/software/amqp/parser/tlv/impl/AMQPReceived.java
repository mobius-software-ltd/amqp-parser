package com.mobius.software.amqp.parser.tlv.impl;

import java.math.BigInteger;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class AMQPReceived implements AMQPState
{
	private Long sectionNumber;
	private BigInteger sectionOffset;

	public AMQPReceived()
	{
	}

	public AMQPReceived(Long sectionNumber, BigInteger sectionOffset)
	{
		this.sectionNumber = sectionNumber;
		this.sectionOffset = sectionOffset;
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (sectionNumber != null)
			list.addElement(0, AMQPWrapper.wrap(sectionNumber));
		if (sectionOffset != null)
			list.addElement(1, AMQPWrapper.wrap(sectionOffset));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x23 }));
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
				sectionNumber = AMQPUnwrapper.unwrapUInt(element);
		}
		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				sectionOffset = AMQPUnwrapper.unwrapULong(element);
		}
	}

	@Override
	public String toString()
	{
		return "AMQPReceived [sectionNumber=" + sectionNumber + ", sectionOffset=" + sectionOffset + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sectionNumber == null) ? 0 : sectionNumber.hashCode());
		result = prime * result + ((sectionOffset == null) ? 0 : sectionOffset.hashCode());
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
		AMQPReceived other = (AMQPReceived) obj;
		if (sectionNumber == null)
		{
			if (other.sectionNumber != null)
				return false;
		}
		else if (!sectionNumber.equals(other.sectionNumber))
			return false;
		if (sectionOffset == null)
		{
			if (other.sectionOffset != null)
				return false;
		}
		else if (!sectionOffset.equals(other.sectionOffset))
			return false;
		return true;
	}

	public Long getSectionNumber()
	{
		return sectionNumber;
	}

	public void setSectionNumber(Long sectionNumber)
	{
		this.sectionNumber = sectionNumber;
	}

	public BigInteger getSectionOffset()
	{
		return sectionOffset;
	}

	public void setSectionOffset(BigInteger sectionOffset)
	{
		this.sectionOffset = sectionOffset;
	}

}
