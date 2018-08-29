package com.mobius.software.amqp.parser.test.positive.codes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mobius.software.amqp.parser.avps.ErrorCode;

public class TestErrorCodes
{
	@Test
	public void testInternalError()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:internal-error"), ErrorCode.INTERNAL_ERROR, ErrorCode.getCondition("amqp:internal-error"));
	}

	@Test
	public void testNotFound()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:not-found"), ErrorCode.NOT_FOUND, ErrorCode.getCondition("amqp:not-found"));
	}

	@Test
	public void testUnauthorizedAccess()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:unauthorized-access"), ErrorCode.UNAUTHORIZED_ACCESS, ErrorCode.getCondition("amqp:unauthorized-access"));
	}

	@Test
	public void testDecodeError()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:decode-error"), ErrorCode.DECODE_ERROR, ErrorCode.getCondition("amqp:decode-error"));
	}

	@Test
	public void testResourceLimitExceeded()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:internal-error"), ErrorCode.RESOURCE_LIMIT_EXCEEDED, ErrorCode.getCondition("amqp:resource-limit-exceeded"));
	}

	@Test
	public void testNotAllowed()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:not-allowed"), ErrorCode.NOT_ALLOWED, ErrorCode.getCondition("amqp:not-allowed"));
	}

	@Test
	public void testInvalidField()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:invalid-field"), ErrorCode.INVALID_FIELD, ErrorCode.getCondition("amqp:invalid-field"));
	}

	@Test
	public void testNotImplemented()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:not-implemented"), ErrorCode.NOT_IMPLEMENTED, ErrorCode.getCondition("amqp:not-implemented"));
	}

	@Test
	public void testResourceLocked()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:resource-locked"), ErrorCode.RESOURCE_LOCKED, ErrorCode.getCondition("amqp:resource-locked"));
	}

	@Test
	public void testPreconditionFailed()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:precondition-failed"), ErrorCode.PRECONDITION_FAILED, ErrorCode.getCondition("amqp:precondition-failed"));
	}

	@Test
	public void testResourceDeleted()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:resource-deleted"), ErrorCode.RESOURCE_DELETED, ErrorCode.getCondition("amqp:resource-deleted"));
	}

	@Test
	public void testIllegalState()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:illegal-state"), ErrorCode.ILLEGAL_STATE, ErrorCode.getCondition("amqp:illegal-state"));
	}

	@Test
	public void testFrameSizeTooSmall()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:frame-size-too-small"), ErrorCode.FRAME_SIZE_TOO_SMALL, ErrorCode.getCondition("amqp:frame-size-too-small"));
	}

	@Test
	public void testConnectionForced()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:connection-forced"), ErrorCode.CONNECTION_FORCED, ErrorCode.getCondition("amqp:connection-forced"));
	}

	@Test
	public void testFramingError()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:framing-error"), ErrorCode.FRAMING_ERROR, ErrorCode.getCondition("amqp:framing-error"));
	}

	@Test
	public void testRedirected()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:redirected"), ErrorCode.REDIRECTED, ErrorCode.getCondition("amqp:redirected"));
	}

	@Test
	public void testErrantLink()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:errant-link"), ErrorCode.ERRANT_LINK, ErrorCode.getCondition("amqp:errant-link"));
	}

	@Test
	public void testHandleInUse()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:handle-in-use"), ErrorCode.HANDLE_IN_USE, ErrorCode.getCondition("amqp:handle-in-use"));
	}

	@Test
	public void testUnattachedHandle()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:unattached-handle"), ErrorCode.UNATTACHED_HANDLE, ErrorCode.getCondition("amqp:unattached-handle"));
	}

	@Test
	public void testDetachForced()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:detach-forced"), ErrorCode.DETACH_FORCED, ErrorCode.getCondition("amqp:detach-forced"));
	}

	@Test
	public void testTransferLimitExceeded()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:transfer-limit-exceeded"), ErrorCode.TRANSFER_LIMIT_EXCEEDED, ErrorCode.getCondition("amqp:transfer-limit-exceeded"));
	}

	@Test
	public void testMessageSizeExceeded()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:message-size-exceeded"), ErrorCode.MESSAGE_SIZE_EXCEEDED, ErrorCode.getCondition("amqp:message-size-exceeded"));
	}

	@Test
	public void testRedirect()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:redirect"), ErrorCode.REDIRECT, ErrorCode.getCondition("amqp:redirect"));
	}

	@Test
	public void testStolen()
	{
		assertEquals(String.format("Code %s not accepted", "amqp:stolen"), ErrorCode.STOLEN, ErrorCode.getCondition("amqp:stolen"));
	}
}
