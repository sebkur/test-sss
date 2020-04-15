package de.mobanisto.libsss;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  public interface LibSSS extends Library {

    LibSSS INSTANCE = Native.load("sss", LibSSS.class);

    // would like to use byte[][] out but only one-dimensional arrays
    // are supported by JNA
    void sss_create_shares(byte[] out, byte[] data, int n, int k);

    void sss_combine_shares(byte[] data, byte[] shares, int k);
  }

  public static final int KEYSHARE_LEN = 33;
  public static final int MLEN = 64;
  public static final int CLEN = MLEN + 16;
  public static final int SHARE_LEN = CLEN + KEYSHARE_LEN;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView input = findViewById(R.id.input);
    input.setText("secret text");

    View button = findViewById(R.id.button);
    button.setOnClickListener(e -> {
      CharSequence text = input.getText();
      List<String> secrets = computeSecrets(text);
      displaySecrets(secrets);
    });
  }

  private List<String> computeSecrets(CharSequence text)
  {
    int n = 3;
    int k = 2;

    System.out.println("computing secrets…");

    byte[] data = new byte[MLEN];
    for (int i = 0; i < data.length; i++) {
      data[i] = (byte) i;
    }

    byte[] shares = new byte[n * SHARE_LEN];
    LibSSS.INSTANCE.sss_create_shares(shares, data, n, k);

    List<String> secrets = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      secrets.add(String.format("secret %d: %s", i + 1, text));

      byte[] share = getShare(shares, i);
      secrets.add(toString(share));
    }
    System.out.println("done");
    return secrets;
  }

  private static byte[] getShare(byte[] shares, int i)
  {
    byte[] share = new byte[SHARE_LEN];
    System.arraycopy(shares, i * SHARE_LEN, share, 0, SHARE_LEN);
    return share;
  }

  private static String toString(byte[] share)
  {
    StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < share.length; i++) {
      byte b = share[i];
      buffer.append(String.format("%x", b));
    }
    return buffer.toString();
  }

  private List<TextView> textViewsSecrets = new ArrayList<>();

  private void displaySecrets(List<String> secrets)
  {
    LinearLayout layout = findViewById(R.id.linearLayout);

    for (TextView old : textViewsSecrets) {
      layout.removeView(old);
    }

    for (String secret : secrets) {
      TextView textView = new TextView(this);
      textView.setText(secret);
      layout.addView(textView);
      textViewsSecrets.add(textView);
    }
  }

}
