package com.mobius.software.amqp.parser.header.impl;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.RoleCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.header.api.HeaderFactory;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.*;

public class AMQPDisposition extends AMQPHeader
{
	private RoleCode role;
	private Long first;
	private Long last;
	private Boolean settled;
	private AMQPState state;
	private Boolean batchable;

	public AMQPDisposition()
	{
		super(HeaderCode.DISPOSITION, 2, 0, 0);
	}

	private AMQPDisposition(HeaderCode code, int doff, int type, int channel, RoleCode role, Long first, Long last, Boolean settled, AMQPState state, Boolean batchable)
	{
		super(code, doff, type, channel);
		this.role = role;
		this.first = first;
		this.last = last;
		this.settled = settled;
		this.state = state;
		this.batchable = batchable;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (role == null)
			throw new MalformedHeaderException("Disposition header's role can't be null");
		list.addElement(0, AMQPWrapper.wrap(role.getType()));

		if (first == null)
			throw new MalformedHeaderException("Transfer header's first can't be null");
		list.addElement(1, AMQPWrapper.wrap(first));

		if (last != null)
			list.addElement(2, AMQPWrapper.wrap(last));
		if (settled != null)
			list.addElement(3, AMQPWrapper.wrap(settled));
		if (state != null)
			list.addElement(4, state.toArgumentsList());
		if (batchable != null)
			list.addElement(5, AMQPWrapper.wrap(batchable));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size < 2)
			throw new MalformedHeaderException("Received malformed Disposition header: role and first can't be null");

		if (size > 6)
			throw new MalformedHeaderException("Received malformed Disposition header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Disposition header: role can't be null");
			role = RoleCode.valueOf(AMQPUnwrapper.unwrapBool(element));
		}
		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Disposition header: first can't be null");
			first = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				last = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (!element.isNull())
				settled = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
			{
				AMQPType code = element.getCode();
				if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
					throw new MalformedHeaderException("Expected type 'STATE' - received: " + element.getCode());
				state = HeaderFactory.getState((TLVList) element);
				state.fromArgumentsList((TLVList) element);
			}
		}
		if (size > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
				batchable = AMQPUnwrapper.unwrapBool(element);
		}
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private RoleCode role;
		private Long first;
		private Long last;
		private Boolean settled;
		private AMQPState state;
		private Boolean batchable;

		public AMQPDisposition build()
		{
			return new AMQPDisposition(HeaderCode.DISPOSITION, doff, type, channel, role, first, last, settled, state, batchable);
		}

		public Builder role(RoleCode role)
		{
			this.role = role;
			return this;
		}

		public Builder first(Long first)
		{
			this.first = first;
			return this;
		}

		public Builder last(Long last)
		{
			this.last = last;
			return this;
		}

		public Builder settled(boolean settled)
		{
			this.settled = settled;
			return this;
		}

		public Builder batchable(boolean batchable)
		{
			this.batchable = batchable;
			return this;
		}

		public Builder withStateAccepted()
		{
			this.state = new AMQPAccepted();
			return this;
		}

		public Builder withStateRejected(AMQPError error)
		{
			this.state = new AMQPRejected(error);
			return this;
		}

		public Builder withStateReleased()
		{
			this.state = new AMQPReleased();
			return this;
		}

		public Builder withStateModified(AMQPModified modified)
		{
			this.state = modified;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPDisposition [role=" + role + ", first=" + first + ", last=" + last + ", settled=" + settled + ", state=" + state + ", batchable=" + batchable + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((batchable == null) ? 0 : batchable.hashCode());
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((settled == null) ? 0 : settled.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		AMQPDisposition other = (AMQPDisposition) obj;
		if (batchable == null)
		{
			if (other.batchable != null)
				return false;
		}
		else if (!batchable.equals(other.batchable))
			return false;
		if (first == null)
		{
			if (other.first != null)
				return false;
		}
		else if (!first.equals(other.first))
			return false;
		if (last == null)
		{
			if (other.last != null)
				return false;
		}
		else if (!last.equals(other.last))
			return false;
		if (role != other.role)
			return false;
		if (settled == null)
		{
			if (other.settled != null)
				return false;
		}
		else if (!settled.equals(other.settled))
			return false;
		if (state == null)
		{
			if (other.state != null)
				return false;
		}
		else if (!state.equals(other.state))
			return false;
		return true;
	}

	public RoleCode getRole()
	{
		return role;
	}

	public void setRole(RoleCode role)
	{
		this.role = role;
	}

	public Long getFirst()
	{
		return first;
	}

	public void setFirst(Long first)
	{
		this.first = first;
	}

	public Long getLast()
	{
		return last;
	}

	public void setLast(Long last)
	{
		this.last = last;
	}

	public Boolean getSettled()
	{
		return settled;
	}

	public void setSettled(Boolean settled)
	{
		this.settled = settled;
	}

	public AMQPState getState()
	{
		return state;
	}

	public void setState(AMQPState state)
	{
		this.state = state;
	}

	public Boolean getBatchable()
	{
		return batchable;
	}

	public void setBatchable(Boolean batchable)
	{
		this.batchable = batchable;
	}
}
