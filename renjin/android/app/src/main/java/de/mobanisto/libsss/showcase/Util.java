package de.mobanisto.libsss.showcase;

import java.util.ArrayList;
import java.util.List;

public class Util {

  public static <T> List<T> pick(List<T> secrets, int... indexes)
  {
    List<T> results = new ArrayList<>();
    for (int i : indexes) {
      results.add(secrets.get(i));
    }
    return results;
  }

}
