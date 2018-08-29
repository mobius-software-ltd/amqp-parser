package com.mobius.software.amqp.parser.test.positive.described;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.LifetimePolicy;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.tlv.impl.AMQPLifetimePolicy;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class TestLifeTimePolicy
{
	private AMQPLifetimePolicy policy;

	@Before
	public void setUp()
	{
		policy = new AMQPLifetimePolicy(LifetimePolicy.DELETE_ON_CLOSE);
	}

	@After
	public void tearDown()
	{
		policy = null;
	}

	@Test
	public void testCode()
	{
		assertEquals("Invalid LifetimePolicy code: ", LifetimePolicy.DELETE_ON_CLOSE, policy.getCode());
	}

	@Test
	public void testGetList()
	{
		byte[] expected = new byte[]
		{ 0x00, 0x53, 0x2b, 0x45 };
		byte[] actual = policy.getList().getBytes();
		Assert.assertArrayEquals("Invalid LifetimePolicy list bytes: ", expected, actual);
	}

	@Test
	public void testFill()
	{
		TLVList list = new TLVList();
		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x2c }));
		list.setConstructor(constructor);
		policy.fill(list);
		assertEquals("Invalid LifetimePolicy fill method result,", LifetimePolicy.DELETE_ON_NO_LINKS, policy.getCode());
	}
}
