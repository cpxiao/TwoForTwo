package com.cpxiao.twofortwo.activity;

import android.os.Bundle;

import com.cpxiao.gamelib.activity.BaseZAdsActivity;
import com.cpxiao.gamelib.fragment.BaseFragment;
import com.cpxiao.twofortwo.fragment.HomeFragment;

public class MainActivity extends BaseZAdsActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return HomeFragment.newInstance(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ZAdManager.getInstance().init(getApplicationContext());
        loadAds();
    }

    private void loadAds() {
//        initAdMobAds100("");
//        initFbAds90("");
//        loadZAds(ZAdPosition.POSITION_MAIN);
    }

    @Override
    protected void onDestroy() {
//        ZAdManager.getInstance().destroyAllPosition(this);
        super.onDestroy();
    }
}
