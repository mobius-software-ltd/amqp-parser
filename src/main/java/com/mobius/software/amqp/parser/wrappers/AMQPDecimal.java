package com.mobius.software.amqp.parser.wrappers;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class AMQPDecimal
{
	private final byte[] value;

	public AMQPDecimal(byte[] value)
	{
		this.value = value;
	}

	public AMQPDecimal(byte b)
	{
		this.value = ByteBuffer.allocate(1).put(b).array();
	}

	public AMQPDecimal(short s)
	{
		this.value = ByteBuffer.allocate(2).putShort(s).array();
	}

	public AMQPDecimal(int i)
	{
		this.value = ByteBuffer.allocate(4).putInt(i).array();
	}

	public AMQPDecimal(long l)
	{
		this.value = ByteBuffer.allocate(8).putLong(l).array();
	}

	public AMQPDecimal(float f)
	{
		this.value = ByteBuffer.allocate(4).putFloat(f).array();
	}

	public AMQPDecimal(double d)
	{
		this.value = ByteBuffer.allocate(8).putDouble(d).array();
	}

	@Override
	public String toString()
	{
		return "AMQPDecimal [value=" + Arrays.toString(value) + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(value);
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
		AMQPDecimal other = (AMQPDecimal) obj;
		if (!Arrays.equals(value, other.value))
			return false;
		return true;
	}

	public double getDouble()
	{
		return ByteBuffer.wrap(value).getDouble();
	}

	public long getLong()
	{
		return ByteBuffer.wrap(value).getLong();
	}

	public int getInt()
	{
		return ByteBuffer.wrap(value).getInt();
	}

	public float getFloat()
	{
		return ByteBuffer.wrap(value).getFloat();
	}

	public short getShort()
	{
		return ByteBuffer.wrap(value).getShort();
	}

	public byte getByte()
	{
		return ByteBuffer.wrap(value).get();
	}

	public byte[] getValue()
	{
		return value;
	}

}
