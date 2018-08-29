package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.AMQPProtoHeader;

public class TestProtoheader
{
	private AMQPProtoHeader header = new AMQPProtoHeader(0);

	@Test
	public void testGetBytes()
	{
		byte[] expectedArray = new byte[]
		{ 0x41, 0x4d, 0x51, 0x50, 0x00, 0x01, 0x00, 0x00 };
		assertTrue("Invalid Proto-header bytes: ", Arrays.equals(expectedArray, header.getBytes()));
	}

	@Test
	public void testProtocol()
	{
		assertEquals("Invalid Proto-header protocol: ", "AMQP", header.getProtocol());
	}

	@Test
	public void testProtocolId()
	{
		assertEquals("Invalid Proto-header protocolId: ", 0, header.getProtocolId());
	}

	@Test
	public void testVersionMajor()
	{
		assertEquals("Invalid Proto-header versionMajor: ", 1, header.getVersionMajor());
	}

	@Test
	public void testVersionMinor()
	{
		assertEquals("Invalid Proto-header versionMinor: ", 0, header.getVersionMinor());
	}

	@Test
	public void testVersionRevision()
	{
		assertEquals("Invalid Proto-header versionRevision: ", 0, header.getVersionRevision());
	}

	@Test
	public void testConstants()
	{
		assertNull("Invalid Proto-header arguments", header.toArgumentsList());
		assertEquals("Invalid Proto-header code", HeaderCode.PROTO, header.getCode());
		assertEquals("Invalid Proto-header doff: ", 2, header.getDoff());
		assertEquals("Invalid Proto-header type: ", 0, header.getType());
		assertEquals("Invalid Proto-header channel: ", 0, header.getChannel());
	}

}
