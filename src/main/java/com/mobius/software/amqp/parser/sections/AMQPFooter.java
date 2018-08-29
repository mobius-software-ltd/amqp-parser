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
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class AMQPFooter implements AMQPSection
{
	private Map<AMQPSymbol, Object> annotations;

	public AMQPFooter()
	{
	}

	private AMQPFooter(Map<AMQPSymbol, Object> annotations)
	{
		this.annotations = annotations;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVAmqp getValue()
	{

		TLVMap map = new TLVMap();

		if (!MapUtils.isEmpty(annotations))
			map = AMQPWrapper.wrapMap(annotations);

		DescribedConstructor constructor = new DescribedConstructor(map.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x78 }));
		map.setConstructor(constructor);

		return map;
	}

	@Override
	public void fill(TLVAmqp map)
	{
		if (!map.isNull())
			annotations = AMQPUnwrapper.unwrapMap(map);
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.FOOTER;
	}

	public static class Builder
	{
		private Map<AMQPSymbol, Object> annotations = new LinkedHashMap<>();

		public AMQPFooter build()
		{
			return new AMQPFooter(annotations);
		}

		public Builder addAnnotation(String name, Object value)
		{
			annotations.put(new AMQPSymbol(name), value);
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPFooter [annotations=" + annotations + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annotations == null) ? 0 : annotations.hashCode());
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
		AMQPFooter other = (AMQPFooter) obj;
		if (annotations == null)
		{
			if (other.annotations != null)
				return false;
		}
		else if (!annotations.equals(other.annotations))
			return false;
		return true;
	}

	public Map<AMQPSymbol, Object> getAnnotations()
	{
		return annotations;
	}
}
