package com.mobius.software.amqp.parser.test.negative.described;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.terminus.AMQPTarget;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.tlv.impl.TLVNull;
import com.mobius.software.amqp.parser.wrappers.AMQPSymbol;

public class TestTargetExceptions
{
	private AMQPTarget target;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		target = AMQPTarget.builder().build();
	}

	@After
	public void tearDown()
	{
		target = null;
	}

	@Test
	public void testGetDynamicProperties()
	{
		expectedEx.expect(MalformedHeaderException.class);
		target.getDynamicNodeProperties().put(new AMQPSymbol("key"), "value");
		target.toArgumentsList();
	}

	@Test
	public void testFillDynamicProperties()
	{
		expectedEx.expect(MalformedHeaderException.class);
		target.setDynamic(true);
		target.getDynamicNodeProperties().put(new AMQPSymbol("key"), "value");
		TLVList list = target.toArgumentsList();
		list.setElement(4, new TLVNull());
		target = AMQPTarget.builder().build();
		target.fromArgumentsList(list);
	}
}
