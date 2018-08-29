package com.mobius.software.amqp.parser.tlv.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class AMQPModified implements AMQPOutcome, AMQPState
{
	private Boolean deliveryFailed;
	private Boolean undeliverableHere;
	private Map<AMQPSymbol, Object> messageAnnotations;

	public AMQPModified()
	{
	}

	private AMQPModified(Boolean deliveryFailed, Boolean undeliverableHere, Map<AMQPSymbol, Object> messageAnnotations)
	{
		this.deliveryFailed = deliveryFailed;
		this.undeliverableHere = undeliverableHere;
		this.messageAnnotations = messageAnnotations;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (deliveryFailed != null)
			list.addElement(0, AMQPWrapper.wrap(deliveryFailed));
		if (undeliverableHere != null)
			list.addElement(1, AMQPWrapper.wrap(undeliverableHere));
		if (!MapUtils.isEmpty(messageAnnotations))
			list.addElement(2, AMQPWrapper.wrapMap(messageAnnotations));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x27 }));
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
				deliveryFailed = AMQPUnwrapper.unwrapBool(element);
		}
		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				undeliverableHere = AMQPUnwrapper.unwrapBool(element);
		}
		if (list.getList().size() > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				messageAnnotations = AMQPUnwrapper.unwrapMap(element);
		}
	}

	@Override
	public String toString()
	{
		return "AMQPModified [deliveryFailed=" + deliveryFailed + ", undeliverableHere=" + undeliverableHere + ", messageAnnotations=" + messageAnnotations + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deliveryFailed == null) ? 0 : deliveryFailed.hashCode());
		result = prime * result + ((messageAnnotations == null) ? 0 : messageAnnotations.hashCode());
		result = prime * result + ((undeliverableHere == null) ? 0 : undeliverableHere.hashCode());
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
		AMQPModified other = (AMQPModified) obj;
		if (deliveryFailed == null)
		{
			if (other.deliveryFailed != null)
				return false;
		}
		else if (!deliveryFailed.equals(other.deliveryFailed))
			return false;
		if (messageAnnotations == null)
		{
			if (other.messageAnnotations != null)
				return false;
		}
		else if (!messageAnnotations.equals(other.messageAnnotations))
			return false;
		if (undeliverableHere == null)
		{
			if (other.undeliverableHere != null)
				return false;
		}
		else if (!undeliverableHere.equals(other.undeliverableHere))
			return false;
		return true;
	}

	public static class Builder
	{
		private Boolean deliveryFailed = false;
		private Boolean undeliverableHere;
		private Map<AMQPSymbol, Object> messageAnnotations = new LinkedHashMap<>();

		public AMQPModified build()
		{
			return new AMQPModified(deliveryFailed, undeliverableHere, messageAnnotations);
		}

		public Builder deliveryFailed(boolean deliveryFailed)
		{
			this.deliveryFailed = deliveryFailed;
			return this;
		}

		public Builder undeliverableHere(boolean undeliverableHere)
		{
			this.undeliverableHere = undeliverableHere;
			return this;
		}

		public Builder addMessageAnnotation(String name, Object value)
		{
			messageAnnotations.put(new AMQPSymbol(name), value);
			return this;
		}
	}

	public Boolean getDeliveryFailed()
	{
		return deliveryFailed;
	}

	public void setDeliveryFailed(Boolean deliveryFailed)
	{
		this.deliveryFailed = deliveryFailed;
	}

	public Boolean getUndeliverableHere()
	{
		return undeliverableHere;
	}

	public void setUndeliverableHere(Boolean undeliverableHere)
	{
		this.undeliverableHere = undeliverableHere;
	}

	public Map<AMQPSymbol, Object> getMessageAnnotations()
	{
		return messageAnnotations;
	}

	public void setMessageAnnotations(Map<AMQPSymbol, Object> messageAnnotations)
	{
		this.messageAnnotations = messageAnnotations;
	}
}
