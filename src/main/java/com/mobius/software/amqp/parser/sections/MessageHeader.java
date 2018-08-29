package com.mobius.software.amqp.parser.sections;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class MessageHeader implements AMQPSection
{
	private Boolean durable;
	private Short priority;
	private Long milliseconds;
	private Boolean firstAquirer;
	private Long deliveryCount;

	public MessageHeader()
	{
	}

	private MessageHeader(Boolean durable, Short priority, Long milliseconds, Boolean firstAquirer, Long deliveryCount)
	{
		this.durable = durable;
		this.priority = priority;
		this.milliseconds = milliseconds;
		this.firstAquirer = firstAquirer;
		this.deliveryCount = deliveryCount;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVAmqp getValue()
	{

		TLVList list = new TLVList();

		if (durable != null)
			list.addElement(0, AMQPWrapper.wrap(durable));
		if (priority != null)
			list.addElement(1, AMQPWrapper.wrap(priority));
		if (milliseconds != null)
			list.addElement(2, AMQPWrapper.wrap(milliseconds));
		if (firstAquirer != null)
			list.addElement(3, AMQPWrapper.wrap(firstAquirer));
		if (deliveryCount != null)
			list.addElement(4, AMQPWrapper.wrap(deliveryCount));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x70 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fill(TLVAmqp value)
	{
		TLVList list = (TLVList) value;
		if (list.getList().size() > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (!element.isNull())
				durable = AMQPUnwrapper.unwrapBool(element);
		}
		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				priority = AMQPUnwrapper.unwrapUByte(element);
		}
		if (list.getList().size() > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				milliseconds = AMQPUnwrapper.unwrapUInt(element);
		}
		if (list.getList().size() > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (!element.isNull())
				firstAquirer = AMQPUnwrapper.unwrapBool(element);
		}
		if (list.getList().size() > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				deliveryCount = AMQPUnwrapper.unwrapUInt(element);
		}
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.HEADER;
	}

	public static class Builder
	{
		private Boolean durable;
		private Short priority;
		private Long milliseconds;
		private Boolean firstAquirer;
		private Long deliveryCount;

		public MessageHeader build()
		{
			return new MessageHeader(durable, priority, milliseconds, firstAquirer, deliveryCount);
		}

		public Builder durable(boolean durable)
		{
			this.durable = durable;
			return this;
		}

		public Builder priority(Short priority)
		{
			this.priority = priority;
			return this;
		}

		public Builder milliseconds(long milliseconds)
		{
			this.milliseconds = milliseconds;
			return this;
		}

		public Builder firstAquirer(boolean firstAquirer)
		{
			this.firstAquirer = firstAquirer;
			return this;
		}

		public Builder deliveryCount(long deliveryCount)
		{
			this.deliveryCount = deliveryCount;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "MessageHeader [durable=" + durable + ", priority=" + priority + ", milliseconds=" + milliseconds + ", firstAquirer=" + firstAquirer + ", deliveryCount=" + deliveryCount + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryCount == null) ? 0 : deliveryCount.hashCode());
		result = prime * result + ((durable == null) ? 0 : durable.hashCode());
		result = prime * result + ((firstAquirer == null) ? 0 : firstAquirer.hashCode());
		result = prime * result + ((milliseconds == null) ? 0 : milliseconds.hashCode());
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
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
		MessageHeader other = (MessageHeader) obj;
		if (deliveryCount == null)
		{
			if (other.deliveryCount != null)
				return false;
		}
		else if (!deliveryCount.equals(other.deliveryCount))
			return false;
		if (durable == null)
		{
			if (other.durable != null)
				return false;
		}
		else if (!durable.equals(other.durable))
			return false;
		if (firstAquirer == null)
		{
			if (other.firstAquirer != null)
				return false;
		}
		else if (!firstAquirer.equals(other.firstAquirer))
			return false;
		if (milliseconds == null)
		{
			if (other.milliseconds != null)
				return false;
		}
		else if (!milliseconds.equals(other.milliseconds))
			return false;
		if (priority == null)
		{
			if (other.priority != null)
				return false;
		}
		else if (!priority.equals(other.priority))
			return false;
		return true;
	}

	public Boolean getDurable()
	{
		return durable;
	}

	public void setDurable(Boolean durable)
	{
		this.durable = durable;
	}

	public Short getPriority()
	{
		return priority;
	}

	public void setPriority(Short priority)
	{
		this.priority = priority;
	}

	public Long getMilliseconds()
	{
		return milliseconds;
	}

	public void setMilliseconds(Long milliseconds)
	{
		this.milliseconds = milliseconds;
	}

	public Boolean getFirstAquirer()
	{
		return firstAquirer;
	}

	public void setFirstAquirer(Boolean firstAquirer)
	{
		this.firstAquirer = firstAquirer;
	}

	public Long getDeliveryCount()
	{
		return deliveryCount;
	}

	public void setDeliveryCount(Long deliveryCount)
	{
		this.deliveryCount = deliveryCount;
	}

}
