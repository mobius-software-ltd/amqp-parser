package com.mobius.software.amqp.parser.wrappers;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;

public class BinaryID implements MessageID
{
	private byte[] id;

	public BinaryID(byte[] id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "BinaryID [id=" + Arrays.toString(id) + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(id);
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
		BinaryID other = (BinaryID) obj;
		if (!Arrays.equals(id, other.id))
			return false;
		return true;
	}

	@Override
	public String getString()
	{
		return null;
	}

	@Override
	public byte[] getBinary()
	{
		return id;
	}

	@Override
	public BigInteger getLong()
	{
		return null;
	}

	@Override
	public UUID getUuid()
	{
		return null;
	}

}
