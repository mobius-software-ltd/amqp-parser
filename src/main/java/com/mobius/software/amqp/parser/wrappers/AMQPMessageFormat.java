package com.mobius.software.amqp.parser.wrappers;

import java.nio.ByteBuffer;

public class AMQPMessageFormat
{
	private final int messageFormat;
	private final int version;

	public AMQPMessageFormat(long value)
	{
		byte[] arr = ByteBuffer.allocate(4).putInt((int) value).array();
		byte[] mf = new byte[4];
		System.arraycopy(arr, 0, mf, 1, 3);
		messageFormat = ByteBuffer.wrap(mf).getInt();
		version = arr[3] & 0xff;
	}

	public AMQPMessageFormat(int messageFormat, int version)
	{
		this.messageFormat = messageFormat;
		this.version = version;
	}

	@Override
	public String toString()
	{
		return "AMQPMessageFormat [messageFormat=" + messageFormat + ", version=" + version + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + messageFormat;
		result = prime * result + version;
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
		AMQPMessageFormat other = (AMQPMessageFormat) obj;
		if (messageFormat != other.messageFormat)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public int getMessageFormat()
	{
		return messageFormat;
	}

	public int getVersion()
	{
		return version;
	}

	public Long encode()
	{
		byte[] arr = new byte[4];
		byte[] mf = ByteBuffer.allocate(4).putInt(messageFormat).array();
		System.arraycopy(mf, 1, arr, 0, 3);
		arr[3] = (byte) version;
		return (long) ByteBuffer.wrap(arr).getInt();
	}

}
