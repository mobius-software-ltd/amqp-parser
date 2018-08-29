package com.mobius.software.amqp.parser.header.api;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.avps.StateCode;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.sections.*;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.api.TLVFactory;
import com.mobius.software.amqp.parser.tlv.impl.*;

import io.netty.buffer.ByteBuf;

public class HeaderFactory
{
	public static AMQPHeader getAMQP(ByteBuf buf)
	{
		TLVAmqp list = TLVFactory.getTlv(buf);
		if (!list.getCode().equals(AMQPType.LIST_0) && list.getCode().equals(AMQPType.LIST_8) && list.getCode().equals(AMQPType.LIST_32))
			throw new MalformedHeaderException("Received amqp-header with malformed arguments");

		Byte byteCode = list.getConstructor().getDescriptorCode();
		HeaderCode code = HeaderCode.valueOf(byteCode);

		AMQPHeader header = code.emptyHeader();
		header.fromArgumentsList((TLVList) list);
		return header;
	}

	public static AMQPHeader getSASL(ByteBuf buf)
	{
		TLVAmqp list = TLVFactory.getTlv(buf);
		if (!list.getCode().equals(AMQPType.LIST_0) && list.getCode().equals(AMQPType.LIST_8) && list.getCode().equals(AMQPType.LIST_32))
			throw new MalformedHeaderException("Received sasl-header with malformed arguments");

		Byte byteCode = list.getConstructor().getDescriptorCode();
		HeaderCode code = HeaderCode.valueOf(byteCode);

		AMQPHeader header = code.emptySASL();
		header.fromArgumentsList((TLVList) list);
		return header;
	}

	public static AMQPSection getSection(ByteBuf buf)
	{
		TLVAmqp value = TLVFactory.getTlv(buf);
		AMQPSection section = null;
		Byte byteCode = value.getConstructor().getDescriptorCode();
		SectionCode code = SectionCode.valueOf(byteCode);
		switch (code)
		{
		case APPLICATION_PROPERTIES:
			section = ApplicationProperties.builder().build();
			break;
		case DATA:
			section = new AMQPData();
			break;
		case DELIVERY_ANNOTATIONS:
			section = DeliveryAnnotations.builder().build();
			break;
		case FOOTER:
			section = AMQPFooter.builder().build();
			break;
		case HEADER:
			section = MessageHeader.builder().build();
			break;
		case MESSAGE_ANNOTATIONS:
			section = MessageAnnotations.builder().build();
			break;
		case PROPERTIES:
			section = AMQPProperties.builder().build();
			break;
		case SEQUENCE:
			section = AMQPSequence.builder().build();
			break;
		case VALUE:
			section = new AMQPValue();
			break;
		default:
			throw new MalformedHeaderException("Received header with unrecognized message section code");
		}

		section.fill(value);

		return section;
	}

	public static AMQPState getState(TLVList list)
	{
		AMQPState state = null;
		Byte byteCode = list.getConstructor().getDescriptorCode();
		StateCode code = StateCode.valueOf(byteCode);
		switch (code)
		{
		case ACCEPTED:
			state = new AMQPAccepted();
			break;
		case MODIFIED:
			state = AMQPModified.builder().build();
			break;
		case RECEIVED:
			state = new AMQPReceived();
			break;
		case REJECTED:
			state = new AMQPRejected();
			break;
		case RELEASED:
			state = new AMQPReleased();
			break;
		default:
			throw new MalformedHeaderException("Received header with unrecognized state code");
		}

		return state;
	}

	public static AMQPOutcome getOutcome(TLVList list)
	{
		AMQPOutcome outcome = null;
		Byte byteCode = list.getConstructor().getDescriptorCode();
		StateCode code = StateCode.valueOf(byteCode);
		switch (code)
		{
		case ACCEPTED:
			outcome = new AMQPAccepted();
			break;
		case MODIFIED:
			outcome = AMQPModified.builder().build();
			break;
		case REJECTED:
			outcome = new AMQPRejected();
			break;
		case RELEASED:
			outcome = new AMQPReleased();
			break;
		default:
			throw new MalformedHeaderException("Received header with unrecognized outcome code");
		}
		return outcome;
	}
}
