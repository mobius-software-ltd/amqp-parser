package com.mobius.software.amqp.parser.sections;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVMap;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class MessageAnnotations implements AMQPSection
{
	private Map<Object, Object> annotations;

	public MessageAnnotations()
	{
	}

	private MessageAnnotations(Map<Object, Object> annotations)
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

		if (annotations != null)
			map = AMQPWrapper.wrapMap(annotations);

		DescribedConstructor constructor = new DescribedConstructor(map.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x72 }));
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
		return SectionCode.MESSAGE_ANNOTATIONS;
	}

	public static class Builder
	{
		private Map<Object, Object> annotations = new LinkedHashMap<>();

		public MessageAnnotations build()
		{
			return new MessageAnnotations(annotations);
		}

		public Builder addAnnotation(String key, Object value)
		{
			annotations.put(new AMQPSymbol(key), value);
			return this;
		}

		public Builder addAnnotation(BigInteger key, Object value)
		{
			annotations.put(key, value);
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "MessageAnnotations [annotations=" + annotations + "]";
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
		MessageAnnotations other = (MessageAnnotations) obj;
		if (annotations == null)
		{
			if (other.annotations != null)
				return false;
		}
		else if (!annotations.equals(other.annotations))
			return false;
		return true;
	}

	public Map<Object, Object> getAnnotations()
	{
		return annotations;
	}
}
