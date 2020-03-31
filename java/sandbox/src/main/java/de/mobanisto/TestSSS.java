package de.mobanisto;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class TestSSS
{

	public static final int KEYSHARE_LEN = 33;
	public static final int MLEN = 64;
	public static final int CLEN = MLEN + 16;
	public static final int SHARE_LEN = CLEN + KEYSHARE_LEN;

	public interface LibSSS extends Library
	{
		LibSSS INSTANCE = Native.load("sss", LibSSS.class);

		// would like to use byte[][] out but only one-dimensional arrays
		// are supported by JNA
		void sss_create_shares(byte[] out, byte[] data, int n, int k);

		void sss_combine_shares(byte[] data, byte[] shares, int k);
	}

	public static void main(String[] args)
	{
		String home = System.getProperty("user.home");
		System.setProperty("jna.library.path",
				home + "/github/dsprenkels/sss/");

		int n = 3;
		int k = 2;

		byte[] data = new byte[MLEN];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) i;
		}

		printData(data);

		byte[] shares = new byte[n * SHARE_LEN];
		LibSSS.INSTANCE.sss_create_shares(shares, data, n, k);

		for (int i = 0; i < n; i++) {
			byte[] share = getShare(shares, i);
			printShare(share);
		}

		byte[] recovered = new byte[MLEN];
		LibSSS.INSTANCE.sss_combine_shares(recovered, shares, 2);

		printData(recovered);
	}

	private static byte[] getShare(byte[] shares, int i)
	{
		byte[] share = new byte[SHARE_LEN];
		System.arraycopy(shares, i * SHARE_LEN, share, 0, SHARE_LEN);
		return share;
	}

	private static void printData(byte[] data)
	{
		for (int i = 0; i < data.length; i++) {
			byte b = data[i];
			System.out.printf("%02x", b);
		}
		System.out.println();
	}

	private static void printShare(byte[] share)
	{
		for (int i = 0; i < share.length; i++) {
			byte b = share[i];
			System.out.printf("%x", b);
		}
		System.out.println();
	}

}
