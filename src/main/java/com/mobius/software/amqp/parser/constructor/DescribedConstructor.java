package com.mobius.software.amqp.parser.constructor;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class DescribedConstructor extends SimpleConstructor
{
	private TLVAmqp descriptor;

	public DescribedConstructor(AMQPType code, TLVAmqp descriptor)
	{
		super(code);
		this.descriptor = descriptor;
	}

	public TLVAmqp getDescriptor()
	{
		return descriptor;
	}

	@Override
	public byte[] getBytes()
	{
		byte[] descriptorBytes = descriptor.getBytes();
		byte[] bytes = new byte[descriptorBytes.length + 2];
		bytes[0] = 0;
		System.arraycopy(descriptorBytes, 0, bytes, 1, descriptorBytes.length);
		bytes[bytes.length - 1] = (byte) code.getType();
		return bytes;
	}

	@Override
	public int getLength()
	{
		return descriptor.getLength() + 2;
	}

	public Byte getDescriptorCode()
	{
		return descriptor.getBytes()[1];
	}
}
