package com.mobius.software.amqp.parser.exception;

public class InvalidCodeException extends RuntimeException
{
	private static final long serialVersionUID = 2996753493803809398L;

	public InvalidCodeException(String message)
	{
		super(message);
	}
}
