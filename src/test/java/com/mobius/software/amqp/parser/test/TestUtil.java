package com.mobius.software.amqp.parser.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil
{
	public static String[] getResourcesList(String folder, boolean absolutePath) throws IOException
	{
		File currFile = new File(TestUtil.class.getClassLoader().getResource(folder).getPath());
		File[] allFiles = currFile.listFiles();
		String[] result = new String[allFiles.length];
		for (int i = 0; i < allFiles.length; i++)
		{
			if (absolutePath)
				result[i] = allFiles[i].getAbsolutePath();
			else
				result[i] = allFiles[i].getName();
		}

		return result;
	}

	public static File[] getFilesList(String folder) throws IOException
	{
		File currFile = new File(TestUtil.class.getClassLoader().getResource(folder).getPath());
		File[] allFiles = currFile.listFiles();

		return allFiles;
	}

	static String readFile(String path, String encoding) throws IOException
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static byte[] hexStringToByteArray(String file)
	{
		String s;
		byte[] data = null;
		try
		{
			s = readFile(file, "UTF-8");
			data = new byte[s.length() / 2];
			for (int i = 0; i < data.length; i++)
			{
				data[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return data;
	}
}
