package com.cpxiao.twofortwo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.cpxiao.R;
import com.cpxiao.twofortwo.fragment.GameFragment;
import com.cpxiao.twofortwo.fragment.HomeFragment;
import com.cpxiao.twofortwo.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private HomeFragment mHomeFragment;
    private GameFragment mGameFragment;
    private SettingsFragment mSettingsFragment;
    private LinearLayout mLayoutAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mHomeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.fragment_container, mHomeFragment, "HomeFragment");
        fragmentTransaction.commit();
    }


}
