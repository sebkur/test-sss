package de.mobanisto.libsss;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.ArrayList;
import java.util.List;

public class SSS {

  public static final int KEYSHARE_LEN = 33;
  public static final int MLEN = 64;
  public static final int CLEN = MLEN + 16;
  public static final int SHARE_LEN = CLEN + KEYSHARE_LEN;

  public interface LibSSS extends Library {

    LibSSS INSTANCE = Native.load("sss", LibSSS.class);

    // We would prefer to use byte[][] out and byte[][] shares, however
    // only one-dimensional arrays are supported by JNA. Hence we
    // convert between two-dimensional arrays and one-dimensional
    // arrays and vice versa between the Java and the native layer.

    void sss_create_shares(byte[] out, byte[] data, int n, int k);

    void sss_combine_shares(byte[] data, byte[] shares, int k);

    void sss_create_keyshares(byte[] out, byte[] key, int n, int k);

    void sss_combine_keyshares(byte[] key, byte[] shares, int k);
  }

  public static List<byte[]> createShares(byte[] data, int n, int k)
  {
    byte[] flatShares = new byte[n * SHARE_LEN];
    LibSSS.INSTANCE.sss_create_shares(flatShares, data, n, k);

    List<byte[]> shares = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      byte[] share = Shares.getShare(flatShares, i);
      shares.add(share);
    }
    return shares;
  }

  public static byte[] combineShares(List<byte[]> shares)
  {
    byte[] recovered = new byte[MLEN];
    byte[] availableShares = Shares.concat(shares);
    LibSSS.INSTANCE.sss_combine_shares(recovered, availableShares, shares.size());
    return recovered;
  }

}
