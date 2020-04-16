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
      List<byte[]> secrets = computeSecrets(text);
      clearTextViews();
      displaySecrets(secrets);
      byte[] recovered = recover(Util.pick(secrets, 0, 1));
      addTextView("recovered text: " + new String(recovered));
    });
  }

  private byte[] recover(List<byte[]> shares)
  {
    byte[] recovered = new byte[Shares.MLEN];
    byte[] availableShares = Shares.concat(shares);
    LibSSS.INSTANCE.sss_combine_shares(recovered, availableShares, shares.size());
    return recovered;
  }

  private List<byte[]> computeSecrets(CharSequence text)
  {
    int n = 3;
    int k = 2;

    String secret = "very secret information";
    System.out.println("computing secrets…");

    byte[] data = Shares.secret(secret);

    byte[] shares = new byte[n * Shares.SHARE_LEN];
    LibSSS.INSTANCE.sss_create_shares(shares, data, n, k);

    List<byte[]> secrets = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      byte[] share = Shares.getShare(shares, i);
      secrets.add(share);
    }
    System.out.println("done");
    return secrets;
  }

  private List<TextView> textViews = new ArrayList<>();

  private void clearTextViews()
  {
    LinearLayout layout = findViewById(R.id.linearLayout);
    for (TextView old : textViews) {
      layout.removeView(old);
    }
  }

  private void addTextView(String text)
  {
    LinearLayout layout = findViewById(R.id.linearLayout);
    TextView textView = new TextView(this);
    layout.addView(textView);
    textViews.add(textView);
    textView.setText(text);
  }

  private void displaySecrets(List<byte[]> shares)
  {
    for (int i = 0; i < shares.size(); i++) {
      byte[] share = shares.get(i);

      String shareHex = Shares.toHexString(share);
      addTextView(String.format("secret %d: %s", i + 1, shareHex));
    }
  }

}
