package de.mobanisto.libsss;

public class Secrets {


  public static byte[] create(String secret)
  {
    byte[] data = new byte[SSS.MLEN];
    byte[] stringBytes = secret.getBytes();
    int len = Math.min(data.length, stringBytes.length);
    System.arraycopy(stringBytes, 0, data, 0, len);
    return data;
  }

}
