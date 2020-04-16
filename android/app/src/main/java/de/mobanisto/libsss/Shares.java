package de.mobanisto.libsss;

import java.util.List;

public class Shares {

  public static final int KEYSHARE_LEN = 33;
  public static final int MLEN = 64;
  public static final int CLEN = MLEN + 16;
  public static final int SHARE_LEN = CLEN + KEYSHARE_LEN;

  public static byte[] getShare(byte[] shares, int i)
  {
    byte[] share = new byte[SHARE_LEN];
    System.arraycopy(shares, i * SHARE_LEN, share, 0, SHARE_LEN);
    return share;
  }

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
