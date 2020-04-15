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

  public interface CLibrary extends Library {

    CLibrary INSTANCE = Native.load(("c"), CLibrary.class);

    void printf(String format, Object... args);

    void sprintf(byte[] buffer, String format, Object... args);

  }

  public interface OurLibrary extends Library {

    OurLibrary INSTANCE = Native.load(("native-lib"), OurLibrary.class);

    void foo(byte[] buffer);

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
      List<String> secrets = computeSecrets(text);
      displaySecrets(secrets);
    });
  }

  private List<String> computeSecrets(CharSequence text)
  {
    System.out.println("computing secrets…");
    List<String> secrets = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      secrets.add(String.format("secret %d: %s", i + 1, text));

      byte[] buffer = new byte[100];
      OurLibrary.INSTANCE.foo(buffer);
      secrets.add(Native.toString(buffer));
    }
    System.out.println("done");
    return secrets;
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

    // Interesting, printf doesn't appear on console
    CLibrary.INSTANCE.printf("Hello World: %d\n", 321);
    // But sprintf works as expected
    byte[] buffer = new byte[100];
    CLibrary.INSTANCE.sprintf(buffer, "Hello World %d\n", 123);
    String string = Native.toString(buffer);
    System.out.print(string);
  }

}
