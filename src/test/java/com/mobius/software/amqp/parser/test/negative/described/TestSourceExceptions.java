package com.mobius.software.amqp.parser.test.negative.described;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.terminus.AMQPSource;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestSourceExceptions
{
	private AMQPSource source;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		source = AMQPSource.builder().build();
	}

	@After
	public void tearDown()
	{
		source = null;
	}

	@Test
	public void testGetDynamicProperties()
	{
		expectedEx.expect(MalformedHeaderException.class);
		source.getDynamicNodeProperties().put(new AMQPSymbol("key"), "value");
		source.toArgumentsList();
	}

	@Test
	public void testFillDynamicProperties()
	{
		expectedEx.expect(MalformedHeaderException.class);
		source.setDynamic(true);
		source.getDynamicNodeProperties().put(new AMQPSymbol("key"), "value");
		TLVList list = source.toArgumentsList();
		list.setElement(4, new TLVNull());
		source = AMQPSource.builder().build();
		source.fromArgumentsList(list);
	}

	@Test
	public void testInvalidDefaultOutcome()
	{
		expectedEx.expect(MalformedHeaderException.class);
		TLVList list = new TLVList();
		list.addElement(8, new TLVFixed(AMQPType.UINT, new byte[]
		{ 0 }));
		source.fromArgumentsList(list);
	}
}
