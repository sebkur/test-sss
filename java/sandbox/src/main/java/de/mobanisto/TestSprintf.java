package de.mobanisto;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class TestSprintf
{

	public interface CLibrary extends Library
	{
		CLibrary INSTANCE = Native.load((Platform.isWindows() ? "msvcrt" : "c"),
				CLibrary.class);

		void printf(String format, Object... args);

		void sprintf(byte[] buffer, String format, Object... args);
	}

	public static void main(String[] args)
	{
		byte[] buffer = new byte[100];
		CLibrary.INSTANCE.sprintf(buffer, "Hello World %d\n", 123);
		String string = Native.toString(buffer);
		System.out.print(string);
		for (int i = 0; i < args.length; i++) {
			CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
		}
	}

}
