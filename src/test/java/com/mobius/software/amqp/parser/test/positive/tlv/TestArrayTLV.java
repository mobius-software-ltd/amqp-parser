package com.mobius.software.amqp.parser.test.positive.tlv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.impl.TLVArray;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestArrayTLV
{
	@Test
	public void testArray_8()
	{
		byte[] expectedArray = new byte[]
		{ (byte) 0xe0, 0x1a, 3, (byte) 0xa3, 7, 0x65, 0x6c, 0x65, 0x6d, 0x65, 0x6e, 0x74, 7, 0x65, 0x6c, 0x65, 0x6d, 0x65, 0x6e, 0x74, 7, 0x65, 0x6c, 0x65, 0x6d, 0x65, 0x6e, 0x74 };
		List<AMQPSymbol> value = new ArrayList<>();
		value.add(new AMQPSymbol("element"));
		value.add(new AMQPSymbol("element"));
		value.add(new AMQPSymbol("element"));
		TLVArray array = AMQPWrapper.wrapArray(value);
		List<AMQPSymbol> arrayValue = AMQPUnwrapper.unwrapArray(array);
		for (AMQPSymbol s : arrayValue)
			assertEquals("Invalid array_8 value: ", new AMQPSymbol("element"), s);
		Assert.assertArrayEquals("Invalid array_8 bytes: ", expectedArray, array.getBytes());
		assertEquals("Invalid array_8 length: ", 28, array.getLength());
	}

	@Test
	public void testArray_32()
	{
		int pos = 0;
		byte[] expectedArray = new byte[266];
		expectedArray[pos++] = (byte) 0xf0;
		pos += 2;
		expectedArray[pos++] = 1;
		expectedArray[pos++] = 5;
		pos += 3;
		expectedArray[pos++] = 32;
		expectedArray[pos++] = (byte) 0xa3;
		List<AMQPSymbol> value = new ArrayList<>();
		String val = "element";
		for (int i = 0; i < 32; i++)
		{
			expectedArray[pos++] = 7;
			System.arraycopy(val.getBytes(), 0, expectedArray, pos, val.length());
			pos += val.length();
			value.add(new AMQPSymbol(val));
		}
		TLVArray array = AMQPWrapper.wrapArray(value);
		List<AMQPSymbol> arrayValue = AMQPUnwrapper.unwrapArray(array);
		for (int i = 0; i < arrayValue.size(); i++)
			assertEquals("Invalid array_32 value: ", new AMQPSymbol("element"), arrayValue.get(i));
		assertTrue("Invalid array_32 bytes: ", Arrays.equals(expectedArray, array.getBytes()));
		assertEquals("Invalid array_32 length: ", 266, array.getLength());
	}

}
