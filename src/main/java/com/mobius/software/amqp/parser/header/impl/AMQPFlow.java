package com.mobius.software.amqp.parser.header.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

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

public class AMQPFlow extends AMQPHeader
{
	private Long nextIncomingId;
	private Long incomingWindow;
	private Long nextOutgoingId;
	private Long outgoingWindow;
	private Long handle;
	private Long deliveryCount;
	private Long linkCredit;
	private Long avaliable;
	private Boolean drain;
	private Boolean echo;
	private Map<AMQPSymbol, Object> properties;

	public AMQPFlow()
	{
		super(HeaderCode.FLOW, 2, 0, 0);
	}

	private AMQPFlow(HeaderCode code, int doff, int type, int channel, Long nextIncomingId, Long incomingWindow, Long nextOutgoingId, Long outgoingWindow, Long handle, Long deliveryCount, Long linkCredit, Long avaliable, Boolean drain, Boolean echo, Map<AMQPSymbol, Object> properties)
	{
		super(code, doff, type, channel);
		this.nextIncomingId = nextIncomingId;
		this.incomingWindow = incomingWindow;
		this.nextOutgoingId = nextOutgoingId;
		this.outgoingWindow = outgoingWindow;
		this.handle = handle;
		this.deliveryCount = deliveryCount;
		this.linkCredit = linkCredit;
		this.avaliable = avaliable;
		this.drain = drain;
		this.echo = echo;
		this.properties = properties;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (nextIncomingId != null)
			list.addElement(0, AMQPWrapper.wrap(nextIncomingId));

		if (incomingWindow == null)
			throw new MalformedHeaderException("Flow header's incoming-window can't be null");
		list.addElement(1, AMQPWrapper.wrap(incomingWindow));

		if (nextOutgoingId == null)
			throw new MalformedHeaderException("Flow header's next-outgoing-id can't be null");
		list.addElement(2, AMQPWrapper.wrap(nextOutgoingId));

		if (outgoingWindow == null)
			throw new MalformedHeaderException("Flow header's outgoing-window can't be null");
		list.addElement(3, AMQPWrapper.wrap(outgoingWindow));

		if (handle != null)
			list.addElement(4, AMQPWrapper.wrap(handle));

		if (deliveryCount != null)
			if (handle != null)
				list.addElement(5, AMQPWrapper.wrap(deliveryCount));
			else
				throw new MalformedHeaderException("Flow headers delivery-count can't be assigned when handle is not specified");

		if (linkCredit != null)
			if (handle != null)
				list.addElement(6, AMQPWrapper.wrap(linkCredit));
			else
				throw new MalformedHeaderException("Flow headers link-credit can't be assigned when handle is not specified");

		if (avaliable != null)
			if (handle != null)
				list.addElement(7, AMQPWrapper.wrap(avaliable));
			else
				throw new MalformedHeaderException("Flow headers avaliable can't be assigned when handle is not specified");

		if (drain != null)
			if (handle != null)
				list.addElement(8, AMQPWrapper.wrap(drain));
			else
				throw new MalformedHeaderException("Flow headers drain can't be assigned when handle is not specified");

		if (echo != null)
			list.addElement(9, AMQPWrapper.wrap(echo));
		if (!MapUtils.isEmpty(properties))
			list.addElement(10, AMQPWrapper.wrapMap(properties));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size < 4)
			throw new MalformedHeaderException("Received malformed Flow header: mandatory " + "fields incoming-window, next-outgoing-id and " + "outgoing-window must not be null");

		if (size > 11)
			throw new MalformedHeaderException("Received malformed Flow header. Invalid arguments size: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (!element.isNull())
				nextIncomingId = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Flow header: incoming-window can't be null");
			incomingWindow = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Flow header: next-outgoing-id can't be null");
			nextOutgoingId = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Flow header: outgoing-window can't be null");
			outgoingWindow = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				handle = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
				if (handle != null)
					deliveryCount = AMQPUnwrapper.unwrapUInt(element);
				else
					throw new MalformedHeaderException("Received malformed Flow header: delivery-count can't be present when handle is null");
		}
		if (size > 6)
		{
			TLVAmqp element = list.getList().get(6);
			if (!element.isNull())
				if (handle != null)
					linkCredit = AMQPUnwrapper.unwrapUInt(element);
				else
					throw new MalformedHeaderException("Received malformed Flow header: link-credit can't be present when handle is null");

		}
		if (size > 7)
		{
			TLVAmqp element = list.getList().get(7);
			if (!element.isNull())
				if (handle != null)
					avaliable = AMQPUnwrapper.unwrapUInt(element);
				else
					throw new MalformedHeaderException("Received malformed Flow header: avaliable can't be present when handle is null");
		}
		if (size > 8)
		{
			TLVAmqp element = list.getList().get(8);
			if (!element.isNull())
				if (handle != null)
					drain = AMQPUnwrapper.unwrapBool(element);
				else
					throw new MalformedHeaderException("Received malformed Flow header: drain can't be present when handle is null");

		}
		if (size > 9)
		{
			TLVAmqp element = list.getList().get(9);
			if (!element.isNull())
				echo = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 10)
		{
			TLVAmqp element = list.getList().get(10);
			if (!element.isNull())
				properties = AMQPUnwrapper.unwrapMap(element);
		}

	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private Long nextIncomingId;
		private Long incomingWindow;
		private Long nextOutgoingId;
		private Long outgoingWindow;
		private Long handle;
		private Long deliveryCount;
		private Long linkCredit;
		private Long avaliable;
		private Boolean drain;
		private Boolean echo;
		private Map<AMQPSymbol, Object> properties = new LinkedHashMap<>();

		public AMQPFlow build()
		{
			return new AMQPFlow(HeaderCode.FLOW, doff, type, channel, nextIncomingId, incomingWindow, nextOutgoingId, outgoingWindow, handle, deliveryCount, linkCredit, avaliable, drain, echo, properties);
		}

		public Builder nextIncomingId(Long nextIncomingId)
		{
			this.nextIncomingId = nextIncomingId;
			return this;
		}

		public Builder incomingWindow(Long incomingWindow)
		{
			this.incomingWindow = incomingWindow;
			return this;
		}

		public Builder nextOutgoingId(Long nextOutgoingId)
		{
			this.nextOutgoingId = nextOutgoingId;
			return this;
		}

		public Builder outgoingWindow(Long outgoingWindow)
		{
			this.outgoingWindow = outgoingWindow;
			return this;
		}

		public Builder handle(Long handle)
		{
			this.handle = handle;
			return this;
		}

		public Builder deliveryCount(Long deliveryCount)
		{
			this.deliveryCount = deliveryCount;
			return this;
		}

		public Builder linkCredit(Long linkCredit)
		{
			this.linkCredit = linkCredit;
			return this;
		}

		public Builder avaliable(Long avaliable)
		{
			this.avaliable = avaliable;
			return this;
		}

		public Builder echo(boolean echo)
		{
			this.echo = echo;
			return this;
		}

		public Builder drain(boolean drain)
		{
			this.drain = drain;
			return this;
		}

		public Builder addProperty(String name, Object value)
		{
			this.properties.put(new AMQPSymbol(name), value);
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPFlow [nextIncomingId=" + nextIncomingId + ", incomingWindow=" + incomingWindow + ", nextOutgoingId=" + nextOutgoingId + ", outgoingWindow=" + outgoingWindow + ", handle=" + handle + ", deliveryCount=" + deliveryCount + ", linkCredit=" + linkCredit + ", avaliable=" + avaliable + ", drain=" + drain + ", echo=" + echo + ", properties=" + properties + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((avaliable == null) ? 0 : avaliable.hashCode());
		result = prime * result + ((deliveryCount == null) ? 0 : deliveryCount.hashCode());
		result = prime * result + ((drain == null) ? 0 : drain.hashCode());
		result = prime * result + ((echo == null) ? 0 : echo.hashCode());
		result = prime * result + ((handle == null) ? 0 : handle.hashCode());
		result = prime * result + ((incomingWindow == null) ? 0 : incomingWindow.hashCode());
		result = prime * result + ((linkCredit == null) ? 0 : linkCredit.hashCode());
		result = prime * result + ((nextIncomingId == null) ? 0 : nextIncomingId.hashCode());
		result = prime * result + ((nextOutgoingId == null) ? 0 : nextOutgoingId.hashCode());
		result = prime * result + ((outgoingWindow == null) ? 0 : outgoingWindow.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		AMQPFlow other = (AMQPFlow) obj;
		if (avaliable == null)
		{
			if (other.avaliable != null)
				return false;
		}
		else if (!avaliable.equals(other.avaliable))
			return false;
		if (deliveryCount == null)
		{
			if (other.deliveryCount != null)
				return false;
		}
		else if (!deliveryCount.equals(other.deliveryCount))
			return false;
		if (drain == null)
		{
			if (other.drain != null)
				return false;
		}
		else if (!drain.equals(other.drain))
			return false;
		if (echo == null)
		{
			if (other.echo != null)
				return false;
		}
		else if (!echo.equals(other.echo))
			return false;
		if (handle == null)
		{
			if (other.handle != null)
				return false;
		}
		else if (!handle.equals(other.handle))
			return false;
		if (incomingWindow == null)
		{
			if (other.incomingWindow != null)
				return false;
		}
		else if (!incomingWindow.equals(other.incomingWindow))
			return false;
		if (linkCredit == null)
		{
			if (other.linkCredit != null)
				return false;
		}
		else if (!linkCredit.equals(other.linkCredit))
			return false;
		if (nextIncomingId == null)
		{
			if (other.nextIncomingId != null)
				return false;
		}
		else if (!nextIncomingId.equals(other.nextIncomingId))
			return false;
		if (nextOutgoingId == null)
		{
			if (other.nextOutgoingId != null)
				return false;
		}
		else if (!nextOutgoingId.equals(other.nextOutgoingId))
			return false;
		if (outgoingWindow == null)
		{
			if (other.outgoingWindow != null)
				return false;
		}
		else if (!outgoingWindow.equals(other.outgoingWindow))
			return false;
		if (properties == null)
		{
			if (other.properties != null)
				return false;
		}
		else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	public Long getNextIncomingId()
	{
		return nextIncomingId;
	}

	public void setNextIncomingId(Long nextIncomingId)
	{
		this.nextIncomingId = nextIncomingId;
	}

	public Long getIncomingWindow()
	{
		return incomingWindow;
	}

	public void setIncomingWindow(Long incomingWindow)
	{
		this.incomingWindow = incomingWindow;
	}

	public Long getNextOutgoingId()
	{
		return nextOutgoingId;
	}

	public void setNextOutgoingId(Long nextOutgoingId)
	{
		this.nextOutgoingId = nextOutgoingId;
	}

	public Long getOutgoingWindow()
	{
		return outgoingWindow;
	}

	public void setOutgoingWindow(Long outgoingWindow)
	{
		this.outgoingWindow = outgoingWindow;
	}

	public Long getHandle()
	{
		return handle;
	}

	public void setHandle(Long handle)
	{
		this.handle = handle;
	}

	public Long getDeliveryCount()
	{
		return deliveryCount;
	}

	public void setDeliveryCount(Long deliveryCount)
	{
		this.deliveryCount = deliveryCount;
	}

	public Long getLinkCredit()
	{
		return linkCredit;
	}

	public void setLinkCredit(Long linkCredit)
	{
		this.linkCredit = linkCredit;
	}

	public Long getAvaliable()
	{
		return avaliable;
	}

	public void setAvaliable(Long avaliable)
	{
		this.avaliable = avaliable;
	}

	public Boolean getDrain()
	{
		return drain;
	}

	public void setDrain(Boolean drain)
	{
		this.drain = drain;
	}

	public Boolean getEcho()
	{
		return echo;
	}

	public void setEcho(Boolean echo)
	{
		this.echo = echo;
	}

	public Map<AMQPSymbol, Object> getProperties()
	{
		return properties;
	}

	public void setProperties(Map<AMQPSymbol, Object> properties)
	{
		this.properties = properties;
	}
}
