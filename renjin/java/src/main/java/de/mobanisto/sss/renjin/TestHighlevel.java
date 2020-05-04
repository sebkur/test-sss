package de.mobanisto.sss.renjin;

import java.util.List;

import de.mobanisto.sss.SSS;
import de.mobanisto.sss.Secrets;
import de.mobanisto.sss.Shares;

public class TestHighlevel
{

	public static void main(String[] args)
	{
		int n = 3;
		int k = 2;

		byte[] data = Secrets.create("a secret");

		System.out.println(new String(data));

		List<byte[]> shares = SSS.createShares(data, n, k);

		for (byte[] share : shares) {
			System.out.println(Shares.toHexString(share));
		}

		byte[] recovered = SSS.combineShares(shares.subList(0, k));

		System.out.println(new String(recovered));
	}

}
