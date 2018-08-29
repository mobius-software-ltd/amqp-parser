package com.mobius.software.amqp.parser.header.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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

public class AMQPBegin extends AMQPHeader
{
	private Integer remoteChannel;
	private Long nextOutgoingId;
	private Long incomingWindow;
	private Long outgoingWindow;
	private Long handleMax;
	private List<AMQPSymbol> offeredCapabilities;
	private List<AMQPSymbol> desiredCapabilities;
	private Map<AMQPSymbol, Object> properties;

	protected AMQPBegin(HeaderCode code, int doff, int type, int channel, Integer remoteChannel, Long nextOutgoingId, Long incomingWindow, Long outgoingWindow, Long handleMax, List<AMQPSymbol> offeredCapabilities, List<AMQPSymbol> desiredCapabilities, Map<AMQPSymbol, Object> properties)
	{
		super(code, doff, type, channel);
		this.remoteChannel = remoteChannel;
		this.nextOutgoingId = nextOutgoingId;
		this.incomingWindow = incomingWindow;
		this.outgoingWindow = outgoingWindow;
		this.handleMax = handleMax;
		this.offeredCapabilities = offeredCapabilities;
		this.desiredCapabilities = desiredCapabilities;
		this.properties = properties;
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (remoteChannel != null)
			list.addElement(0, AMQPWrapper.wrap(remoteChannel));

		if (nextOutgoingId == null)
			throw new MalformedHeaderException("Begin header's next-outgoing-id can't be null");
		list.addElement(1, AMQPWrapper.wrap(nextOutgoingId));

		if (incomingWindow == null)
			throw new MalformedHeaderException("Begin header's incoming-window can't be null");
		list.addElement(2, AMQPWrapper.wrap(incomingWindow));

		if (outgoingWindow == null)
			throw new MalformedHeaderException("Begin header's incoming-window can't be null");
		list.addElement(3, AMQPWrapper.wrap(outgoingWindow));

		if (handleMax != null)
			list.addElement(4, AMQPWrapper.wrap(handleMax));
		if (!CollectionUtils.isEmpty(offeredCapabilities))
			list.addElement(5, AMQPWrapper.wrapArray(offeredCapabilities));
		if (!CollectionUtils.isEmpty(desiredCapabilities))
			list.addElement(6, AMQPWrapper.wrapArray(desiredCapabilities));
		if (!MapUtils.isEmpty(properties))
			list.addElement(7, AMQPWrapper.wrapMap(properties));

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
			throw new MalformedHeaderException("Received malformed Begin header: mandatory " + "fields next-outgoing-id, incoming-window and " + "outgoing-window must not be null");

		if (size > 8)
			throw new MalformedHeaderException("Received malformed Begin header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (!element.isNull())
				remoteChannel = AMQPUnwrapper.unwrapUShort(element);
		}
		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Begin header: next-outgoing-id can't be null");
			nextOutgoingId = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Begin header: incoming-window can't be null");
			incomingWindow = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Begin header: outgoing-window can't be null");
			outgoingWindow = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				handleMax = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
				offeredCapabilities = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 6)
		{
			TLVAmqp element = list.getList().get(6);
			if (!element.isNull())
				desiredCapabilities = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 7)
		{
			TLVAmqp element = list.getList().get(7);
			if (!element.isNull())
				properties = AMQPUnwrapper.unwrapMap(element);
		}
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private Integer remoteChannel;
		private Long nextOutgoingId;
		private Long incomingWindow;
		private Long outgoingWindow;
		private Long handleMax;
		private List<AMQPSymbol> offeredCapabilities = new ArrayList<>();
		private List<AMQPSymbol> desiredCapabilities = new ArrayList<>();
		private Map<AMQPSymbol, Object> properties = new LinkedHashMap<>();

		public AMQPBegin build()
		{
			return new AMQPBegin(HeaderCode.BEGIN, doff, type, channel, remoteChannel, nextOutgoingId, incomingWindow, outgoingWindow, handleMax, offeredCapabilities, desiredCapabilities, properties);
		}

		public Builder remoteChannel(int remoteChannel)
		{
			this.remoteChannel = remoteChannel;
			return this;
		}

		public Builder nextOutgoingId(long nextOutgoingId)
		{
			this.nextOutgoingId = nextOutgoingId;
			return this;
		}

		public Builder incomingWindow(long incomingWindow)
		{
			this.incomingWindow = incomingWindow;
			return this;
		}

		public Builder outgoingWindow(long outgoingWindow)
		{
			this.outgoingWindow = outgoingWindow;
			return this;
		}

		public Builder handleMax(long handleMax)
		{
			this.handleMax = handleMax;
			return this;
		}

		public Builder addOfferedCapability(String value)
		{
			offeredCapabilities.add(new AMQPSymbol(value));
			return this;
		}

		public Builder addDesiredCapability(String value)
		{
			desiredCapabilities.add(new AMQPSymbol(value));
			return this;
		}

		public Builder addProperty(String name, Object value)
		{
			properties.put(new AMQPSymbol(name), value);
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPBegin [remoteChannel=" + remoteChannel + ", nextOutgoingId=" + nextOutgoingId + ", incomingWindow=" + incomingWindow + ", outgoingWindow=" + outgoingWindow + ", handleMax=" + handleMax + ", offeredCapabilities=" + offeredCapabilities + ", desiredCapabilities=" + desiredCapabilities + ", properties=" + properties + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((desiredCapabilities == null) ? 0 : desiredCapabilities.hashCode());
		result = prime * result + ((handleMax == null) ? 0 : handleMax.hashCode());
		result = prime * result + ((incomingWindow == null) ? 0 : incomingWindow.hashCode());
		result = prime * result + ((nextOutgoingId == null) ? 0 : nextOutgoingId.hashCode());
		result = prime * result + ((offeredCapabilities == null) ? 0 : offeredCapabilities.hashCode());
		result = prime * result + ((outgoingWindow == null) ? 0 : outgoingWindow.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((remoteChannel == null) ? 0 : remoteChannel.hashCode());
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
		AMQPBegin other = (AMQPBegin) obj;
		if (desiredCapabilities == null)
		{
			if (other.desiredCapabilities != null)
				return false;
		}
		else if (!desiredCapabilities.equals(other.desiredCapabilities))
			return false;
		if (handleMax == null)
		{
			if (other.handleMax != null)
				return false;
		}
		else if (!handleMax.equals(other.handleMax))
			return false;
		if (incomingWindow == null)
		{
			if (other.incomingWindow != null)
				return false;
		}
		else if (!incomingWindow.equals(other.incomingWindow))
			return false;
		if (nextOutgoingId == null)
		{
			if (other.nextOutgoingId != null)
				return false;
		}
		else if (!nextOutgoingId.equals(other.nextOutgoingId))
			return false;
		if (offeredCapabilities == null)
		{
			if (other.offeredCapabilities != null)
				return false;
		}
		else if (!offeredCapabilities.equals(other.offeredCapabilities))
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
		if (remoteChannel == null)
		{
			if (other.remoteChannel != null)
				return false;
		}
		else if (!remoteChannel.equals(other.remoteChannel))
			return false;
		return true;
	}

	public Integer getRemoteChannel()
	{
		return remoteChannel;
	}

	public void setRemoteChannel(Integer remoteChannel)
	{
		this.remoteChannel = remoteChannel;
	}

	public Long getNextOutgoingId()
	{
		return nextOutgoingId;
	}

	public void setNextOutgoingId(Long nextOutgoingId)
	{
		this.nextOutgoingId = nextOutgoingId;
	}

	public Long getIncomingWindow()
	{
		return incomingWindow;
	}

	public void setIncomingWindow(Long incomingWindow)
	{
		this.incomingWindow = incomingWindow;
	}

	public Long getOutgoingWindow()
	{
		return outgoingWindow;
	}

	public void setOutgoingWindow(Long outgoingWindow)
	{
		this.outgoingWindow = outgoingWindow;
	}

	public Long getHandleMax()
	{
		return handleMax;
	}

	public void setHandleMax(Long handleMax)
	{
		this.handleMax = handleMax;
	}

	public List<AMQPSymbol> getOfferedCapabilities()
	{
		return offeredCapabilities;
	}

	public void setOfferedCapabilities(List<AMQPSymbol> offeredCapabilities)
	{
		this.offeredCapabilities = offeredCapabilities;
	}

	public List<AMQPSymbol> getDesiredCapabilities()
	{
		return desiredCapabilities;
	}

	public void setDesiredCapabilities(List<AMQPSymbol> desiredCapabilities)
	{
		this.desiredCapabilities = desiredCapabilities;
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
