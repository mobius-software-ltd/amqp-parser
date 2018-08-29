package com.mobius.software.amqp.parser.header.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.SaslException;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;

public class SASLResponse extends AMQPHeader
{
	private byte[] response;

	public SASLResponse()
	{
		super(HeaderCode.RESPONSE, 2, 1, 0);
	}

	private SASLResponse(HeaderCode code, int doff, int type, int channel, byte[] response)
	{
		super(code, doff, type, channel);
		this.response = response;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public TLVList toArgumentsList()
	{
		TLVList list = new TLVList();
		if (response == null)
			throw new MalformedHeaderException("SASL-Response header's challenge can't be null");
		list.addElement(0, AMQPWrapper.wrap(response));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x43 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size == 0)
			throw new MalformedHeaderException("Received malformed SASL-Response header: challenge can't be null");

		if (size > 1)
			throw new MalformedHeaderException("Received malformed SASL-Response header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed SASL-Response header: challenge can't be null");
			response = AMQPUnwrapper.unwrapBinary(element);
		}
	}

	private byte[] calcCramMD5(byte[] challenge, String user) throws SaslException
	{
		if (challenge != null && challenge.length != 0)
		{
			try
			{
				SecretKeySpec key = new SecretKeySpec(user.getBytes("ASCII"), "HMACMD5");
				Mac mac = Mac.getInstance("HMACMD5");
				mac.init(key);

				byte[] bytes = mac.doFinal(challenge);

				StringBuffer hash = new StringBuffer(user);
				hash.append(' ');
				for (int i = 0; i < bytes.length; i++)
				{
					String hex = Integer.toHexString(0xFF & bytes[i]);
					if (hex.length() == 1)
					{
						hash.append('0');
					}
					hash.append(hex);
				}
				return hash.toString().getBytes("ASCII");
			}
			catch (UnsupportedEncodingException e)
			{
				throw new SaslException("Unable to utilise required encoding", e);
			}
			catch (InvalidKeyException e)
			{
				throw new SaslException("Unable to utilise key", e);
			}
			catch (NoSuchAlgorithmException e)
			{
				throw new SaslException("Unable to utilise required algorithm", e);
			}
		}
		else
		{
			return new byte[0];
		}
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private byte[] response;

		public Builder()
		{
			super();
			this.type = 1;
		}

		public SASLResponse build()
		{
			return new SASLResponse(HeaderCode.RESPONSE, doff, type, channel, response);
		}

		public Builder response(byte[] response)
		{
			this.response = response;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "SASLResponse [response=" + Arrays.toString(response) + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(response);
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
		SASLResponse other = (SASLResponse) obj;
		if (!Arrays.equals(response, other.response))
			return false;
		return true;
	}

	public byte[] getResponse()
	{
		return response;
	}

	public void setCramMD5Response(byte[] challenge, String user) throws SaslException
	{
		if (user == null)
			throw new IllegalArgumentException("CramMD5 response generator must be provided with a non-null username value");
		if (challenge == null)
			throw new IllegalArgumentException("CramMD5 response generator must be provided with a non-null challenge value");
		this.response = calcCramMD5(challenge, user);
	}

}
