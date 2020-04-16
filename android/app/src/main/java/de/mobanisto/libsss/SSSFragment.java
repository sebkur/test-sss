package de.mobanisto.libsss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SSSFragment extends Fragment {

  private LinearLayout layout;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    ViewGroup rootView = (ViewGroup) inflater.inflate(
        R.layout.fragment_sss, container, false);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
  {
    layout = getView().findViewById(R.id.linearLayout);

    TextView input = getView().findViewById(R.id.input);
    input.setText("very secret information");

    View button = getView().findViewById(R.id.button);
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
    for (TextView old : textViews) {
      layout.removeView(old);
    }
  }

  private void addTextView(String text)
  {
    TextView textView = new TextView(getContext());
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
