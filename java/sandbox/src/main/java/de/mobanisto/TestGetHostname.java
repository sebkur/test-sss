package de.mobanisto;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class TestGetHostname
{

	public interface CLibrary extends Library
	{
		CLibrary INSTANCE = Native.load("c", CLibrary.class);

		// We can use methods from stdio.h but also unistd.h
		// see `apt-file show libc6-dev` for a list of header
		// files that belong to the c library.
		void gethostname(byte[] name, int len);
	}

	public static void main(String[] args)
	{
		byte[] buffer = new byte[100];
		CLibrary.INSTANCE.gethostname(buffer, buffer.length);
		String string = Native.toString(buffer);
		System.out.println(string);
	}

}
