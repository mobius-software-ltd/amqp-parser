package com.mobius.software.amqp.parser.test.positive.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mobius.software.amqp.parser.AMQPParser;
import com.mobius.software.amqp.parser.header.api.AMQPHeader;
import com.mobius.software.amqp.parser.test.TestUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TestParser
{
	private List<File> samples;
	private List<File> qpid;
	private List<File> encoded;

	@Before
	public void setUp()
	{
		try
		{
			File[] files = TestUtil.getFilesList("captures/samples");
			samples = Arrays.asList(files);
			Collections.sort(samples);
			files = TestUtil.getFilesList("captures/qpid");
			qpid = Arrays.asList(files);
			Collections.sort(qpid);
			files = TestUtil.getFilesList("captures/encoded");
			encoded = Arrays.asList(files);
			Collections.sort(encoded);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail();
		}
	}

	@After
	public void tearDown()
	{
		samples = null;
		qpid = null;
		encoded = null;
	}

	@Test
	public void testGetNext()
	{
		byte[] arr = TestUtil.hexStringToByteArray(encoded.get(11).getAbsolutePath());
		ByteBuf buf = Unpooled.copiedBuffer(arr);
		int readableBytes = buf.readableBytes();
		ByteBuf next = AMQPParser.next(buf);
		assertEquals("Invalid parser getNext value: ", 249, next.capacity());
		assertEquals("Parser.getNext() doesn't release readerIndex: ", readableBytes, buf.readableBytes());
	}

	@Test
	public void testEncoded()
	{
		for (File file : encoded)
		{
			// System.out.println(file.getName());
			byte[] arr = TestUtil.hexStringToByteArray(file.getAbsolutePath());
			ByteBuf in = Unpooled.copiedBuffer(arr);
			AMQPHeader headerIn = AMQPParser.decode(in);
			ByteBuf out = AMQPParser.encode(headerIn);
			byte[] bytes = new byte[out.readableBytes()];
			out.readBytes(bytes);
			// System.out.println(StaticData.bytesToHex(arr));
			// System.out.println(StaticData.bytesToHex(bytes));
			assertArrayEquals(arr, bytes);
			AMQPHeader headerOut = AMQPParser.decode(Unpooled.copiedBuffer(bytes));
			out = AMQPParser.encode(headerOut);
			bytes = new byte[out.readableBytes()];
			out.readBytes(bytes);
			assertEquals(Arrays.equals(arr, bytes), true);
		}
	}

	@Test
	public void testSamples()
	{
		for (File file : samples)
		{
			// System.out.println(file.getName());
			byte[] arr = TestUtil.hexStringToByteArray(file.getAbsolutePath());
			ByteBuf in = Unpooled.copiedBuffer(arr);
			AMQPHeader headerIn = AMQPParser.decode(in);
			assertNotNull(String.format("Error parsing %s", file.getName()), headerIn);
			ByteBuf out = AMQPParser.encode(headerIn);
			byte[] bytes = new byte[out.readableBytes()];
			out.readBytes(bytes);
			// System.out.println(StaticData.bytesToHex(arr));
			// System.out.println(StaticData.bytesToHex(bytes));
			// assertEquals(Arrays.equals(arr, bytes), true);
			AMQPHeader headerOut = AMQPParser.decode(Unpooled.copiedBuffer(bytes));
			out = AMQPParser.encode(headerOut);
			bytes = new byte[out.readableBytes()];
			out.readBytes(bytes);
			// assertEquals(Arrays.equals(arr, bytes), true);
			assertNotNull(String.format("Error parsing %s", file.getName()), headerOut);
		}
	}

	@Test
	public void testQpid()
	{
		for (File file : qpid)
		{
			// System.out.println(qpidFile.getName());
			byte[] arr = TestUtil.hexStringToByteArray(file.getAbsolutePath());
			ByteBuf in = Unpooled.copiedBuffer(arr);
			AMQPHeader headerIn = AMQPParser.decode(in);
			assertNotNull(String.format("Error parsing %s", file.getName()), headerIn);
			ByteBuf out = AMQPParser.encode(headerIn);
			byte[] bytes = new byte[out.readableBytes()];
			out.readBytes(bytes);
			// assertEquals(Arrays.equals(arr, bytes), true);
			AMQPHeader headerOut = AMQPParser.decode(Unpooled.copiedBuffer(bytes));
			out = AMQPParser.encode(headerOut);
			bytes = new byte[out.readableBytes()];
			out.readBytes(bytes);
			// assertEquals(Arrays.equals(arr, bytes), true);
			assertNotNull(String.format("Error parsing %s", file.getName()), headerOut);
		}
	}

}
