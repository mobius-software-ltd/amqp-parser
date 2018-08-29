package com.mobius.software.amqp.parser.test.negative.codes;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobius.software.amqp.parser.avps.*;
import com.mobius.software.amqp.parser.exception.InvalidCodeException;

@SuppressWarnings("unused")
public class TestNullCode
{
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp()
	{
		expectedEx.expect(InvalidCodeException.class);
	}

	@After
	public void tearDown()
	{
		expectedEx = null;
	}

	@Test
	public void testAMQPType()
	{
		AMQPType type = AMQPType.valueOf(0x00);
	}

	@Test
	public void testDistributionMode()
	{
		DistributionMode mode = DistributionMode.getMode("");
	}

	@Test
	public void testErrorCodes()
	{
		ErrorCode code = ErrorCode.getCondition("");
	}

	@Test
	public void testHeaderCodes()
	{
		HeaderCode code = HeaderCode.valueOf(0x00);
	}

	@Test
	public void testLifetimePolicy()
	{
		LifetimePolicy policy = LifetimePolicy.valueOf(0x00);
	}

	@Test
	public void testOutcomeCodes()
	{
		OutcomeCode code = OutcomeCode.valueOf((short) 5);
	}

	@Test
	public void testReceiveCodes()
	{
		ReceiveCode code = ReceiveCode.valueOf((short) 2);
	}

	@Test
	public void testSectionCodes()
	{
		SectionCode code = SectionCode.valueOf(0x00);
	}

	@Test
	public void testSendCodes()
	{
		SendCode code = SendCode.valueOf((short) 3);
	}

	@Test
	public void testStateCodes()
	{
		StateCode code = StateCode.valueOf(0x00);
	}

	@Test
	public void testTerminusDurability()
	{
		TerminusDurability durability = TerminusDurability.valueOf(3);
	}

	@Test
	public void testTerminuxExpiryPolicy()
	{
		TerminusExpiryPolicy policy = TerminusExpiryPolicy.getPolicy("");
	}
}
