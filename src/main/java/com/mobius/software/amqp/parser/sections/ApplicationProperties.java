package com.mobius.software.amqp.parser.sections;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVMap;

public class ApplicationProperties implements AMQPSection
{
	private Map<String, Object> properties;

	public ApplicationProperties()
	{
	}

	private ApplicationProperties(Map<String, Object> properties)
	{
		this.properties = properties;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private Map<String, Object> properties = new LinkedHashMap<>();

		public ApplicationProperties build()
		{
			return new ApplicationProperties(properties);
		}

		public Builder addProperty(String name, Object value)
		{
			properties.put(name, value);
			return this;
		}
	}

	@Override
	public TLVAmqp getValue()
	{

		TLVMap map = new TLVMap();

		if (!MapUtils.isEmpty(properties))
			map = AMQPWrapper.wrapMap(properties);

		DescribedConstructor constructor = new DescribedConstructor(map.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x74 }));
		map.setConstructor(constructor);

		return map;
	}

	@Override
	public void fill(TLVAmqp map)
	{
		if (!map.isNull())
			properties = AMQPUnwrapper.unwrapMap(map);
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.APPLICATION_PROPERTIES;
	}

	@Override
	public String toString()
	{
		return "ApplicationProperties [properties=" + properties + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		ApplicationProperties other = (ApplicationProperties) obj;
		if (properties == null)
		{
			if (other.properties != null)
				return false;
		}
		else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	public Map<String, Object> getProperties()
	{
		return properties;
	}
}
