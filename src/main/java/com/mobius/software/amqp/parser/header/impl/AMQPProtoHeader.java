package com.mobius.software.amqp.parser.header.impl;

import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class AMQPProtoHeader extends AMQPHeader
{
	public static final AMQPProtoHeader VER_1_0_0_NO_SASL = new AMQPProtoHeader(0);
	public static final AMQPProtoHeader VER_1_0_0_SASL = new AMQPProtoHeader(3);

	private final String protocol = "AMQP";
	private final int protocolId;
	private final int versionMajor = 1;
	private final int versionMinor = 0;
	private final int versionRevision = 0;

	public AMQPProtoHeader(int protocolId)
	{
		super(HeaderCode.PROTO, 2, 0, 0);
		this.protocolId = protocolId;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public int getProtocolId()
	{
		return protocolId;
	}

	public int getVersionMajor()
	{
		return versionMajor;
	}

	public int getVersionMinor()
	{
		return versionMinor;
	}

	public int getVersionRevision()
	{
		return versionRevision;
	}

	@Override
	public String toString()
	{
		return "AMQPProtoHeader [protocol=" + protocol + ", protocolId=" + protocolId + ", versionMajor=" + versionMajor + ", versionMinor=" + versionMinor + ", versionRevision=" + versionRevision + "]";
	}

	public byte[] getBytes()
	{
		byte[] bytes = new byte[8];
		System.arraycopy(protocol.getBytes(), 0, bytes, 0, protocol.length());
		bytes[4] = (byte) protocolId;
		bytes[5] = (byte) versionMajor;
		bytes[6] = (byte) versionMinor;
		bytes[7] = (byte) versionRevision;
		return bytes;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		result = prime * result + protocolId;
		result = prime * result + versionMajor;
		result = prime * result + versionMinor;
		result = prime * result + versionRevision;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AMQPProtoHeader other = (AMQPProtoHeader) obj;
		if (protocol == null)
		{
			if (other.protocol != null)
				return false;
		}
		else if (!protocol.equals(other.protocol))
			return false;
		if (protocolId != other.protocolId)
			return false;
		if (versionMajor != other.versionMajor)
			return false;
		if (versionMinor != other.versionMinor)
			return false;
		if (versionRevision != other.versionRevision)
			return false;
		return true;
	}

	@Override
	public TLVList toArgumentsList()
	{
		return null;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{
	}

}
