package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.TerminusExpiryPolicy;

public class TestTerminusExpiryPolicyCodes
{
	@Test
	public void testLinkDetach()
	{
		assertEquals(String.format("Code %s not accepted", "link-detach"), TerminusExpiryPolicy.LINK_DETACH, TerminusExpiryPolicy.getPolicy("link-detach"));
	}

	@Test
	public void testSessionEnd()
	{
		assertEquals(String.format("Code %s not accepted", "session-end"), TerminusExpiryPolicy.SESSION_END, TerminusExpiryPolicy.getPolicy("session-end"));
	}

	@Test
	public void testConnectionClose()
	{
		assertEquals(String.format("Code %s not accepted", "connection-close"), TerminusExpiryPolicy.CONNETION_CLOSE, TerminusExpiryPolicy.getPolicy("connection-close"));
	}

	@Test
	public void testNever()
	{
		assertEquals(String.format("Code %s not accepted", "never"), TerminusExpiryPolicy.NEVER, TerminusExpiryPolicy.getPolicy("never"));
	}

}
