package de.mobanisto.libsss;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.ArrayList;
import java.util.List;

public class SSS {

  public interface LibSSS extends Library {

    LibSSS INSTANCE = Native.load("sss", LibSSS.class);

    // would like to use byte[][] out but only one-dimensional arrays
    // are supported by JNA
    void sss_create_shares(byte[] out, byte[] data, int n, int k);

    void sss_combine_shares(byte[] data, byte[] shares, int k);
  }

  public static List<byte[]> computeSecrets(String secret, int n, int k)
  {
    byte[] data = Shares.secret(secret);

    byte[] shares = new byte[n * Shares.SHARE_LEN];
    LibSSS.INSTANCE.sss_create_shares(shares, data, n, k);

    List<byte[]> secrets = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      byte[] share = Shares.getShare(shares, i);
      secrets.add(share);
    }
    return secrets;
  }

  public static byte[] recover(List<byte[]> shares)
  {
    byte[] recovered = new byte[Shares.MLEN];
    byte[] availableShares = Shares.concat(shares);
    LibSSS.INSTANCE.sss_combine_shares(recovered, availableShares, shares.size());
    return recovered;
  }

}
