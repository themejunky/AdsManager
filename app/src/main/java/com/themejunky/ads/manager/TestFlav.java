package com.themejunky.ads.manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ModuleAdsManager;
import themejunky.module_adsmanager.ModuleAdsManager2;
import themejunky.module_adsmanager.ads.AdsListenerManager;


public class TestFlav extends AppCompatActivity implements AdsListenerManager.ListenerAds {

    private List<String> flowAds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        ModuleAdsManager2 mManager = new ModuleAdsManager2(this,true,this);
        mManager.setNativeAdmobKey("ca-app-pub-8562466601970101/5081303159");

        mManager.logs("testarea");

        LinearLayout mContainer = findViewById(R.id.nAddd);
        mContainer.removeAllViews();

        mContainer.addView(mManager.getAds());
//        LinearLayout mContainer2 = findViewById(R.id.nAddd1);
//        mContainer2.removeAllViews();
//        mContainer2.addView(mManager.getAds());

//        //mContainer.removeAllViews();
//        mContainer.addView(mManager.getAds());
//
//        RelativeLayout mContainer2 = findViewById(R.id.nAds0);
//
//      //  mContainer2.removeAllViews();
//        mContainer2.addView(mManager.getAds());


      /*
        flowAds.add("admob");

        ModuleAdsManager moduleAdsManager = ModuleAdsManager.getInstance(this,true);
        moduleAdsManager.initializeNativeAds(true,false);
        moduleAdsManager.initAdmobNativeAds(((RelativeLayout) findViewById(R.id.containerAdmob)),"ca-app-pub-8562466601970101/5081303159");


        moduleAdsManager.setNativeFlowAndShowAds(flowAds, findViewById(R.id.containerFacebook), findViewById(R.id.containerAdmob),findViewById(R.id.containerAppnext));*/
    }






































    @Override
    public void afterInterstitialIsClosed(String action) {

    }

    @Override
    public void loadedInterAds() {

    }

    @Override
    public void loadInterFailed() {

    }

    @Override
    public void loadNativeAds(String type) {

    }
}
