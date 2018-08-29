package com.mobius.software.amqp.parser.header.api;

import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public interface Parsable
{
	TLVList toArgumentsList();

	void fromArgumentsList(TLVList list);
}
