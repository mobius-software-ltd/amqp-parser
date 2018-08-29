package com.mobius.software.amqp.parser.terminus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.TerminusDurability;
import com.mobius.software.amqp.parser.avps.TerminusExpiryPolicy;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.header.api.Parsable;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class AMQPTarget implements Parsable
{
	private String address;
	private TerminusDurability durable;
	private TerminusExpiryPolicy expiryPeriod;
	private Long timeout;
	private Boolean dynamic;
	private Map<AMQPSymbol, Object> dynamicNodeProperties;
	private List<AMQPSymbol> capabilities;

	public AMQPTarget()
	{
	}

	private AMQPTarget(String address, TerminusDurability durable, TerminusExpiryPolicy expiryPeriod, Long timeout, Boolean dynamic, Map<AMQPSymbol, Object> dynamicNodeProperties, List<AMQPSymbol> capabilities)
	{
		this.address = address;
		this.durable = durable;
		this.expiryPeriod = expiryPeriod;
		this.timeout = timeout;
		this.dynamic = dynamic;
		this.dynamicNodeProperties = dynamicNodeProperties;
		this.capabilities = capabilities;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (address != null)
			list.addElement(0, AMQPWrapper.wrap(address));
		if (durable != null)
			list.addElement(1, AMQPWrapper.wrap(durable.getCode()));
		if (expiryPeriod != null)
			list.addElement(2, AMQPWrapper.wrap(new AMQPSymbol(expiryPeriod.getPolicy())));
		if (timeout != null)
			list.addElement(3, AMQPWrapper.wrap(timeout));
		if (dynamic != null)
			list.addElement(4, AMQPWrapper.wrap(dynamic));

		if (!MapUtils.isEmpty(dynamicNodeProperties))
			if (dynamic != null)
			{
				if (dynamic)
					list.addElement(5, AMQPWrapper.wrapMap(dynamicNodeProperties));
				else
					throw new MalformedHeaderException("Target's dynamic-node-properties can't be specified when dynamic flag is false");
			}
			else
				throw new MalformedHeaderException("Target's dynamic-node-properties can't be specified when dynamic flag is not set");

		if (!CollectionUtils.isEmpty(capabilities))
			list.addElement(6, AMQPWrapper.wrapArray(capabilities));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x29 }));
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
				address = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				durable = TerminusDurability.valueOf(AMQPUnwrapper.unwrapUInt(element));
		}
		if (list.getList().size() > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				expiryPeriod = TerminusExpiryPolicy.getPolicy(AMQPUnwrapper.unwrapSymbol(element).getValue());
		}
		if (list.getList().size() > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (!element.isNull())
				timeout = AMQPUnwrapper.unwrapUInt(element);
		}
		if (list.getList().size() > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				dynamic = AMQPUnwrapper.unwrapBool(element);
		}
		if (list.getList().size() > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
			{
				if (dynamic != null)
				{
					if (dynamic)
						dynamicNodeProperties = AMQPUnwrapper.unwrapMap(element);
					else
						throw new MalformedHeaderException("Received malformed Target: dynamic-node-properties can't be specified when dynamic flag is false");
				}
				else
					throw new MalformedHeaderException("Received malformed Target: dynamic-node-properties can't be specified when dynamic flag is not set");
			}
		}
		if (list.getList().size() > 6)
		{
			TLVAmqp element = list.getList().get(6);
			if (!element.isNull())
				capabilities = AMQPUnwrapper.unwrapArray(element);
		}
	}

	public static class Builder
	{
		private String address;
		private TerminusDurability durable;
		private TerminusExpiryPolicy expiryPeriod;
		private Long timeout;
		private Boolean dynamic;
		private Map<AMQPSymbol, Object> dynamicNodeProperties = new LinkedHashMap<>();
		private List<AMQPSymbol> capabilities = new ArrayList<>();

		public AMQPTarget build()
		{
			return new AMQPTarget(address, durable, expiryPeriod, timeout, dynamic, dynamicNodeProperties, capabilities);
		}

		public Builder address(String address)
		{
			this.address = address;
			return this;
		}

		public Builder durable(TerminusDurability durable)
		{
			this.durable = durable;
			return this;
		}

		public Builder expiryPeriod(TerminusExpiryPolicy expiryPeriod)
		{
			this.expiryPeriod = expiryPeriod;
			return this;
		}

		public Builder timeout(Long timeout)
		{
			this.timeout = timeout;
			return this;
		}

		public Builder dynamic(boolean dynamic)
		{
			this.dynamic = dynamic;
			return this;
		}

		public Builder addDynamicNodeProperty(String name, Object value)
		{
			this.dynamicNodeProperties.put(new AMQPSymbol(name), value);
			return dynamic(true);
		}

		public Builder addCapability(String capability)
		{
			this.capabilities.add(new AMQPSymbol(capability));
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPTarget [address=" + address + ", durable=" + durable + ", expiryPeriod=" + expiryPeriod + ", timeout=" + timeout + ", dynamic=" + dynamic + ", dynamicNodeProperties=" + dynamicNodeProperties + ", capabilities=" + capabilities + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((capabilities == null) ? 0 : capabilities.hashCode());
		result = prime * result + ((durable == null) ? 0 : durable.hashCode());
		result = prime * result + ((dynamic == null) ? 0 : dynamic.hashCode());
		result = prime * result + ((dynamicNodeProperties == null) ? 0 : dynamicNodeProperties.hashCode());
		result = prime * result + ((expiryPeriod == null) ? 0 : expiryPeriod.hashCode());
		result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
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
		AMQPTarget other = (AMQPTarget) obj;
		if (address == null)
		{
			if (other.address != null)
				return false;
		}
		else if (!address.equals(other.address))
			return false;
		if (capabilities == null)
		{
			if (other.capabilities != null)
				return false;
		}
		else if (!capabilities.equals(other.capabilities))
			return false;
		if (durable != other.durable)
			return false;
		if (dynamic == null)
		{
			if (other.dynamic != null)
				return false;
		}
		else if (!dynamic.equals(other.dynamic))
			return false;
		if (dynamicNodeProperties == null)
		{
			if (other.dynamicNodeProperties != null)
				return false;
		}
		else if (!dynamicNodeProperties.equals(other.dynamicNodeProperties))
			return false;
		if (expiryPeriod != other.expiryPeriod)
			return false;
		if (timeout == null)
		{
			if (other.timeout != null)
				return false;
		}
		else if (!timeout.equals(other.timeout))
			return false;
		return true;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public TerminusDurability getDurable()
	{
		return durable;
	}

	public void setDurable(TerminusDurability durability)
	{
		this.durable = durability;
	}

	public TerminusExpiryPolicy getExpiryPeriod()
	{
		return expiryPeriod;
	}

	public void setExpiryPeriod(TerminusExpiryPolicy expiryPeriod)
	{
		this.expiryPeriod = expiryPeriod;
	}

	public Long getTimeout()
	{
		return timeout;
	}

	public void setTimeout(Long timeout)
	{
		this.timeout = timeout;
	}

	public Boolean getDynamic()
	{
		return dynamic;
	}

	public void setDynamic(Boolean dynamic)
	{
		this.dynamic = dynamic;
	}

	public Map<AMQPSymbol, Object> getDynamicNodeProperties()
	{
		return dynamicNodeProperties;
	}

	public List<AMQPSymbol> getCapabilities()
	{
		return capabilities;
	}

	public void setDynamicNodeProperties(Map<AMQPSymbol, Object> dynamicNodeProperties)
	{
		this.dynamicNodeProperties = dynamicNodeProperties;
	}

	public void setCapabilities(List<AMQPSymbol> capabilities)
	{
		this.capabilities = capabilities;
	}
}
