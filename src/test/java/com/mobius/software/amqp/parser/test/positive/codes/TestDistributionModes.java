package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.DistributionMode;

public class TestDistributionModes
{
	@Test
	public void testMove()
	{
		assertEquals(String.format("Code %s was not accepted:", "move"), DistributionMode.MOVE, DistributionMode.getMode("move"));
	}

	@Test
	public void testCopy()
	{
		assertEquals(String.format("Code %s was not accepted:", "copy"), DistributionMode.COPY, DistributionMode.getMode("copy"));
	}

}
