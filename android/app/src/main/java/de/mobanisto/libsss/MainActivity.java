package de.mobanisto.libsss;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView input = findViewById(R.id.input);
    input.setText("very secret information");

    View button = findViewById(R.id.button);
    button.setOnClickListener(e -> {
      CharSequence text = input.getText();
      cipherDecipherAndDisplay(text.toString());
    });
  }

  private void cipherDecipherAndDisplay(String secretText)
  {
    System.out.println("computing shares…");
    byte[] secret = Secrets.create(secretText);

    List<byte[]> shares = SSS.createShares(secret, 3, 2);
    System.out.println("done");

    clearTextViews();
    displayShares(shares);

    byte[] recovered = SSS.combineShares(Util.pick(shares, 0, 1));
    addTextView("recovered secret: " + new String(recovered));
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

  private void displayShares(List<byte[]> shares)
  {
    for (int i = 0; i < shares.size(); i++) {
      byte[] share = shares.get(i);

      String shareHex = Shares.toHexString(share);
      addTextView(String.format("share %d: %s", i + 1, shareHex));
    }
  }

}
