package com.mobius.software.amqp.parser;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.HeaderFactory;
import com.mobius.software.amqp.parser.header.impl.AMQPPing;
import com.mobius.software.amqp.parser.header.impl.AMQPProtoHeader;
import com.mobius.software.amqp.parser.header.impl.AMQPTransfer;
import com.mobius.software.amqp.parser.sections.AMQPSection;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class AMQPParser
{
	public static ByteBuf next(ByteBuf buf)
	{
		buf.markReaderIndex();
		int length = buf.readInt();
		if (length == 1095586128)
		{
			int protocolId = buf.readByte();
			int versionMajor = buf.readByte();
			int versionMinor = buf.readByte();
			int versionRevision = buf.readByte();
			if ((protocolId == 0 || protocolId == 3) && versionMajor == 1 && versionMinor == 0 && versionRevision == 0)
			{
				buf.resetReaderIndex();
				return Unpooled.buffer(8);
			}
		}
		buf.resetReaderIndex();
		return Unpooled.buffer(length);
	}

	public static AMQPHeader decode(ByteBuf buf)
	{
		long length = buf.readInt() & 0xffffffffL;
		int doff = buf.readByte() & 0xff;
		int type = buf.readByte() & 0xff;
		int channel = buf.readShort() & 0xffff;

		// TODO check condition
		if (length == 8 && doff == 2 && (type == 0 || type == 1) && channel == 0)
			if (buf.readableBytes() == 0)
				return new AMQPPing();
			else
				throw new MalformedHeaderException("Received malformed ping-header with invalid length");

		// PTOROCOL-HEADER
		if (length == 1095586128 && (doff == 3 || doff == 0) && type == 1 && channel == 0)
			if (buf.readableBytes() == 0)
				return new AMQPProtoHeader(doff);
			else
				throw new MalformedHeaderException("Received malformed protocol-header with invalid length");

		if (length != buf.readableBytes() + 8)
			throw new MalformedHeaderException("Received malformed header with invalid length");

		AMQPHeader header = null;
		if (type == 0)
			header = HeaderFactory.getAMQP(buf);
		else if (type == 1)
			header = HeaderFactory.getSASL(buf);
		else
			throw new MalformedHeaderException("Received malformed header with invalid type: " + type);

		header.setDoff(doff);
		header.setType(type);
		header.setChannel(channel);

		if (header.getCode().equals(HeaderCode.TRANSFER))
		{
			AMQPTransfer transfer = (AMQPTransfer) header;
			transfer.setSections(new LinkedHashMap<SectionCode, AMQPSection>());
			while (buf.readableBytes() > 0)
			{
				AMQPSection section = HeaderFactory.getSection(buf);
				transfer.getSections().put(section.getCode(), section);
			}
		}
		return header;
	}

	public static ByteBuf encode(AMQPHeader header)
	{

		ByteBuf buf = null;

		if (header instanceof AMQPProtoHeader)
		{
			buf = Unpooled.buffer(8);
			buf.writeBytes(new String("AMQP").getBytes(StandardCharsets.US_ASCII));
			buf.writeByte(((AMQPProtoHeader) header).getProtocolId());
			buf.writeByte(((AMQPProtoHeader) header).getVersionMajor());
			buf.writeByte(((AMQPProtoHeader) header).getVersionMinor());
			buf.writeByte(((AMQPProtoHeader) header).getVersionRevision());
			return buf;
		}

		if (header instanceof AMQPPing)
		{
			buf = Unpooled.buffer(8);
			buf.writeInt(8);
			buf.writeByte(header.getDoff());
			buf.writeByte(header.getType());
			buf.writeShort(header.getChannel());
			return buf;
		}

		int length = 8;

		TLVList arguments = header.toArgumentsList();
		length += arguments.getLength();

		Set<AMQPSection> sections = null;
		if (header.getCode().equals(HeaderCode.TRANSFER))
		{
			sections = new LinkedHashSet<>(((AMQPTransfer) header).getSections().values());
			for (AMQPSection section : sections)
				length += section.getValue().getLength();
		}

		buf = Unpooled.buffer(length);

		buf.writeInt(length);

		int doff = header.getDoff();
		buf.writeByte(doff);

		int type = header.getType();
		buf.writeByte(type);

		int channel = header.getChannel();
		buf.writeShort(channel);

		buf.writeBytes(arguments.getBytes());

		if (sections != null)
			for (AMQPSection section : sections)
				buf.writeBytes(section.getValue().getBytes());

		return buf;
	}
}
