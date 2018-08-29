package com.mobius.software.amqp.parser.tlv.impl;

import java.nio.ByteBuffer;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.SimpleConstructor;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TLVVariable extends TLVAmqp
{
	private byte[] value;
	private int width;

	public TLVVariable(AMQPType code, byte[] value)
	{
		super(new SimpleConstructor(code));
		this.value = value;
		width = value.length > 255 ? 4 : 1;
	}

	@Override
	public byte[] getBytes()
	{
		byte[] constructorBytes = constructor.getBytes();
		byte[] widthBytes = new byte[width];
		if (width == 1)
			widthBytes[0] = (byte) value.length;
		else if (width == 4)
			ByteBuffer.wrap(widthBytes).putInt(value.length);
		byte[] bytes = new byte[constructorBytes.length + width + value.length];
		System.arraycopy(constructorBytes, 0, bytes, 0, constructorBytes.length);
		System.arraycopy(widthBytes, 0, bytes, constructorBytes.length, width);
		if (value.length > 0)
			System.arraycopy(value, 0, bytes, constructorBytes.length + width, value.length);
		return bytes;
	}

	@Override
	public int getLength()
	{
		return value.length + constructor.getLength() + width;
	}

	@Override
	public byte[] getValue()
	{
		return value;
	}

}
