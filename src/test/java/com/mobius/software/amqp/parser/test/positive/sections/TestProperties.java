package com.mobius.software.amqp.parser.test.positive.sections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.sections.AMQPProperties;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public class TestProperties
{
	private AMQPProperties section;
	private Date date = new Date();

	@Before
	public void setUp()
	{
		section = AMQPProperties.builder()//
				.absoluteExpiryTime(date)//
				.contentEncoding("UTF-8")//
				.contentType("string")//
				.correlationId(new byte[]
				{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })//
				.creationTime(date)//
				.groupId("foo group")//
				.groupSequence(1000L)//
				.withMessageID("some id")//
				.replyTo("remotehost")//
				.replyToGroupId("bar group")//
				.subject("some subject")//
				.to("bar")//
				.userId(new byte[]
				{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 })//
				.build();
		TLVAmqp value = section.getValue();
		section.fill(value);
	}

	@After
	public void tearDown()
	{
		section = null;
		date = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x73, (byte) 0xc0 };
		Assert.assertArrayEquals("Invalid Properties value constructor bytes", expectedArray, section.getValue().getConstructor().getBytes());
	}

	@Test
	public void testAbsoluteExpiryTime()
	{
		assertEquals("Invalid Properties absoluteExpiryTime: ", date, section.getAbsoluteExpiryTime());
	}

	@Test
	public void testContentEncoding()
	{
		assertEquals("Invalid Properties contencEncoding: ", "UTF-8", section.getContentEncoding());
	}

	@Test
	public void testContentType()
	{
		assertEquals("Invalid Properties contentType: ", "string", section.getContentType());
	}

	@Test
	public void testCorrelationId()
	{
		Assert.assertArrayEquals("Invalid Properties correlationId: ", new byte[]
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }, section.getCorrelationId());
	}

	@Test
	public void testExpiryTime()
	{
		assertEquals("Invalid Properties expiryTime: ", date, section.getCreationTime());
	}

	@Test
	public void testGroupId()
	{
		assertEquals("Invalid Properties groupId: ", "foo group", section.getGroupId());
	}

	@Test
	public void testGroupSequence()
	{
		assertEquals("Invalid Properties groupSequence: ", Long.valueOf(1000), section.getGroupSequence());
	}

	@Test
	public void testMessageId()
	{
		assertEquals("Invalid Properties messageId value: ", "some id", section.getMessageId().getString());
		assertNull("Invalud Properties messageId binary value: ", section.getMessageId().getBinary());
		assertNull("Invalud Properties messageId uuid value: ", section.getMessageId().getUuid());
		assertNull("Invalud Properties messageId long value: ", section.getMessageId().getLong());
	}

	@Test
	public void testReplyTo()
	{
		assertEquals("Invalid Properties replyTo", "remotehost", section.getReplyTo());
	}

	@Test
	public void testReplyToGroupId()
	{
		assertEquals("Invalid Properties replyToGroupId", "bar group", section.getReplyToGroupId());
	}

	@Test
	public void testSubject()
	{
		assertEquals("Invalid Properties subject", "some subject", section.getSubject());
	}

	@Test
	public void testTo()
	{
		assertEquals("Invalid Properties to", "bar", section.getTo());
	}

	@Test
	public void testUserId()
	{
		Assert.assertArrayEquals("Invalid Properties userId", new byte[]
		{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 }, section.getUserId());
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Properties code", SectionCode.PROPERTIES, section.getCode());
	}

}
