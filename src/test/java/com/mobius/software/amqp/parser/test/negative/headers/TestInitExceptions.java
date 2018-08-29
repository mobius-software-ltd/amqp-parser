package com.mobius.software.amqp.parser.test.negative.headers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.impl.SASLInit;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;
import com.mobius.software.amqp.parser.tlv.impl.TLVVariable;

public class TestInitExceptions
{
	private SASLInit header = new SASLInit();

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void testGetMechanismNull()
	{
		expectedEx.expect(MalformedHeaderException.class);
		header.toArgumentsList();
	}

	@Test
	public void testFillMechanismNull()
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
		list.addElement(0, new TLVVariable(AMQPType.SYMBOL_8, new byte[5]));
		list.addElement(4, new TLVNull());
		header.fromArgumentsList(list);
	}

}
