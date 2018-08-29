package com.mobius.software.amqp.parser.header.api;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVArray;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVMap;
import com.mobius.software.amqp.parser.wrappers.AMQPDecimal;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class AMQPUnwrapper
{
	public static short unwrapUByte(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.UBYTE)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse UBYTE: received " + tlv.getCode());
		return (short) (tlv.getValue()[0] & 0xff);
	}

	public static byte unwrapByte(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.BYTE)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse BYTE: received " + tlv.getCode());
		return tlv.getValue()[0];
	}

	public static int unwrapUShort(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.USHORT)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse USHORT: received " + tlv.getCode());
		return ByteBuffer.wrap(tlv.getValue()).getShort() & 0xffff;
	}

	public static short unwrapShort(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.SHORT)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse SHORT: received " + tlv.getCode());
		return ByteBuffer.wrap(tlv.getValue()).getShort();
	}

	public static long unwrapUInt(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.UINT && code != AMQPType.SMALL_UINT && code != AMQPType.UINT_0)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse UINT: received " + code);
		byte[] value = tlv.getValue();
		if (value.length == 0)
			return 0;
		if (value.length == 1)
			return tlv.getValue()[0] & 0xff;
		return ByteBuffer.wrap(tlv.getValue()).getInt() & 0xffffffffL;
	}

	public static int unwrapInt(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.INT && code != AMQPType.SMALL_INT)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse INT: received " + code);
		byte[] value = tlv.getValue();
		if (value.length == 0)
			return 0;
		if (value.length == 1)
			return tlv.getValue()[0];
		return ByteBuffer.wrap(tlv.getValue()).getInt();
	}

	public static BigInteger unwrapULong(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.ULONG && code != AMQPType.SMALL_ULONG && code != AMQPType.ULONG_0)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse ULONG: received " + code);
		byte[] value = tlv.getValue();
		if (value.length == 0)
			return BigInteger.valueOf(0);
		if (value.length == 1)
			return BigInteger.valueOf(tlv.getValue()[0] & 0xff);
		BigInteger b = BigInteger.valueOf(ByteBuffer.wrap(tlv.getValue()).getLong());
		if (b.signum() < 0)
			b = b.add(BigInteger.ONE.shiftLeft(64));
		return b;
	}

	public static Long unwrapLong(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.LONG && code != AMQPType.SMALL_LONG)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse LONG: received " + code);
		byte[] value = tlv.getValue();
		if (value.length == 0)
			return 0L;
		if (value.length == 1)
			return (long) tlv.getValue()[0];
		return ByteBuffer.wrap(tlv.getValue()).getLong();
	}

	public static Boolean unwrapBool(TLVAmqp tlv)
	{
		switch (tlv.getCode())
		{
		case BOOLEAN:
			byte val = tlv.getValue()[0];
			if (val == 0)
				return false;
			else if (val == 1)
				return true;
			else
				throw new MalformedHeaderException("Invalid Boolean type value: " + val);
		case BOOLEAN_TRUE:
			return true;
		case BOOLEAN_FALSE:
			return false;
		default:
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse BOOLEAN: received " + tlv.getCode());
		}
	}

	public static Double unwrapDouble(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.DOUBLE)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse DOUBLE: received " + tlv.getCode());
		return ByteBuffer.wrap(tlv.getValue()).getDouble();
	}

	public static Float unwrapFloat(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.FLOAT)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse FLOAT: received " + tlv.getCode());
		return ByteBuffer.wrap(tlv.getValue()).getFloat();
	}

	public static Date unwrapTimestamp(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.TIMESTAMP)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse TIMESTAMP: received " + tlv.getCode());
		return new Date(ByteBuffer.wrap(tlv.getValue()).getLong());
	}

	public static AMQPDecimal unwrapDecimal(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.DECIMAL_32 && code != AMQPType.DECIMAL_64 && code != AMQPType.DECIMAL_128)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse DECIMAL: received " + tlv.getCode());
		return new AMQPDecimal(tlv.getValue());
	}

	public static int unwrapChar(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.CHAR)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse CHAR: received " + tlv.getCode());
		return ByteBuffer.wrap(tlv.getValue()).getInt();
	}

	public static String unwrapString(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.STRING_8 && code != AMQPType.STRING_32)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse STRING: received " + code);
		return new String(tlv.getValue());
	}

	public static AMQPSymbol unwrapSymbol(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.SYMBOL_8 && code != AMQPType.SYMBOL_32)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse SYMBOL: received " + code);
		return new AMQPSymbol(new String(tlv.getValue()));
	}

	public static byte[] unwrapBinary(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.BINARY_8 && code != AMQPType.BINARY_32)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse BINARY: received " + code);
		return tlv.getValue();
	}

	public static UUID unwrapUuid(TLVAmqp tlv)
	{
		if (tlv.getCode() != AMQPType.UUID)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse UUID: received " + tlv.getCode());
		return UUID.fromString(new String(tlv.getValue()));
	}

	public static List<Object> unwrapList(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse LIST: received " + tlv.getCode());
		List<Object> result = new ArrayList<>();
		for (TLVAmqp value : ((TLVList) tlv).getList())
			result.add(unwrap(value));
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<T, Object> unwrapMap(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.MAP_8 && code != AMQPType.MAP_32)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse MAP: received " + tlv.getCode());
		Map<T, Object> result = new LinkedHashMap<>();
		for (Map.Entry<TLVAmqp, TLVAmqp> entry : ((TLVMap) tlv).getMap().entrySet())
		{
			T key = (T) unwrap(entry.getKey());
			Object value = unwrap(entry.getValue());
			result.put(key, value);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> unwrapArray(TLVAmqp tlv)
	{
		AMQPType code = tlv.getCode();
		if (code != AMQPType.ARRAY_8 && code != AMQPType.ARRAY_32)
			throw new IllegalArgumentException(new Date() + ": " + "Error trying to parse ARRAY: received " + tlv.getCode());
		List<T> result = new ArrayList<>();
		for (TLVAmqp element : ((TLVArray) tlv).getElements())
			result.add((T) unwrap(element));
		return result;
	}

	public static Object unwrap(TLVAmqp value)
	{

		switch (value.getCode())
		{

		case NULL:
			return null;

		case ARRAY_32:
		case ARRAY_8:
			return unwrapArray(value);

		case BINARY_32:
		case BINARY_8:
			return unwrapBinary(value);

		case UBYTE:
			return unwrapUByte(value);

		case BOOLEAN:
		case BOOLEAN_FALSE:
		case BOOLEAN_TRUE:
			return unwrapBool(value);

		case BYTE:
			return unwrapByte(value);

		case CHAR:
			return unwrapChar(value);

		case DOUBLE:
			return unwrapDouble(value);

		case FLOAT:
			return unwrapFloat(value);

		case INT:
		case SMALL_INT:
			return unwrapInt(value);

		case LIST_0:
		case LIST_32:
		case LIST_8:
			return unwrapList(value);

		case LONG:
		case SMALL_LONG:
			return unwrapLong(value);

		case MAP_32:
		case MAP_8:
			return unwrapMap(value);

		case SHORT:
			return unwrapShort(value);

		case STRING_32:
		case STRING_8:
			return unwrapString(value);

		case SYMBOL_32:
		case SYMBOL_8:
			return unwrapSymbol(value);

		case TIMESTAMP:
			return new Date(unwrapLong(value));

		case UINT:
		case SMALL_UINT:
		case UINT_0:
			return unwrapUInt(value);

		case ULONG:
		case SMALL_ULONG:
		case ULONG_0:
			return unwrapULong(value);

		case USHORT:
			return unwrapUShort(value);

		case UUID:
			return unwrapUuid(value);

		case DECIMAL_128:
		case DECIMAL_32:
		case DECIMAL_64:
			return unwrapDecimal(value);

		default:
			throw new IllegalArgumentException(new Date() + ": " + "received unrecognized type");
		}
	}
}
