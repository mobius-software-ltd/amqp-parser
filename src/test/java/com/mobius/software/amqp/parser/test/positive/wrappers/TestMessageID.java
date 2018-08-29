package com.mobius.software.amqp.parser.test.positive.wrappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.mobius.software.amqp.parser.wrappers.BinaryID;
import com.mobius.software.amqp.parser.wrappers.LongID;
import com.mobius.software.amqp.parser.wrappers.MessageID;
import com.mobius.software.amqp.parser.wrappers.StringID;
import com.mobius.software.amqp.parser.wrappers.UuidID;

public class TestMessageID
{
	private MessageID messageId;

	@Test
	public void testBinaryId()
	{
		byte[] expected = new byte[]
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		messageId = new BinaryID(expected);
		Assert.assertArrayEquals("Invalid BinaryID value: ", expected, messageId.getBinary());
		assertNull("Invalid BinaryID string value", messageId.getString());
		assertNull("Invalid BinaryID uuid value", messageId.getUuid());
		assertNull("Invalid BinaryID long value", messageId.getLong());
	}

	@Test
	public void testStringId()
	{
		String value = "test id";
		messageId = new StringID(value);
		assertEquals("Invalid StringID value: ", value, messageId.getString());
		assertNull("Invalid BinaryID binary value", messageId.getBinary());
		assertNull("Invalid BinaryID uuid value", messageId.getUuid());
		assertNull("Invalid BinaryID long value", messageId.getLong());
	}

	@Test
	public void testUuidId()
	{
		UUID value = UUID.fromString("1eeeded2-ef6a-4104-89e3-2873ac090f0e");
		messageId = new UuidID(value);
		assertEquals("Invalid UuidID value: ", value, messageId.getUuid());
		assertNull("Invalid UuidID binary value", messageId.getBinary());
		assertNull("Invalid UuidID string value", messageId.getString());
		assertNull("Invalid UuidID long value", messageId.getLong());
	}

	@Test
	public void testLongId()
	{
		BigInteger value = BigInteger.valueOf(9999999999L);
		messageId = new LongID(value);
		assertEquals("Invalid LongID value: ", value, messageId.getLong());
		assertNull("Invalid LongID binary value", messageId.getBinary());
		assertNull("Invalid LongID string value", messageId.getString());
		assertNull("Invalid LongID uuid value", messageId.getUuid());
	}
}
