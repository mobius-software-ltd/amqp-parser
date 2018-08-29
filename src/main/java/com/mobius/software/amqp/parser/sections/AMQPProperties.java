package com.mobius.software.amqp.parser.sections;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.TLVFixed;
import com.mobius.software.amqp.parser.tlv.impl.TLVList;
import com.mobius.software.amqp.parser.wrappers.BinaryID;
import com.mobius.software.amqp.parser.wrappers.LongID;
import com.mobius.software.amqp.parser.wrappers.MessageID;
import com.mobius.software.amqp.parser.wrappers.StringID;
import com.mobius.software.amqp.parser.wrappers.UuidID;

public class AMQPProperties implements AMQPSection
{
	private MessageID messageId;
	private byte[] userId;
	private String to;
	private String subject;
	private String replyTo;
	private byte[] correlationId;
	private String contentType;
	private String contentEncoding;
	private Date absoluteExpiryTime;
	private Date creationTime;
	private String groupId;
	private Long groupSequence;
	private String replyToGroupId;

	public AMQPProperties()
	{
	}

	private AMQPProperties(MessageID messageId, byte[] userId, String to, String subject, String replyTo, byte[] correlationId, String contentType, String contentEncoding, Date absoluteExpiryTime, Date creationTime, String groupId, Long groupSequence, String replyToGroupId)
	{
		this.messageId = messageId;
		this.userId = userId;
		this.to = to;
		this.subject = subject;
		this.replyTo = replyTo;
		this.correlationId = correlationId;
		this.contentType = contentType;
		this.contentEncoding = contentEncoding;
		this.absoluteExpiryTime = absoluteExpiryTime;
		this.creationTime = creationTime;
		this.groupId = groupId;
		this.groupSequence = groupSequence;
		this.replyToGroupId = replyToGroupId;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	@Override
	public SectionCode getCode()
	{
		return SectionCode.PROPERTIES;
	}

	@Override
	public TLVAmqp getValue()
	{

		TLVList list = new TLVList();

		if (messageId != null)
		{
			Object value = null;
			if (messageId.getBinary() != null)
				value = messageId.getBinary();
			else if (messageId.getLong() != null)
				value = messageId.getLong();
			else if (messageId.getString() != null)
				value = messageId.getString();
			else if (messageId.getUuid() != null)
				value = messageId.getUuid();
			list.addElement(0, AMQPWrapper.wrap(value));
		}
		if (userId != null)
			list.addElement(1, AMQPWrapper.wrap(userId));
		if (to != null)
			list.addElement(2, AMQPWrapper.wrap(to));
		if (subject != null)
			list.addElement(3, AMQPWrapper.wrap(subject));
		if (replyTo != null)
			list.addElement(4, AMQPWrapper.wrap(replyTo));
		if (correlationId != null)
			list.addElement(5, AMQPWrapper.wrap(correlationId));
		if (contentType != null)
			list.addElement(6, AMQPWrapper.wrap(contentType));
		if (contentEncoding != null)
			list.addElement(7, AMQPWrapper.wrap(contentEncoding));
		if (absoluteExpiryTime != null)
			list.addElement(8, AMQPWrapper.wrap(absoluteExpiryTime));
		if (creationTime != null)
			list.addElement(9, AMQPWrapper.wrap(creationTime));
		if (groupId != null)
			list.addElement(10, AMQPWrapper.wrap(groupId));
		if (groupSequence != null)
			list.addElement(11, AMQPWrapper.wrap(groupSequence));
		if (replyToGroupId != null)
			list.addElement(12, AMQPWrapper.wrap(replyToGroupId));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ 0x73 }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fill(TLVAmqp value)
	{
		TLVList list = (TLVList) value;
		if (list.getList().size() > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (!element.isNull())
			{
				switch (element.getCode())
				{
				case ULONG_0:
				case SMALL_ULONG:
				case ULONG:
					messageId = new LongID(AMQPUnwrapper.unwrapULong(element));
					break;
				case STRING_8:
				case STRING_32:
					messageId = new StringID(AMQPUnwrapper.unwrapString(element));
					break;
				case BINARY_8:
				case BINARY_32:
					messageId = new BinaryID(AMQPUnwrapper.unwrapBinary(element));
					break;
				case UUID:
					messageId = new UuidID(AMQPUnwrapper.unwrapUuid(element));
					break;
				default:
					throw new IllegalArgumentException("Expected type 'MessageID' - received: " + element.getCode());
				}
			}
		}
		if (list.getList().size() > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				userId = AMQPUnwrapper.unwrapBinary(element);
		}
		if (list.getList().size() > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				to = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (!element.isNull())
				subject = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				replyTo = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
				correlationId = AMQPUnwrapper.unwrapBinary(element);
		}
		if (list.getList().size() > 6)
		{
			TLVAmqp element = list.getList().get(6);
			if (!element.isNull())
				contentType = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 7)
		{
			TLVAmqp element = list.getList().get(7);
			if (!element.isNull())
				contentEncoding = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 8)
		{
			TLVAmqp element = list.getList().get(8);
			if (!element.isNull())
				absoluteExpiryTime = AMQPUnwrapper.unwrapTimestamp(element);
		}
		if (list.getList().size() > 9)
		{
			TLVAmqp element = list.getList().get(9);
			if (!element.isNull())
				creationTime = AMQPUnwrapper.unwrapTimestamp(element);
		}
		if (list.getList().size() > 10)
		{
			TLVAmqp element = list.getList().get(10);
			if (!element.isNull())
				groupId = AMQPUnwrapper.unwrapString(element);
		}
		if (list.getList().size() > 11)
		{
			TLVAmqp element = list.getList().get(11);
			if (!element.isNull())
				groupSequence = AMQPUnwrapper.unwrapUInt(element);
		}
		if (list.getList().size() > 12)
		{
			TLVAmqp element = list.getList().get(12);
			if (!element.isNull())
				replyToGroupId = AMQPUnwrapper.unwrapString(element);
		}
	}

	public static class Builder
	{
		private MessageID messageId;
		private byte[] userId;
		private String to;
		private String subject;
		private String replyTo;
		private byte[] correlationId;
		private String contentType;
		private String contentEncoding;
		private Date absoluteExpiryTime;
		private Date creationTime;
		private String groupId;
		private Long groupSequence;
		private String replyToGroupId;

		public AMQPProperties build()
		{
			return new AMQPProperties(messageId, userId, to, subject, replyTo, correlationId, contentType, contentEncoding, absoluteExpiryTime, creationTime, groupId, groupSequence, replyToGroupId);
		}

		public Builder withMessageID(byte[] id)
		{
			this.messageId = new BinaryID(id);
			return this;
		}

		public Builder withMessageID(long id)
		{
			this.messageId = new LongID(BigInteger.valueOf(id));
			return this;
		}

		public Builder withMessageID(BigInteger id)
		{
			this.messageId = new LongID(id);
			return this;
		}

		public Builder withMessageID(String id)
		{
			this.messageId = new StringID(id);
			return this;
		}

		public Builder withMessageID(UUID id)
		{
			this.messageId = new UuidID(id);
			return this;
		}

		public Builder userId(byte[] userId)
		{
			this.userId = userId;
			return this;
		}

		public Builder to(String to)
		{
			this.to = to;
			return this;
		}

		public Builder subject(String subject)
		{
			this.subject = subject;
			return this;
		}

		public Builder replyTo(String replyTo)
		{
			this.replyTo = replyTo;
			return this;
		}

		public Builder correlationId(byte[] correlationId)
		{
			this.correlationId = correlationId;
			return this;
		}

		public Builder contentType(String contentType)
		{
			this.contentType = contentType;
			return this;
		}

		public Builder contentEncoding(String contentEncoding)
		{
			this.contentEncoding = contentEncoding;
			return this;
		}

		public Builder absoluteExpiryTime(Date absoluteExpiryTime)
		{
			this.absoluteExpiryTime = absoluteExpiryTime;
			return this;
		}

		public Builder creationTime(Date creationTime)
		{
			this.creationTime = creationTime;
			return this;
		}

		public Builder groupId(String groupId)
		{
			this.groupId = groupId;
			return this;
		}

		public Builder groupSequence(Long groupSequence)
		{
			this.groupSequence = groupSequence;
			return this;
		}

		public Builder replyToGroupId(String replyToGroupId)
		{
			this.replyToGroupId = replyToGroupId;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return "AMQPProperties [messageId=" + messageId + ", userId=" + Arrays.toString(userId) + ", to=" + to + ", subject=" + subject + ", replyTo=" + replyTo + ", correlationId=" + Arrays.toString(correlationId) + ", contentType=" + contentType + ", contentEncoding=" + contentEncoding + ", absoluteExpiryTime=" + absoluteExpiryTime + ", creationTime=" + creationTime + ", groupId=" + groupId + ", groupSequence=" + groupSequence + ", replyToGroupId=" + replyToGroupId + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absoluteExpiryTime == null) ? 0 : absoluteExpiryTime.hashCode());
		result = prime * result + ((contentEncoding == null) ? 0 : contentEncoding.hashCode());
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + Arrays.hashCode(correlationId);
		result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((groupSequence == null) ? 0 : groupSequence.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((replyTo == null) ? 0 : replyTo.hashCode());
		result = prime * result + ((replyToGroupId == null) ? 0 : replyToGroupId.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + Arrays.hashCode(userId);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AMQPProperties other = (AMQPProperties) obj;
		if (absoluteExpiryTime == null)
		{
			if (other.absoluteExpiryTime != null)
				return false;
		}
		else if (!absoluteExpiryTime.equals(other.absoluteExpiryTime))
			return false;
		if (contentEncoding == null)
		{
			if (other.contentEncoding != null)
				return false;
		}
		else if (!contentEncoding.equals(other.contentEncoding))
			return false;
		if (contentType == null)
		{
			if (other.contentType != null)
				return false;
		}
		else if (!contentType.equals(other.contentType))
			return false;
		if (!Arrays.equals(correlationId, other.correlationId))
			return false;
		if (creationTime == null)
		{
			if (other.creationTime != null)
				return false;
		}
		else if (!creationTime.equals(other.creationTime))
			return false;
		if (groupId == null)
		{
			if (other.groupId != null)
				return false;
		}
		else if (!groupId.equals(other.groupId))
			return false;
		if (groupSequence == null)
		{
			if (other.groupSequence != null)
				return false;
		}
		else if (!groupSequence.equals(other.groupSequence))
			return false;
		if (messageId == null)
		{
			if (other.messageId != null)
				return false;
		}
		else if (!messageId.equals(other.messageId))
			return false;
		if (replyTo == null)
		{
			if (other.replyTo != null)
				return false;
		}
		else if (!replyTo.equals(other.replyTo))
			return false;
		if (replyToGroupId == null)
		{
			if (other.replyToGroupId != null)
				return false;
		}
		else if (!replyToGroupId.equals(other.replyToGroupId))
			return false;
		if (subject == null)
		{
			if (other.subject != null)
				return false;
		}
		else if (!subject.equals(other.subject))
			return false;
		if (to == null)
		{
			if (other.to != null)
				return false;
		}
		else if (!to.equals(other.to))
			return false;
		if (!Arrays.equals(userId, other.userId))
			return false;
		return true;
	}

	public MessageID getMessageId()
	{
		return messageId;
	}

	public void setMessageId(MessageID messageId)
	{
		this.messageId = messageId;
	}

	public byte[] getUserId()
	{
		return userId;
	}

	public void setUserId(byte[] userId)
	{
		this.userId = userId;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getReplyTo()
	{
		return replyTo;
	}

	public void setReplyTo(String replyTo)
	{
		this.replyTo = replyTo;
	}

	public byte[] getCorrelationId()
	{
		return correlationId;
	}

	public void setCorrelationId(byte[] correlationId)
	{
		this.correlationId = correlationId;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getContentEncoding()
	{
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding)
	{
		this.contentEncoding = contentEncoding;
	}

	public Date getAbsoluteExpiryTime()
	{
		return absoluteExpiryTime;
	}

	public void setAbsoluteExpiryTime(Date absoluteExpiryTime)
	{
		this.absoluteExpiryTime = absoluteExpiryTime;
	}

	public Date getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime(Date creationTime)
	{
		this.creationTime = creationTime;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public Long getGroupSequence()
	{
		return groupSequence;
	}

	public void setGroupSequence(Long groupSequence)
	{
		this.groupSequence = groupSequence;
	}

	public String getReplyToGroupId()
	{
		return replyToGroupId;
	}

	public void setReplyToGroupId(String replyToGroupId)
	{
		this.replyToGroupId = replyToGroupId;
	}

}
