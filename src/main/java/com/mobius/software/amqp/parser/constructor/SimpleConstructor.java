package com.mobius.software.amqp.parser.constructor;

import com.mobius.software.amqp.parser.avps.AMQPType;

public class SimpleConstructor
{
	protected AMQPType code;

	public SimpleConstructor(AMQPType code)
	{
		this.code = code;
	}

	public AMQPType getCode()
	{
		return code;
	}

	public void setCode(AMQPType code)
	{
		this.code = code;
	}

	public byte[] getBytes()
	{
		return new byte[]
		{ (byte) code.getType() };
	}

	public int getLength()
	{
		return 1;
	}

	public Byte getDescriptorCode()
	{
		return null;
	}
}
