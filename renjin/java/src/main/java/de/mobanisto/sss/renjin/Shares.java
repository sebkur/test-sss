package de.mobanisto.sss.renjin;

import java.util.List;

public class Shares
{

	/**
	 * From array of shares encoded in a 1-dimensional array, get the i'th
	 * share.
	 *
	 * @param shares
	 *            the 1-dimensional array
	 * @param i
	 *            the index of the share to get
	 * @return the share extracted from the array.
	 */
	public static byte[] getShare(byte[] shares, int i)
	{
		byte[] share = new byte[SSS.SHARE_LEN];
		System.arraycopy(shares, i * SSS.SHARE_LEN, share, 0, SSS.SHARE_LEN);
		return share;
	}

	/**
	 * From array of keyshares encoded in a 1-dimensional array, get the i'th
	 * keyshare.
	 *
	 * @param shares
	 *            the 1-dimensional array
	 * @param i
	 *            the index of the keyshare to get
	 * @return the keyshare extracted from the array.
	 */
	public static byte[] getKeyshare(byte[] shares, int i)
	{
		byte[] share = new byte[SSS.KEYSHARE_LEN];
		System.arraycopy(shares, i * SSS.KEYSHARE_LEN, share, 0,
				SSS.KEYSHARE_LEN);
		return share;
	}

	/**
	 * Concatenate a list of shares into a 1-dimensional array.
	 *
	 * @param shares
	 *            the list of shares to combine
	 * @return an array containing the data of all shares.
	 */
	public static byte[] concat(List<byte[]> shares)
	{
		int total = 0;
		for (byte[] share : shares) {
			total += share.length;
		}

		byte[] result = new byte[total];
		int pos = 0;
		for (byte[] share : shares) {
			System.arraycopy(share, 0, result, pos, share.length);
			pos += share.length;
		}

		return result;
	}

	/**
	 * Get a hexadecimal string representation of the specified share.
	 *
	 * @param share
	 *            the share to convert to a string
	 * @return a string that displays raw data bytes as human-readable
	 *         hexadecimal values.
	 */
	public static String toHexString(byte[] share)
	{
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < share.length; i++) {
			byte b = share[i];
			buffer.append(String.format("%x", b));
		}
		return buffer.toString();
	}

}
