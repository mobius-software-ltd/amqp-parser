package com.mobius.software.amqp.parser.header.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.ReceiveCode;
import com.mobius.software.amqp.parser.avps.RoleCode;
import com.mobius.software.amqp.parser.avps.SendCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.terminus.AMQPSource;
import com.mobius.software.amqp.parser.terminus.AMQPTarget;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class AMQPAttach extends AMQPHeader
{
	private String name;
	private Long handle;
	private RoleCode role;
	private SendCode sndSettleMode;
	private ReceiveCode rcvSettleMode;
	private AMQPSource source;
	private AMQPTarget target;
	private Map<AMQPSymbol, Object> unsettled;
	private Boolean incompleteUnsettled;
	private Long initialDeliveryCount;
	private BigInteger maxMessageSize;
	private List<AMQPSymbol> offeredCapabilities;
	private List<AMQPSymbol> desiredCapabilities;
	private Map<AMQPSymbol, Object> properties;

	public AMQPAttach()
	{
		super(HeaderCode.ATTACH, 2, 0, 0);
	}

	private AMQPAttach(HeaderCode code, int doff, int type, int channel, String name, Long handle, RoleCode role, SendCode sndSettleMode, ReceiveCode rcvSettleMode, AMQPSource source, AMQPTarget target, Map<AMQPSymbol, Object> unsettled, Boolean incompleteUnsettled, Long initialDeliveryCount, BigInteger maxMessageSize, List<AMQPSymbol> offeredCapabilities, List<AMQPSymbol> desiredCapabilities, Map<AMQPSymbol, Object> properties)
	{
		super(code, doff, type, channel);
		this.name = name;
		this.handle = handle;
		this.role = role;
		this.sndSettleMode = sndSettleMode;
		this.rcvSettleMode = rcvSettleMode;
		this.source = source;
		this.target = target;
		this.unsettled = unsettled;
		this.incompleteUnsettled = incompleteUnsettled;
		this.initialDeliveryCount = initialDeliveryCount;
		this.maxMessageSize = maxMessageSize;
		this.offeredCapabilities = offeredCapabilities;
		this.desiredCapabilities = desiredCapabilities;
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

		if (name == null)
			throw new MalformedHeaderException("Attach header's name can't be null");
		list.addElement(0, AMQPWrapper.wrapString(name));

		if (handle == null)
			throw new MalformedHeaderException("Attach header's handle can't be null");
		list.addElement(1, AMQPWrapper.wrap(handle));

		if (role == null)
			throw new MalformedHeaderException("Attach header's role can't be null");
		list.addElement(2, AMQPWrapper.wrap(role.getType()));

		if (sndSettleMode != null)
			list.addElement(3, AMQPWrapper.wrap(sndSettleMode.getType()));
		if (rcvSettleMode != null)
			list.addElement(4, AMQPWrapper.wrap(rcvSettleMode.getType()));
		if (source != null)
			list.addElement(5, source.toArgumentsList());
		if (target != null)
			list.addElement(6, target.toArgumentsList());
		if (!MapUtils.isEmpty(unsettled))
			list.addElement(7, AMQPWrapper.wrapMap(unsettled));
		if (incompleteUnsettled != null)
			list.addElement(8, AMQPWrapper.wrap(incompleteUnsettled));

		if (initialDeliveryCount != null)
			list.addElement(9, AMQPWrapper.wrap(initialDeliveryCount));
		else if (role.equals(RoleCode.SENDER))
			throw new MalformedHeaderException("Sender's attach header must contain a non-null initial-delivery-count value");

		if (maxMessageSize != null)
			list.addElement(10, AMQPWrapper.wrap(maxMessageSize));
		if (!CollectionUtils.isEmpty(offeredCapabilities))
			list.addElement(11, AMQPWrapper.wrapArray(offeredCapabilities));
		if (!CollectionUtils.isEmpty(desiredCapabilities))
			list.addElement(12, AMQPWrapper.wrapArray(desiredCapabilities));
		if (!MapUtils.isEmpty(properties))
			list.addElement(13, AMQPWrapper.wrapMap(properties));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));

		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{
		int size = list.getList().size();
		if (size < 3)
			throw new MalformedHeaderException("Received malformed Attach header: mandatory " + "fields name, handle and role must not be null");

		if (size > 14)
			throw new MalformedHeaderException("Received malformed Attach header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Attach header: name can't be null");
			name = AMQPUnwrapper.unwrapString(element);
		}
		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Attach header: handle can't be null");
			handle = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Attach header: role can't be null");
			role = RoleCode.valueOf(AMQPUnwrapper.unwrapBool(element));
		}
		if (size > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (!element.isNull())
				sndSettleMode = SendCode.valueOf(AMQPUnwrapper.unwrapUByte(element));
		}
		if (size > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				rcvSettleMode = ReceiveCode.valueOf(AMQPUnwrapper.unwrapUByte(element));
		}
		if (size > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
			{
				AMQPType code = element.getCode();
				if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
					throw new MalformedHeaderException("Expected type SOURCE - received: " + element.getCode());
				source = AMQPSource.builder().build();
				source.fromArgumentsList((TLVList) element);
			}
		}
		if (size > 6)
		{
			TLVAmqp element = list.getList().get(6);
			if (!element.isNull())
			{
				AMQPType code = element.getCode();
				if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
					throw new MalformedHeaderException("Expected type TARGET - received: " + element.getCode());
				target = AMQPTarget.builder().build();
				target.fromArgumentsList((TLVList) element);
			}
		}
		if (size > 7)
		{
			TLVAmqp unsettledMap = list.getList().get(7);
			if (!unsettledMap.isNull())
				unsettled = AMQPUnwrapper.unwrapMap(unsettledMap);
		}
		if (size > 8)
		{
			TLVAmqp element = list.getList().get(8);
			if (!element.isNull())
				incompleteUnsettled = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 9)
		{
			TLVAmqp element = list.getList().get(9);
			if (!element.isNull())
				initialDeliveryCount = AMQPUnwrapper.unwrapUInt(element);
			else if (role.equals(RoleCode.SENDER))
				throw new MalformedHeaderException("Received an attach header with a null initial-delivery-count");
		}
		if (size > 10)
		{
			TLVAmqp element = list.getList().get(10);
			if (!element.isNull())
				maxMessageSize = AMQPUnwrapper.unwrapULong(element);
		}
		if (size > 11)
		{
			TLVAmqp element = list.getList().get(11);
			if (!element.isNull())
				offeredCapabilities = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 12)
		{
			TLVAmqp element = list.getList().get(12);
			if (!element.isNull())
				desiredCapabilities = AMQPUnwrapper.unwrapArray(element);
		}
		if (size > 13)
		{
			TLVAmqp element = list.getList().get(13);
			if (!element.isNull())
				properties = AMQPUnwrapper.unwrapMap(element);
		}
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private String name;
		private Long handle;
		private RoleCode role;
		private SendCode sndSettleMode;
		private ReceiveCode rcvSettleMode;
		private AMQPSource source;
		private AMQPTarget target;
		private Map<AMQPSymbol, Object> unsettled = new LinkedHashMap<>();
		private Boolean incompleteUnsettled;
		private Long initialDeliveryCount;
		private BigInteger maxMessageSize;
		private List<AMQPSymbol> offeredCapabilities = new ArrayList<>();
		private List<AMQPSymbol> desiredCapabilities = new ArrayList<>();
		private Map<AMQPSymbol, Object> properties = new LinkedHashMap<>();

		public AMQPAttach build()
		{
			return new AMQPAttach(HeaderCode.ATTACH, doff, type, channel, name, handle, role, sndSettleMode, rcvSettleMode, source, target, unsettled, incompleteUnsettled, initialDeliveryCount, maxMessageSize, offeredCapabilities, desiredCapabilities, properties);
		}

		public Builder name(String name)
		{
			this.name = name;
			return this;
		}

		public Builder sndSettleMode(SendCode sndSettleMode)
		{
			this.sndSettleMode = sndSettleMode;
			return this;
		}

		public Builder rcvSettleMode(ReceiveCode rcvSettleMode)
		{
			this.rcvSettleMode = rcvSettleMode;
			return this;
		}

		public Builder handle(long handle)
		{
			this.handle = handle;
			return this;
		}

		public Builder role(RoleCode role)
		{
			this.role = role;
			return this;
		}

		public Builder source(AMQPSource source)
		{
			this.source = source;
			return this;
		}

		public Builder target(AMQPTarget target)
		{
			this.target = target;
			return this;
		}

		public Builder addUnsettled(String name, Object value)
		{
			this.unsettled.put(new AMQPSymbol(name), value);
			return this;
		}

		public Builder incompleteUnsettled(boolean incompleteUnsettled)
		{
			this.incompleteUnsettled = incompleteUnsettled;
			return this;
		}

		public Builder initialDeliveryCount(long initialDeliveryCount)
		{
			this.initialDeliveryCount = initialDeliveryCount;
			return this;
		}

		public Builder maxMessageSize(BigInteger maxMessageSize)
		{
			this.maxMessageSize = maxMessageSize;
			return this;
		}

		public Builder maxMessageSize(long maxMessageSize)
		{
			this.maxMessageSize = BigInteger.valueOf(maxMessageSize);
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
		return "AMQPAttach [name=" + name + ", handle=" + handle + ", role=" + role + ", sndSettleMode=" + sndSettleMode + ", rcvSettleMode=" + rcvSettleMode + ", source=" + source + ", target=" + target + ", unsettled=" + unsettled + ", incompleteUnsettled=" + incompleteUnsettled + ", initialDeliveryCount=" + initialDeliveryCount + ", maxMessageSize=" + maxMessageSize + ", offeredCapabilities=" + offeredCapabilities + ", desiredCapabilities=" + desiredCapabilities + ", properties=" + properties + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((desiredCapabilities == null) ? 0 : desiredCapabilities.hashCode());
		result = prime * result + ((handle == null) ? 0 : handle.hashCode());
		result = prime * result + ((incompleteUnsettled == null) ? 0 : incompleteUnsettled.hashCode());
		result = prime * result + ((initialDeliveryCount == null) ? 0 : initialDeliveryCount.hashCode());
		result = prime * result + ((maxMessageSize == null) ? 0 : maxMessageSize.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((offeredCapabilities == null) ? 0 : offeredCapabilities.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((rcvSettleMode == null) ? 0 : rcvSettleMode.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((sndSettleMode == null) ? 0 : sndSettleMode.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((unsettled == null) ? 0 : unsettled.hashCode());
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
		AMQPAttach other = (AMQPAttach) obj;
		if (desiredCapabilities == null)
		{
			if (other.desiredCapabilities != null)
				return false;
		}
		else if (!desiredCapabilities.equals(other.desiredCapabilities))
			return false;
		if (handle == null)
		{
			if (other.handle != null)
				return false;
		}
		else if (!handle.equals(other.handle))
			return false;
		if (incompleteUnsettled == null)
		{
			if (other.incompleteUnsettled != null)
				return false;
		}
		else if (!incompleteUnsettled.equals(other.incompleteUnsettled))
			return false;
		if (initialDeliveryCount == null)
		{
			if (other.initialDeliveryCount != null)
				return false;
		}
		else if (!initialDeliveryCount.equals(other.initialDeliveryCount))
			return false;
		if (maxMessageSize == null)
		{
			if (other.maxMessageSize != null)
				return false;
		}
		else if (!maxMessageSize.equals(other.maxMessageSize))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (offeredCapabilities == null)
		{
			if (other.offeredCapabilities != null)
				return false;
		}
		else if (!offeredCapabilities.equals(other.offeredCapabilities))
			return false;
		if (properties == null)
		{
			if (other.properties != null)
				return false;
		}
		else if (!properties.equals(other.properties))
			return false;
		if (rcvSettleMode != other.rcvSettleMode)
			return false;
		if (role != other.role)
			return false;
		if (sndSettleMode != other.sndSettleMode)
			return false;
		if (source == null)
		{
			if (other.source != null)
				return false;
		}
		else if (!source.equals(other.source))
			return false;
		if (target == null)
		{
			if (other.target != null)
				return false;
		}
		else if (!target.equals(other.target))
			return false;
		if (unsettled == null)
		{
			if (other.unsettled != null)
				return false;
		}
		else if (!unsettled.equals(other.unsettled))
			return false;
		return true;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getHandle()
	{
		return handle;
	}

	public void setHandle(Long handle)
	{
		this.handle = handle;
	}

	public RoleCode getRole()
	{
		return role;
	}

	public void setRole(RoleCode role)
	{
		this.role = role;
	}

	public SendCode getSndSettleMode()
	{
		return sndSettleMode;
	}

	public void setSndSettleMode(SendCode sndSettleMode)
	{
		this.sndSettleMode = sndSettleMode;
	}

	public ReceiveCode getRcvSettleMode()
	{
		return rcvSettleMode;
	}

	public void setRcvSettleMode(ReceiveCode rcvSettleMode)
	{
		this.rcvSettleMode = rcvSettleMode;
	}

	public AMQPSource getSource()
	{
		return source;
	}

	public void setSource(AMQPSource source)
	{
		this.source = source;
	}

	public AMQPTarget getTarget()
	{
		return target;
	}

	public void setTarget(AMQPTarget target)
	{
		this.target = target;
	}

	public Map<AMQPSymbol, Object> getUnsettled()
	{
		return unsettled;
	}

	public Boolean getIncompleteUnsettled()
	{
		return incompleteUnsettled;
	}

	public void setIncompleteUnsettled(Boolean incompleteUnsettled)
	{
		this.incompleteUnsettled = incompleteUnsettled;
	}

	public Long getInitialDeliveryCount()
	{
		return initialDeliveryCount;
	}

	public void setInitialDeliveryCount(Long initialDeliveryCount)
	{
		this.initialDeliveryCount = initialDeliveryCount;
	}

	public BigInteger getMaxMessageSize()
	{
		return maxMessageSize;
	}

	public void setMaxMessageSize(BigInteger maxMessageSize)
	{
		this.maxMessageSize = maxMessageSize;
	}

	public List<AMQPSymbol> getOfferedCapabilities()
	{
		return offeredCapabilities;
	}

	public List<AMQPSymbol> getDesiredCapabilities()
	{
		return desiredCapabilities;
	}

	public Map<AMQPSymbol, Object> getProperties()
	{
		return properties;
	}

	public void setUnsettled(Map<AMQPSymbol, Object> unsettled)
	{
		this.unsettled = unsettled;
	}

	public void setOfferedCapabilities(List<AMQPSymbol> offeredCapabilities)
	{
		this.offeredCapabilities = offeredCapabilities;
	}

	public void setDesiredCapabilities(List<AMQPSymbol> desiredCapabilities)
	{
		this.desiredCapabilities = desiredCapabilities;
	}

	public void setProperties(Map<AMQPSymbol, Object> properties)
	{
		this.properties = properties;
	}
}
