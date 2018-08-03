package com.themejunky.ads.manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ModuleAdsManager;
import themejunky.module_adsmanager.ads.AdsListenerManager;

/**
 * Created by ThemeJunky on 4/25/2018.
 */

public class Second extends AppCompatActivity implements AdsListenerManager.ListenerAds {

    private List<String> flowAds = new ArrayList<>();
    private ModuleAdsManager mModuleAdsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowAds.add("appnext");
        flowAds.add("admob");
        flowAds.add("facebook");

//
        ModuleAdsManager  mModuleAdsManager = new ModuleAdsManager();
        mModuleAdsManager.initManagers(this, true);
        mModuleAdsManager.setLogName("wwawq");

//
        mModuleAdsManager.getManagerNative().iniNativeFacebook("833164856890775_838240766383184",false);
        mModuleAdsManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/5081303159",false);

        //   mModuleAdsManager = ((MainApplication)getApplication()).moduleAdsManager;
        mModuleAdsManager.setListenerAds(this);


        try {
            mModuleAdsManager.getManagerNative().iniNativeAppnext("cdd052e2-9394-407c-99d4-323439dd7398",false);

        }
        catch (Exception e) {

        }
    }

    @Override
    public void loadedNativeAds(String type) {

    }

    @Override
    public void loadedInterstitialAds() {

    }

    @Override
    public void afterInterstitialIsClosed(String action) {

    }
}
