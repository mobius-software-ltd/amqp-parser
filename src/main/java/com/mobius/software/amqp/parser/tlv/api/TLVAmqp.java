package com.mobius.software.amqp.parser.tlv.api;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.SimpleConstructor;

public abstract class TLVAmqp
{
	protected SimpleConstructor constructor;

	public TLVAmqp(SimpleConstructor constructor)
	{
		this.constructor = constructor;
	}

	public SimpleConstructor getConstructor()
	{
		return constructor;
	}

	public AMQPType getCode()
	{
		return constructor.getCode();
	}

	public void setConstructor(SimpleConstructor constructor)
	{
		this.constructor = constructor;
	}

	public void setCode(AMQPType code)
	{
		constructor.setCode(code);
	}

	public abstract byte[] getBytes();

	public abstract int getLength();

	public boolean isNull()
	{
		return constructor.getCode().equals(AMQPType.NULL);
	}

	public abstract byte[] getValue();
}
