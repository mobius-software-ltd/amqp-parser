package com.mobius.software.amqp.parser.tlv.impl;

import java.nio.ByteBuffer;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.constructor.SimpleConstructor;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TLVFixed extends TLVAmqp
{
	private byte[] value;

	public TLVFixed(AMQPType code, byte[] value)
	{
		super(new SimpleConstructor(code));
		this.value = value;
	}

	@Override
	public byte[] getBytes()
	{
		byte[] constructorBytes = constructor.getBytes();
		byte[] bytes = new byte[constructorBytes.length + value.length];
		System.arraycopy(constructorBytes, 0, bytes, 0, constructorBytes.length);
		if (value.length > 0)
			System.arraycopy(value, 0, bytes, constructorBytes.length, value.length);
		return bytes;
	}

	@Override
	public int getLength()
	{
		return value.length + constructor.getLength();
	}

	@Override
	public byte[] getValue()
	{
		return value;
	}

	// TODO
	@Override
	public String toString()
	{
		String s = null;

		switch (constructor.getCode())
		{

		case BOOLEAN_TRUE:
			s = "1";
			break;

		case BOOLEAN_FALSE:
		case UINT_0:
		case ULONG_0:
			s = "0";
			break;

		case BOOLEAN:
		case BYTE:
		case UBYTE:
		case SMALL_INT:
		case SMALL_LONG:
		case SMALL_UINT:
		case SMALL_ULONG:
			s = Byte.toString(value[0]);
			break;

		case SHORT:
		case USHORT:
			s = Short.toString(ByteBuffer.wrap(value).getShort());
			break;

		case CHAR:
		case DECIMAL_32:
		case FLOAT:
		case INT:
		case UINT:
			s = Integer.toString(ByteBuffer.wrap(value).getInt());
			break;

		case DECIMAL_64:
		case DOUBLE:
		case LONG:
		case ULONG:
		case TIMESTAMP:
			s = Long.toString(ByteBuffer.wrap(value).getLong());
			break;

		case DECIMAL_128:
			s = "decimal-128";
			break;

		case UUID:
			s = new String(value);
			break;

		default:
			break;
		}

		return s;
	}

}
