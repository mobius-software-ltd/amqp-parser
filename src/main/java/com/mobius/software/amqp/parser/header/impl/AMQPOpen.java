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

public class AMQPOpen extends AMQPHeader
{
	private String containerId;
	private String hostname;
	private Long maxFrameSize;
	private Integer channelMax;
	private Long idleTimeout;
	private List<AMQPSymbol> outgoingLocales;
	private List<AMQPSymbol> incomingLocales;
	private List<AMQPSymbol> offeredCapabilities;
	private List<AMQPSymbol> desiredCapabilities;
	private Map<AMQPSymbol, Object> properties;

	private AMQPOpen(HeaderCode code, int doff, int type, int channel, String containerId, String hostname, Long maxFrameSize, Integer channelMax, Long idleTimeout, List<AMQPSymbol> outgoingLocales, List<AMQPSymbol> incomingLocales, List<AMQPSymbol> offeredCapabilities, List<AMQPSymbol> desiredCapabilities, Map<AMQPSymbol, Object> properties)
	{
		super(code, doff, type, channel);
		this.containerId = containerId;
		this.hostname = hostname;
		this.maxFrameSize = maxFrameSize;
		this.channelMax = channelMax;
		this.idleTimeout = idleTimeout;
		this.outgoingLocales = outgoingLocales;
		this.incomingLocales = incomingLocales;
		this.offeredCapabilities = offeredCapabilities;
		this.desiredCapabilities = desiredCapabilities;
		this.properties = properties;
		this.doff = doff;
		this.type = type;
		this.channel = channel;
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (containerId == null)
			throw new MalformedHeaderException("Open header's container id can't be null");
		list.addElement(0, AMQPWrapper.wrap(containerId));

		if (hostname != null)
			list.addElement(1, AMQPWrapper.wrap(hostname));
		if (maxFrameSize != null)
			list.addElement(2, AMQPWrapper.wrap(maxFrameSize));
		if (channelMax != null)
			list.addElement(3, AMQPWrapper.wrap(channelMax));
		if (idleTimeout != null)
			list.addElement(4, AMQPWrapper.wrap(idleTimeout));
		if (!CollectionUtils.isEmpty(outgoingLocales))
			list.addElement(5, AMQPWrapper.wrapArray(outgoingLocales));
		if (!CollectionUtils.isEmpty(incomingLocales))
			list.addElement(6, AMQPWrapper.wrapArray(incomingLocales));
		if (!CollectionUtils.isEmpty(offeredCapabilities))
			list.addElement(7, AMQPWrapper.wrapArray(offeredCapabilities));
		if (!CollectionUtils.isEmpty(desiredCapabilities))
			list.addElement(8, AMQPWrapper.wrapArray(desiredCapabilities));
		if (!MapUtils.isEmpty(properties))
			list.addElement(9, AMQPWrapper.wrapMap(properties));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{
		int size = list.getList().size();
		if (size == 0)
			throw new MalformedHeaderException("Received malformed Open header: container id can't be null");

		if (size > 10)
			throw new MalformedHeaderException("Received malformed Open header. Invalid number of arguments: " + size);

		TLVAmqp element = list.getList().get(0);
		if (element.isNull())
			throw new MalformedHeaderException("Received malformed Open header: container id can't be null");
		containerId = AMQPUnwrapper.unwrapString(element);

		if (size > 1)
		{
			element = list.getList().get(1);
			if (!element.isNull())
				hostname = AMQPUnwrapper.unwrapString(element);
		}
		if (size > 2)
		{
			element = list.getList().get(2);
			if (!element.isNull())
				maxFrameSize = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 3)
		{
			element = list.getList().get(3);
			if (!element.isNull())
				channelMax = AMQPUnwrapper.unwrapUShort(element);
		}
		if (size > 4)
		{
			element = list.getList().get(4);
			if (!element.isNull())
				idleTimeout = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 5)
		{
			element = list.getList().get(5);
			if (!element.isNull())
				outgoingLocales = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 6)
		{
			element = list.getList().get(6);
			if (!element.isNull())
				incomingLocales = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 7)
		{
			element = list.getList().get(7);
			if (!element.isNull())
				offeredCapabilities = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 8)
		{
			element = list.getList().get(8);
			if (!element.isNull())
				desiredCapabilities = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 9)
		{
			element = list.getList().get(9);
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
		private String containerId;
		private String hostname;
		private Long maxFrameSize;
		private Integer channelMax;
		private Long idleTimeout;
		private List<AMQPSymbol> outgoingLocales = new ArrayList<>();
		private List<AMQPSymbol> incomingLocales = new ArrayList<>();
		private List<AMQPSymbol> offeredCapabilities = new ArrayList<>();
		private List<AMQPSymbol> desiredCapabilities = new ArrayList<>();
		private Map<AMQPSymbol, Object> properties = new LinkedHashMap<>();

		public AMQPOpen build()
		{
			return new AMQPOpen(HeaderCode.OPEN, doff, type, channel, containerId, hostname, maxFrameSize, channelMax, idleTimeout, outgoingLocales, incomingLocales, offeredCapabilities, desiredCapabilities, properties);
		}

		public Builder containerId(String containerId)
		{
			this.containerId = containerId;
			return this;
		}

		public Builder hostname(String hostname)
		{
			this.hostname = hostname;
			return this;
		}

		public Builder maxFrameSize(long maxFrameSize)
		{
			this.maxFrameSize = maxFrameSize;
			return this;
		}

		public Builder channelMax(int channelMax)
		{
			this.channelMax = channelMax;
			return this;
		}

		public Builder idleTimeout(long idleTimeout)
		{
			this.idleTimeout = idleTimeout;
			return this;
		}

		public Builder addOutgoingLocale(String value)
		{
			outgoingLocales.add(new AMQPSymbol(value));
			return this;
		}

		public Builder addIncomingLocale(String value)
		{
			incomingLocales.add(new AMQPSymbol(value));
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
		return "AMQPOpen [containerId=" + containerId + ", hostname=" + hostname + ", maxFrameSize=" + maxFrameSize + ", channelMax=" + channelMax + ", idleTimeout=" + idleTimeout + ", outgoingLocales=" + outgoingLocales + ", incomingLocales=" + incomingLocales + ", offeredCapabilities=" + offeredCapabilities + ", desiredCapabilities=" + desiredCapabilities + ", properties=" + properties + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((channelMax == null) ? 0 : channelMax.hashCode());
		result = prime * result + ((containerId == null) ? 0 : containerId.hashCode());
		result = prime * result + ((desiredCapabilities == null) ? 0 : desiredCapabilities.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((idleTimeout == null) ? 0 : idleTimeout.hashCode());
		result = prime * result + ((incomingLocales == null) ? 0 : incomingLocales.hashCode());
		result = prime * result + ((maxFrameSize == null) ? 0 : maxFrameSize.hashCode());
		result = prime * result + ((offeredCapabilities == null) ? 0 : offeredCapabilities.hashCode());
		result = prime * result + ((outgoingLocales == null) ? 0 : outgoingLocales.hashCode());
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
		AMQPOpen other = (AMQPOpen) obj;
		if (channelMax == null)
		{
			if (other.channelMax != null)
				return false;
		}
		else if (!channelMax.equals(other.channelMax))
			return false;
		if (containerId == null)
		{
			if (other.containerId != null)
				return false;
		}
		else if (!containerId.equals(other.containerId))
			return false;
		if (desiredCapabilities == null)
		{
			if (other.desiredCapabilities != null)
				return false;
		}
		else if (!desiredCapabilities.equals(other.desiredCapabilities))
			return false;
		if (hostname == null)
		{
			if (other.hostname != null)
				return false;
		}
		else if (!hostname.equals(other.hostname))
			return false;
		if (idleTimeout == null)
		{
			if (other.idleTimeout != null)
				return false;
		}
		else if (!idleTimeout.equals(other.idleTimeout))
			return false;
		if (incomingLocales == null)
		{
			if (other.incomingLocales != null)
				return false;
		}
		else if (!incomingLocales.equals(other.incomingLocales))
			return false;
		if (maxFrameSize == null)
		{
			if (other.maxFrameSize != null)
				return false;
		}
		else if (!maxFrameSize.equals(other.maxFrameSize))
			return false;
		if (offeredCapabilities == null)
		{
			if (other.offeredCapabilities != null)
				return false;
		}
		else if (!offeredCapabilities.equals(other.offeredCapabilities))
			return false;
		if (outgoingLocales == null)
		{
			if (other.outgoingLocales != null)
				return false;
		}
		else if (!outgoingLocales.equals(other.outgoingLocales))
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

	public String getContainerId()
	{
		return containerId;
	}

	public void setContainerId(String containerId)
	{
		this.containerId = containerId;
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public Long getMaxFrameSize()
	{
		return maxFrameSize;
	}

	public void setMaxFrameSize(Long maxFrameSize)
	{
		this.maxFrameSize = maxFrameSize;
	}

	public Integer getChannelMax()
	{
		return channelMax;
	}

	public void setChannelMax(Integer channelMax)
	{
		this.channelMax = channelMax;
	}

	public Long getIdleTimeout()
	{
		return idleTimeout;
	}

	public void setIdleTimeout(Long idleTimeout)
	{
		this.idleTimeout = idleTimeout;
	}

	public List<AMQPSymbol> getOutgoingLocales()
	{
		return outgoingLocales;
	}

	public void setOutgoingLocales(List<AMQPSymbol> outgoingLocales)
	{
		this.outgoingLocales = outgoingLocales;
	}

	public List<AMQPSymbol> getIncomingLocales()
	{
		return incomingLocales;
	}

	public void setIncomingLocales(List<AMQPSymbol> incomingLocales)
	{
		this.incomingLocales = incomingLocales;
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
