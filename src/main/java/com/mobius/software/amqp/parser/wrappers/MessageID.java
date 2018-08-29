package com.mobius.software.amqp.parser.wrappers;

import java.math.BigInteger;
import java.util.UUID;

public interface MessageID
{
	String getString();

	byte[] getBinary();

	BigInteger getLong();

	UUID getUuid();
	
	int hashCode();
	
	boolean equals(Object other);
}
