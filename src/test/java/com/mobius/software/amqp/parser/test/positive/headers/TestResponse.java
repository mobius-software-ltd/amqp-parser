package com.mobius.software.amqp.parser.test.positive.headers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.security.sasl.SaslException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.impl.SASLResponse;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestResponse
{
	private SASLResponse header;
	private byte[] challenge = new byte[]
	{ 0x3c, 0x34, 0x36, 0x33, 0x34, 0x37, 0x32, 0x38, 0x33, 0x31, 0x31, 0x30, 0x35, 0x32, 0x30, 0x37, 0x32, 0x30, 0x2e, 0x31, 0x34, 0x35, 0x34, 0x34, 0x31, 0x32, 0x30, 0x38, 0x35, 0x33, 0x38, 0x31, 0x40, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x68, 0x6f, 0x73, 0x74, 0x3e };
	private byte[] expectedResponse = new byte[]
	{ 0x67, 0x75, 0x65, 0x73, 0x74, 0x20, 0x36, 0x63, 0x64, 0x30, 0x62, 0x34, 0x65, 0x30, 0x35, 0x64, 0x63, 0x31, 0x64, 0x31, 0x37, 0x35, 0x33, 0x33, 0x34, 0x66, 0x39, 0x32, 0x66, 0x32, 0x66, 0x64, 0x61, 0x62, 0x34, 0x31, 0x39, 0x36 };

	@Before
	public void setUp()
	{
		try
		{
			header = new SASLResponse();
			SASLResponse in = new SASLResponse();
			in.setCramMD5Response(challenge, "guest");
			TLVList list = in.toArgumentsList();
			header.fromArgumentsList(list);
		}
		catch (SaslException e)
		{
			e.printStackTrace();
		}
	}

	@After
	public void tearDown()
	{
		header = null;
		challenge = null;
		expectedResponse = null;
	}

	@Test
	public void testConstructor()
	{
		byte[] expectedArray = new byte[]
		{ 0x00, 0x53, 0x43, (byte) 0xc0 };
		assertTrue("Invalid Response arguments constructor bytes", Arrays.equals(expectedArray, header.toArgumentsList().getConstructor().getBytes()));
	}

	@Test
	public void testResponseCramMD5()
	{
		assertTrue("Invalid Response CramMD5", Arrays.equals(expectedResponse, header.getResponse()));
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid Response code: ", HeaderCode.RESPONSE, header.getCode());
	}

}
