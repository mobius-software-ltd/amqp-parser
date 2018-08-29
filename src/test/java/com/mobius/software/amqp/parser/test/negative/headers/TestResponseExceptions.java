package com.mobius.software.amqp.parser.test.negative.headers;

import javax.security.sasl.SaslException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.SASLResponse;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;
import com.mobius.software.amqp.parser.tlv.impl.TLVVariable;

public class TestResponseExceptions
{
	private SASLResponse header = new SASLResponse();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testGetResponseNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillResponseNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(1, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testSetResponseNameNull()
	{
		expectedEx.expect(IllegalArgumentException.class);
		try
		{
			header.setCramMD5Response(new byte[20], null);
		}
		catch (SaslException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testSetResponseChallengeNull()
	{
		expectedEx.expect(IllegalArgumentException.class);
		try
		{
			header.setCramMD5Response(null, "username");
		}
		catch (SaslException e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidArguments()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVVariable(AMQPType.BINARY_8, new byte[5]));
		list.addElement(1, new TLVNull());
		header.fromArgumentsList(list);
	}

}
