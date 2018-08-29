package com.mobius.software.amqp.parser.sections;

import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;

public interface AMQPSection
{
	void fill(TLVAmqp list);

	TLVAmqp getValue();

	SectionCode getCode();
}
