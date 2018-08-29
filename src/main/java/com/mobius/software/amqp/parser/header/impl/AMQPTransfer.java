package com.mobius.software.amqp.parser.header.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mobius.software.amqp.parser.avps.AMQPType;
import com.mobius.software.amqp.parser.avps.HeaderCode;
import com.mobius.software.amqp.parser.avps.ReceiveCode;
import com.mobius.software.amqp.parser.avps.SectionCode;
import com.mobius.software.amqp.parser.constructor.DescribedConstructor;
import com.mobius.software.amqp.parser.exception.MalformedHeaderException;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.header.api.AMQPUnwrapper;
import com.mobius.software.amqp.parser.header.api.AMQPWrapper;
import com.mobius.software.amqp.parser.header.api.HeaderFactory;
import com.mobius.software.amqp.parser.sections.AMQPSection;
import com.mobius.software.amqp.parser.tlv.api.TLVAmqp;
import com.mobius.software.amqp.parser.tlv.impl.*;
import com.mobius.software.amqp.parser.wrappers.AMQPMessageFormat;

public class AMQPTransfer extends AMQPHeader
{
	private Long handle;
	private Long deliveryId;
	private byte[] deliveryTag;
	private AMQPMessageFormat messageFormat;
	private Boolean settled;
	private Boolean more;
	private ReceiveCode rcvSettleMode;
	private AMQPState state;
	private Boolean resume;
	private Boolean aborted;
	private Boolean batchable;
	private Map<SectionCode, AMQPSection> sections;

	public AMQPTransfer()
	{
		super(HeaderCode.TRANSFER, 2, 0, 0);
	}

	private AMQPTransfer(HeaderCode code, int doff, int type, int channel, Long handle, Long deliveryId, byte[] deliveryTag, AMQPMessageFormat messageFormat, Boolean settled, Boolean more, ReceiveCode rcvSettleMode, AMQPState state, Boolean resume, Boolean aborted, Boolean batchable, Map<SectionCode, AMQPSection> sections)
	{
		super(code, doff, type, channel);
		this.handle = handle;
		this.deliveryId = deliveryId;
		this.deliveryTag = deliveryTag;
		this.messageFormat = messageFormat;
		this.settled = settled;
		this.more = more;
		this.rcvSettleMode = rcvSettleMode;
		this.state = state;
		this.resume = resume;
		this.aborted = aborted;
		this.batchable = batchable;
		this.sections = sections;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder extends AMQPHeaderBuilder<Builder>
	{
		private Long handle;
		private Long deliveryId;
		private byte[] deliveryTag;
		private AMQPMessageFormat messageFormat;
		private Boolean settled;
		private Boolean more;
		private ReceiveCode rcvSettleMode;
		private AMQPState state;
		private Boolean resume;
		private Boolean aborted;
		private Boolean batchable;
		private Map<SectionCode, AMQPSection> sections = new LinkedHashMap<>();

		public AMQPTransfer build()
		{
			return new AMQPTransfer(HeaderCode.TRANSFER, doff, type, channel, handle, deliveryId, deliveryTag, messageFormat, settled, more, rcvSettleMode, state, resume, aborted, batchable, sections);
		}

		public Builder handle(Long handle)
		{
			this.handle = handle;
			return this;
		}

		public Builder deliveryId(Long deliveryId)
		{
			this.deliveryId = deliveryId;
			return this;
		}

		public Builder deliveryTag(byte[] deliveryTag)
		{
			this.deliveryTag = deliveryTag;
			return this;
		}

		public Builder messageFormat(AMQPMessageFormat messageFormat)
		{
			this.messageFormat = messageFormat;
			return this;
		}

		public Builder settled(boolean settled)
		{
			this.settled = settled;
			return this;
		}

		public Builder more(boolean more)
		{
			this.more = more;
			return this;
		}

		public Builder rcvSettleMode(ReceiveCode rcvSettleMode)
		{
			this.rcvSettleMode = rcvSettleMode;
			return this;
		}

		public Builder withStateAccepted()
		{
			this.state = new AMQPAccepted();
			return this;
		}

		public Builder withStateRejected(AMQPError error)
		{
			this.state = new AMQPRejected(error);
			return this;
		}

		public Builder withStateReleased()
		{
			this.state = new AMQPReleased();
			return this;
		}

		public Builder withState(AMQPState state)
		{
			this.state = state;
			return this;
		}

		public Builder resume(boolean resume)
		{
			this.resume = resume;
			return this;
		}

		public Builder aborted(boolean aborted)
		{
			this.aborted = aborted;
			return this;
		}

		public Builder batchable(boolean batchable)
		{
			this.batchable = batchable;
			return this;
		}

		public Builder addSection(AMQPSection section)
		{
			this.sections.put(section.getCode(), section);
			return this;
		}
	}

	@Override
	public TLVList toArgumentsList()
	{

		TLVList list = new TLVList();

		if (handle == null)
			throw new MalformedHeaderException("Transfer header's handle can't be null");
		list.addElement(0, AMQPWrapper.wrap(handle));

		if (deliveryId != null)
			list.addElement(1, AMQPWrapper.wrap(deliveryId));
		if (deliveryTag != null)
			list.addElement(2, AMQPWrapper.wrap(deliveryTag));
		if (messageFormat != null)
			list.addElement(3, AMQPWrapper.wrap(messageFormat.encode()));
		if (settled != null)
			list.addElement(4, AMQPWrapper.wrap(settled));
		if (more != null)
			list.addElement(5, AMQPWrapper.wrap(more));
		if (rcvSettleMode != null)
			list.addElement(6, AMQPWrapper.wrap(rcvSettleMode.getType()));
		if (state != null)
			list.addElement(7, state.toArgumentsList());
		if (resume != null)
			list.addElement(8, AMQPWrapper.wrap(resume));
		if (aborted != null)
			list.addElement(9, AMQPWrapper.wrap(aborted));
		if (batchable != null)
			list.addElement(10, AMQPWrapper.wrap(batchable));

		DescribedConstructor constructor = new DescribedConstructor(list.getCode(), new TLVFixed(AMQPType.SMALL_ULONG, new byte[]
		{ (byte) code.getType() }));
		list.setConstructor(constructor);

		return list;
	}

	@Override
	public void fromArgumentsList(TLVList list)
	{

		int size = list.getList().size();

		if (size == 0)
			throw new MalformedHeaderException("Received malformed Transfer header: handle can't be null");

		if (size > 11)
			throw new MalformedHeaderException("Received malformed Transfer header. Invalid number of arguments: " + size);

		if (size > 0)
		{
			TLVAmqp element = list.getList().get(0);
			if (element.isNull())
				throw new MalformedHeaderException("Received malformed Transfer header: handle can't be null");
			handle = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 1)
		{
			TLVAmqp element = list.getList().get(1);
			if (!element.isNull())
				deliveryId = AMQPUnwrapper.unwrapUInt(element);
		}
		if (size > 2)
		{
			TLVAmqp element = list.getList().get(2);
			if (!element.isNull())
				deliveryTag = AMQPUnwrapper.unwrapBinary(element);
		}
		if (size > 3)
		{
			TLVAmqp element = list.getList().get(3);
			if (!element.isNull())
				messageFormat = new AMQPMessageFormat(AMQPUnwrapper.unwrapUInt(element));
		}
		if (size > 4)
		{
			TLVAmqp element = list.getList().get(4);
			if (!element.isNull())
				settled = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 5)
		{
			TLVAmqp element = list.getList().get(5);
			if (!element.isNull())
				more = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 6)
		{
			TLVAmqp element = list.getList().get(6);
			if (!element.isNull())
				rcvSettleMode = ReceiveCode.valueOf(AMQPUnwrapper.unwrapUByte(element));
		}
		if (size > 7)
		{
			TLVAmqp element = list.getList().get(7);
			if (!element.isNull())
			{
				AMQPType code = element.getCode();
				if (code != AMQPType.LIST_0 && code != AMQPType.LIST_8 && code != AMQPType.LIST_32)
					throw new MalformedHeaderException("Expected type 'STATE' - received: " + element.getCode());
				state = HeaderFactory.getState((TLVList) element);
				state.fromArgumentsList((TLVList) element);
			}
		}
		if (size > 8)
		{
			TLVAmqp element = list.getList().get(8);
			if (!element.isNull())
				resume = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 9)
		{
			TLVAmqp element = list.getList().get(9);
			if (!element.isNull())
				aborted = AMQPUnwrapper.unwrapBool(element);
		}
		if (size > 10)
		{
			TLVAmqp element = list.getList().get(10);
			if (!element.isNull())
				batchable = AMQPUnwrapper.unwrapBool(element);
		}

	}

	@Override
	public String toString()
	{
		return "AMQPTransfer [handle=" + handle + ", deliveryId=" + deliveryId + ", deliveryTag=" + Arrays.toString(deliveryTag) + ", messageFormat=" + messageFormat + ", settled=" + settled + ", more=" + more + ", rcvSettleMode=" + rcvSettleMode + ", state=" + state + ", resume=" + resume + ", aborted=" + aborted + ", batchable=" + batchable + ", sections=" + sections + ", code=" + code + ", doff=" + doff + ", type=" + type + ", channel=" + channel + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((aborted == null) ? 0 : aborted.hashCode());
		result = prime * result + ((batchable == null) ? 0 : batchable.hashCode());
		result = prime * result + ((deliveryId == null) ? 0 : deliveryId.hashCode());
		result = prime * result + Arrays.hashCode(deliveryTag);
		result = prime * result + ((handle == null) ? 0 : handle.hashCode());
		result = prime * result + ((messageFormat == null) ? 0 : messageFormat.hashCode());
		result = prime * result + ((more == null) ? 0 : more.hashCode());
		result = prime * result + ((rcvSettleMode == null) ? 0 : rcvSettleMode.hashCode());
		result = prime * result + ((resume == null) ? 0 : resume.hashCode());
		result = prime * result + ((sections == null) ? 0 : sections.hashCode());
		result = prime * result + ((settled == null) ? 0 : settled.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		AMQPTransfer other = (AMQPTransfer) obj;
		if (aborted == null)
		{
			if (other.aborted != null)
				return false;
		}
		else if (!aborted.equals(other.aborted))
			return false;
		if (batchable == null)
		{
			if (other.batchable != null)
				return false;
		}
		else if (!batchable.equals(other.batchable))
			return false;
		if (deliveryId == null)
		{
			if (other.deliveryId != null)
				return false;
		}
		else if (!deliveryId.equals(other.deliveryId))
			return false;
		if (!Arrays.equals(deliveryTag, other.deliveryTag))
			return false;
		if (handle == null)
		{
			if (other.handle != null)
				return false;
		}
		else if (!handle.equals(other.handle))
			return false;
		if (messageFormat == null)
		{
			if (other.messageFormat != null)
				return false;
		}
		else if (!messageFormat.equals(other.messageFormat))
			return false;
		if (more == null)
		{
			if (other.more != null)
				return false;
		}
		else if (!more.equals(other.more))
			return false;
		if (rcvSettleMode != other.rcvSettleMode)
			return false;
		if (resume == null)
		{
			if (other.resume != null)
				return false;
		}
		else if (!resume.equals(other.resume))
			return false;
		if (sections == null)
		{
			if (other.sections != null)
				return false;
		}
		else if (!sections.equals(other.sections))
			return false;
		if (settled == null)
		{
			if (other.settled != null)
				return false;
		}
		else if (!settled.equals(other.settled))
			return false;
		if (state == null)
		{
			if (other.state != null)
				return false;
		}
		else if (!state.equals(other.state))
			return false;
		return true;
	}

	public Long getHandle()
	{
		return handle;
	}

	public void setHandle(Long handle)
	{
		this.handle = handle;
	}

	public Long getDeliveryId()
	{
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId)
	{
		this.deliveryId = deliveryId;
	}

	public byte[] getDeliveryTag()
	{
		return deliveryTag;
	}

	public void setDeliveryTag(byte[] deliveryTag)
	{
		this.deliveryTag = deliveryTag;
	}

	public AMQPMessageFormat getMessageFormat()
	{
		return messageFormat;
	}

	public void setMessageFormat(AMQPMessageFormat messageFormat)
	{
		this.messageFormat = messageFormat;
	}

	public Boolean getSettled()
	{
		return settled;
	}

	public void setSettled(Boolean settled)
	{
		this.settled = settled;
	}

	public Boolean getMore()
	{
		return more;
	}

	public void setMore(Boolean more)
	{
		this.more = more;
	}

	public ReceiveCode getRcvSettleMode()
	{
		return rcvSettleMode;
	}

	public void setRcvSettleMode(ReceiveCode rcvSettleMode)
	{
		this.rcvSettleMode = rcvSettleMode;
	}

	public AMQPState getState()
	{
		return state;
	}

	public void setState(AMQPState state)
	{
		this.state = state;
	}

	public Boolean getResume()
	{
		return resume;
	}

	public void setResume(Boolean resume)
	{
		this.resume = resume;
	}

	public Boolean getAborted()
	{
		return aborted;
	}

	public void setAborted(Boolean aborted)
	{
		this.aborted = aborted;
	}

	public Boolean getBatchable()
	{
		return batchable;
	}

	public void setBatchable(Boolean batchable)
	{
		this.batchable = batchable;
	}

	public AMQPSection getHeader()
	{
		return sections != null ? sections.get(SectionCode.HEADER) : null;
	}

	public AMQPSection getDeliveryAnnotations()
	{
		return sections != null ? sections.get(SectionCode.DELIVERY_ANNOTATIONS) : null;
	}

	public AMQPSection getMessageAnnotations()
	{
		return sections != null ? sections.get(SectionCode.MESSAGE_ANNOTATIONS) : null;
	}

	public AMQPSection getProperties()
	{
		return sections != null ? sections.get(SectionCode.PROPERTIES) : null;
	}

	public AMQPSection getApplicationProperties()
	{
		return sections != null ? sections.get(SectionCode.APPLICATION_PROPERTIES) : null;
	}

	public AMQPSection getData()
	{
		return sections != null ? sections.get(SectionCode.DATA) : null;
	}

	public AMQPSection getSequence()
	{
		return sections != null ? sections.get(SectionCode.SEQUENCE) : null;
	}

	public AMQPSection getValue()
	{
		return sections != null ? sections.get(SectionCode.VALUE) : null;
	}

	public AMQPSection getFooter()
	{
		return sections != null ? sections.get(SectionCode.FOOTER) : null;
	}

	public Map<SectionCode, AMQPSection> getSections()
	{
		return sections;
	}

	public void setSections(Map<SectionCode, AMQPSection> sections)
	{
		this.sections = sections;
	}
}
