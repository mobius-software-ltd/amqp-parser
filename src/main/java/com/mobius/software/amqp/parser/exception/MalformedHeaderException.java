package com.mobius.software.amqp.parser.exception;

public class MalformedHeaderException extends RuntimeException
{
	private static final long serialVersionUID = 3763330149957232236L;

	public MalformedHeaderException(String message)
	{
		super(message);
	}
}
