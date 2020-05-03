package de.mobanisto.sss.renjin;

import org.renjin.gcc.example.Example;
import org.renjin.gcc.runtime.BytePtr;

public class Test
{

	public static void main(String[] args)
	{
		int n = 3;
		int k = 2;

		byte[] data = new byte[SSS.MLEN];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) i;
		}

		System.out.println(Shares.toHexString(data));

		byte[] shares = new byte[n * SSS.SHARE_LEN];

		BytePtr rshares = new BytePtr(shares);
		BytePtr rdata = new BytePtr(data);
		Example.sss_create_shares(rshares, rdata, (byte) n, (byte) k);

		for (int i = 0; i < n; i++) {
			byte[] share = Shares.getShare(shares, i);
			System.out.println(Shares.toHexString(share));
		}

		byte[] recovered = new byte[SSS.MLEN];
		BytePtr rrecovered = new BytePtr(recovered);
		Example.sss_combine_shares(rrecovered, rshares, (byte) 2);

		System.out.println(Shares.toHexString(recovered));
	}

}
