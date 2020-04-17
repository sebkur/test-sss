package de.mobanisto;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class TestSSSEncodings
{

	public static final int KEYSHARE_LEN = 33;
	public static final int MLEN = 64;
	public static final int CLEN = MLEN + 16;
	public static final int SHARE_LEN = CLEN + KEYSHARE_LEN;

	public interface LibSSS extends Library
	{
		LibSSS INSTANCE = Native.load("sss", LibSSS.class);

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

		print("data", hex(data));

		byte[] shares = new byte[n * SHARE_LEN];
		LibSSS.INSTANCE.sss_create_shares(shares, data, n, k);

		for (int i = 0; i < n; i++) {
			byte[] share = getShare(shares, i);
			print("hex", hex(share));
			print("base64", Base64.encodeBase64String(share));
			print("base32", new Base32().encodeToString(share));
			print("base32h", new Base32(true).encodeToString(share));
		}

		byte[] recovered = new byte[MLEN];
		LibSSS.INSTANCE.sss_combine_shares(recovered, shares, 2);

		print("data", hex(recovered));
	}

	private static byte[] getShare(byte[] shares, int i)
	{
		byte[] share = new byte[SHARE_LEN];
		System.arraycopy(shares, i * SHARE_LEN, share, 0, SHARE_LEN);
		return share;
	}

	private static String hex(byte[] data)
	{
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			byte b = data[i];
			buffer.append(String.format("%x", b));
		}
		return buffer.toString();
	}

	private static void print(String name, String encoded)
	{
		System.out.printf("%-7s (%d): %s", name, encoded.length(), encoded);
		System.out.println();
	}

}
