package com.mobius.software.amqp.parser.wrappers;

import java.math.BigInteger;
import java.util.UUID;

public class LongID implements MessageID
{
	private BigInteger id;

	public LongID(BigInteger id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "LongID [id=" + id + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LongID other = (LongID) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
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
		return null;
	}

	@Override
	public BigInteger getLong()
	{
		return id;
	}

	@Override
	public UUID getUuid()
	{
		return null;
	}

}
