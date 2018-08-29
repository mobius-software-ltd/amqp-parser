package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.SASLChallenge;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;
import com.mobius.software.amqp.parser.tlv.impl.TLVVariable;

public class TestChallengeExceptions
{
	private SASLChallenge header = new SASLChallenge();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testGetChallengeNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillChallengeNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		header.fromArgumentsList(list);
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
