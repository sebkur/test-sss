package de.mobanisto.sss;

import java.security.SecureRandom;

import org.renjin.gcc.runtime.BytePtr;

public class randombytes__
{

	/*
	 * We're overriding this method because the system call to retrieve random
	 * bytes cannot currently be compiled using the gcc-bridge.
	 */
	public static int randombytes(org.renjin.gcc.runtime.Ptr buf, int n)
	{
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[n];
		random.nextBytes(bytes);
		BytePtr source = new BytePtr(bytes);
		buf.memcpy(source, n);
		return n;
	}

}
