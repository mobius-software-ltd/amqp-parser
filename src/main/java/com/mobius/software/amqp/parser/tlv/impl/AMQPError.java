package com.mobius.software.amqp.parser.tlv.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.ErrorCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.header.api.Parsable;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class AMQPError implements Parsable
{
	private ErrorCode condition;
	private String description;
	private Map<AMQPSymbol, Object> info;

	private AMQPError(ErrorCode condition, String description, Map<AMQPSymbol, Object> info)
	{
		this.condition = condition;
		this.description = description;
		this.info = info;
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();

		if (condition != null)
			list.addElement(0, AMQPWrapper.wrap(new AMQPSymbol(condition.getCondition())));

		if (description != null)
			list.addElement(1, AMQPWrapper.wrap(description));

		if (!MapUtils.isEmpty(info))
			list.addElement(2, AMQPWrapper.wrapMap(info));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x1D }));

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
				condition = ErrorCode.getCondition(AMQPUnwrapper.unwrapSymbol(element).getValue());
		}

		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				description = AMQPUnwrapper.unwrapString(element);
		}

		if (list.getList().size() > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				info = AMQPUnwrapper.unwrapMap(element);
		}
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private ErrorCode condition;
		private String description;
		private Map<AMQPSymbol, Object> info = new LinkedHashMap<>();

		public AMQPError build()
		{
			return new AMQPError(condition, description, info);
		}

		public Builder condition(ErrorCode condition)
		{
			this.condition = condition;
			return this;
		}

		public Builder description(String description)
		{
			this.description = description;
			return this;
		}

		public Builder addInfo(String name, Object value)
		{
			this.info.put(new AMQPSymbol(name), value);
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPError [condition=" + condition + ", description=" + description + ", info=" + info + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
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
		AMQPError other = (AMQPError) obj;
		if (condition != other.condition)
			return false;
		if (description == null)
		{
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (info == null)
		{
			if (other.info != null)
				return false;
		}
		else if (!info.equals(other.info))
			return false;
		return true;
	}

	public ErrorCode getCondition()
	{
		return condition;
	}

	public void setCondition(ErrorCode condition)
	{
		this.condition = condition;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Map<AMQPSymbol, Object> getInfo()
	{
		return info;
	}

	public void setInfo(Map<AMQPSymbol, Object> info)
	{
		this.info = info;
	}

}
