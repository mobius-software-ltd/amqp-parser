package com.mobius.software.amqp.parser.test.positive.tlv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVMap;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestCompoundTLV
{
	@Test
	public void testList0()
	{
		TLVList list = AMQPWrapper.wrapList(new ArrayList<Object>());
		assertEquals("Invalid list_0 value: ", new ArrayList<TLVAmqp>(), list.getList());
		assertTrue("Invalid list_0 bytes: ", Arrays.equals(new byte[]
		{ 0x45 }, list.getBytes()));
		assertEquals("Invalid list_0 length: ", 1, list.getLength());
	}

	@Test
	public void testList8()
	{
		byte[] expectedArray = new byte[]
		{ (byte) 0xc0, 0x13, 3, (byte) 0xa1, 4, 0x73, 0x74, 0x72, 0x31, (byte) 0xa1, 4, 0x73, 0x74, 0x72, 0x32, (byte) 0xa1, 4, 0x73, 0x74, 0x72, 0x33 };
		List<String> value = new ArrayList<String>();
		value.add("str1");
		value.add("str2");
		value.add("str3");
		TLVList list = AMQPWrapper.wrapList(value);
		List<Object> listValue = AMQPUnwrapper.unwrapList(list);
		for (int i = 0; i < value.size(); i++)
			assertEquals("Invalid list_8 value: ", value.get(i), listValue.get(i));
		assertTrue("Invalid list_8 bytes: ", Arrays.equals(expectedArray, list.getBytes()));
		assertEquals("Invalid list_8 length: ", 21, list.getLength());
	}

	@Test
	public void testList32()
	{
		byte[] expectedArray = new byte[369];
		expectedArray[0] = (byte) 0xd0;
		expectedArray[3] = 0x01;
		expectedArray[4] = 0x6c;
		expectedArray[8] = 0x14;
		List<String> value = new ArrayList<String>();
		int pos = 9;
		String strValue = "x symbols string";
		for (int i = 0; i < 20; i++)
		{
			expectedArray[pos++] = (byte) 0xa1;
			expectedArray[pos++] = 0x10;
			System.arraycopy(strValue.getBytes(), 0, expectedArray, pos, strValue.length());
			pos += strValue.length();
			value.add(strValue);
		}
		TLVList list = AMQPWrapper.wrapList(value);
		List<Object> listValue = AMQPUnwrapper.unwrapList(list);
		for (int i = 0; i < value.size(); i++)
			assertEquals("Invalid list_32 value: ", value.get(i), listValue.get(i));
		assertTrue("Invalid list_32 bytes: ", Arrays.equals(expectedArray, list.getBytes()));
		assertEquals("Invalid list_32 length: ", 369, list.getLength());
	}

	@Test
	public void testMap8()
	{
		byte[] expectedArray = new byte[]
		{ (byte) 0xc1, 0x28, 6, (byte) 0xa3, 4, 0x6b, 0x65, 0x79, 0x31, (byte) 0xa1, 5, 0x76, 0x61, 0x6c, 0x75, 0x65, (byte) 0xa3, 4, 0x6b, 0x65, 0x79, 0x32, (byte) 0xa1, 5, 0x76, 0x61, 0x6c, 0x75, 0x65, (byte) 0xa3, 4, 0x6b, 0x65, 0x79, 0x33, (byte) 0xa1, 5, 0x76, 0x61, 0x6c, 0x75, 0x65 };
		Map<AMQPSymbol, Object> value = new LinkedHashMap<>();
		String val = "value";
		value.put(new AMQPSymbol("key1"), val);
		value.put(new AMQPSymbol("key2"), val);
		value.put(new AMQPSymbol("key3"), val);
		TLVMap map = AMQPWrapper.wrapMap(value);
		Map<AMQPSymbol, Object> mapValue = AMQPUnwrapper.unwrapMap(map);
		int pos = 1;
		for (Map.Entry<AMQPSymbol, Object> entry : mapValue.entrySet())
		{
			assertEquals("Invalid map_8 'key' value: ", new AMQPSymbol("key" + pos++), entry.getKey());
			assertEquals("Invalid map_8 'value' value: ", val, entry.getValue());
		}
		assertTrue("Invalid map_8 bytes: ", Arrays.equals(expectedArray, map.getBytes()));
		assertEquals("Invalid map_8 length: ", 42, map.getLength());
	}

	@Test
	public void testMap32()
	{
		int pos = 0;
		byte[] expectedArray = new byte[270];
		expectedArray[pos++] = (byte) 0xd1;
		pos += 2;
		expectedArray[pos++] = 1;
		expectedArray[pos++] = 9;
		pos += 3;
		expectedArray[pos++] = 0x12;
		Map<AMQPSymbol, Object> value = new LinkedHashMap<>();
		String key,
				val = "hello world map value";
		for (int i = 1; i < 10; i++)
		{
			key = "key" + i;
			expectedArray[pos++] = (byte) 0xa3;
			expectedArray[pos++] = 4;
			System.arraycopy(key.getBytes(), 0, expectedArray, pos, key.length());
			pos += key.length();
			expectedArray[pos++] = (byte) 0xa1;
			expectedArray[pos++] = 0x15;
			System.arraycopy(val.getBytes(), 0, expectedArray, pos, val.length());
			pos += val.length();
			value.put(new AMQPSymbol(key), val);
		}
		TLVMap map = AMQPWrapper.wrapMap(value);
		Map<AMQPSymbol, Object> mapValue = AMQPUnwrapper.unwrapMap(map);
		pos = 1;
		for (Map.Entry<AMQPSymbol, Object> entry : mapValue.entrySet())
		{
			assertEquals("Invalid map_32 'key' value: ", new AMQPSymbol("key" + pos++), entry.getKey());
			assertEquals("Invalid map_32 'value' value: ", val, entry.getValue());
		}
		assertTrue("Invalid map_32 bytes: ", Arrays.equals(expectedArray, map.getBytes()));
		assertEquals("Invalid map_32 length: ", 270, map.getLength());
	}
}
