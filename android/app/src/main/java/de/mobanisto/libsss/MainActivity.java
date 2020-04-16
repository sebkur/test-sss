package de.mobanisto.libsss;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

  private static final int NUM_PAGES = 2;

  private ViewPager mPager;

  private androidx.viewpager.widget.PagerAdapter pagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPager = findViewById(R.id.pager);
    pagerAdapter = new PagerAdapter(getSupportFragmentManager());
    mPager.setAdapter(pagerAdapter);
  }

  @Override
  public void onBackPressed()
  {
    if (mPager.getCurrentItem() == 0) {
      super.onBackPressed();
    } else {
      mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }
  }

  private class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm)
    {
      super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
      if (position == 0) {
        return new SSSFragment();
      } else {
        return new HazmatFragment();
      }
    }

    @Override
    public int getCount()
    {
      return NUM_PAGES;
    }

  }

}
