package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.SASLOutcome;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;

public class TestOutcomeExceptions
{
	private SASLOutcome header = SASLOutcome.builder().build();

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
		list.addElement(1, new TLVNull());
		header.fromArgumentsList(list);
	}

	@Test
	public void testInvalidArguments()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(0, new TLVFixed(AMQPType.UBYTE, new byte[]
		{ 0 }));
		list.addElement(2, new TLVNull());
		header.fromArgumentsList(list);
	}

}
