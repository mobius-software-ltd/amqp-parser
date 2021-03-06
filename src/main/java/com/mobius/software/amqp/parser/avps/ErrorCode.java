package com.mobius.software.amqp.parser.avps;

import java.util.HashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.exception.InvalidCodeException;

public enum ErrorCode
{
	INTERNAL_ERROR("amqp:internal-error"), NOT_FOUND("amqp:not-found"), UNAUTHORIZED_ACCESS("amqp:unauthorized-access"), DECODE_ERROR("amqp:decode-error"), RESOURCE_LIMIT_EXCEEDED("amqp:resource-limit-exceeded"), NOT_ALLOWED("amqp:not-allowed"), INVALID_FIELD("amqp:invalid-field"), NOT_IMPLEMENTED("amqp:not-implemented"), RESOURCE_LOCKED("amqp:resource-locked"), PRECONDITION_FAILED("amqp:precondition-failed"), RESOURCE_DELETED("amqp:resource-deleted"), ILLEGAL_STATE("amqp:illegal-state"), FRAME_SIZE_TOO_SMALL("amqp:frame-size-too-small"), CONNECTION_FORCED("amqp:connection-forced"), FRAMING_ERROR("amqp:framing-error"), REDIRECTED("amqp:redirected"), WINDOW_VIOLATION("amqp:window-violation"), ERRANT_LINK("amqp:errant-link"), HANDLE_IN_USE("amqp:handle-in-use"), UNATTACHED_HANDLE("amqp:unattached-handle"), DETACH_FORCED("amqp:detach-forced"), TRANSFER_LIMIT_EXCEEDED("amqp:transfer-limit-exceeded"), MESSAGE_SIZE_EXCEEDED("amqp:message-size-exceeded"), REDIRECT("amqp:redirect"), STOLEN("amqp:stolen");

	private static Map<String, ErrorCode> map = new HashMap<String, ErrorCode>();
	static
	{
		for (ErrorCode legEnum : ErrorCode.values())
		{
			map.put(legEnum.condition, legEnum);
		}
	}

	private String condition;

	public String getCondition()
	{
		return condition;
	}

	private ErrorCode(final String leg)
	{
		condition = leg;
	}

	public static ErrorCode getCondition(String condition)
	{
		ErrorCode code = map.get(condition);
		if (code == null)
			throw new InvalidCodeException("Unrecognized error condition: " + condition);
		return code;
	}
}
