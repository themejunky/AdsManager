package com.themejunky.ads.manager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appnext.base.Appnext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ModuleAdsManager;
import themejunky.module_adsmanager.utils.Action;


public class MainActivity extends AppCompatActivity implements AdsListenerManager.ListenerAds, View.OnClickListener {

    private List<String> flowAds = new ArrayList<>();
    private ModuleAdsManager mModuleAdsManager;
    private Button apply, rate, getMore;
    private View viewButtons;
    private ImageView splash;
    private LinearLayout layoutButtons;
    private byte nrFailedLoad = 0;
    private RelativeLayout containerFacebook, mContainerAdmob, containerAppnext;
    private boolean isLoaded;
    final private String APP_ID = "app185a7e71e1714831a49ec7";
    final private String ZONE_ID = "vz06e8c32a037749699e7050";
    private List<String> placementIdVungle = Arrays.asList("DEFAULT-0525668");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Appnext.init(this);

        initView();
        Log.d("TestButton","onCreate");

        flowAds.add("adcolony");



        mModuleAdsManager = ((MainApplication)getApplication()).moduleAdsManager;
        mModuleAdsManager.setListenerAds(this);
        mModuleAdsManager.initManagers(this, true);
       // mModuleAdsManager.getManagerInterstitial().initInterstitialChartboost(this,"5af56f18e113780b0e5a1360", "46cfc662d3d840bf07db9f500244dc7820453682"); //test
     //   mModuleAdsManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/5081303159",false);
       // mModuleAdsManager.getManagerInterstitial().initInterstitialAdColony(APP_ID,ZONE_ID);
        mModuleAdsManager.getManagerInterstitial().initInterstitialAdColony("app1f87c72549f94ad9bb","vzf857cf81285d4051bc");
        //mModuleAdsManager.getManagerInterstitial().initInterstitialVungle("5b33864e05b4826538f1f58f",placementIdVungle);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TestButton","---------start 1---------------");
               // mModuleAdsManager.getManagerNative().showAds(flowAds,((RelativeLayout) findViewById(R.id.containerAdmob)));
                findViewById(R.id.applyid).setVisibility(View.VISIBLE);

            }
        },5000);



    }

    public void initView() {
        apply = (Button) findViewById(R.id.applyid);


        Log.d("TestLogs2", "onCreate");

        apply.setOnClickListener(this);

    }


    @Override
    public void afterInterstitialIsClosed(String action) {
        Log.d("TestLogs", "afterInterstitialIsClosed");
        switch (action) {
            case Action.APPLY:
                startActivity(new Intent(this, ApplyActivity.class));
                break;
            case "rate":
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case "more":
                Toast.makeText(this, "GetMore", Toast.LENGTH_SHORT).show();
                break;
            case "back":
                finish();
                break;

        }

    }

    @Override
    public void loadedInterstitialAds() {
        isLoaded = true;
    }


    @Override
    public void loadedNativeAds(String type) {
    /*    if (type.equals("admob") && flowAds.size() > 0) {
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob,containerAppnext);

        }else if(type.equals("appnext") && flowAds.size() > 0){
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob,containerAppnext);
        }*/
    }


    @Override
    public void onClick(View v) {
        viewButtons = v;
        switch (viewButtons.getId()) {
            case R.id.applyid:
               if(mModuleAdsManager.getManagerInterstitial().isSomeAdLoaded()){
                mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.APPLY);
            }else{
                   startActivity(new Intent(this, ApplyActivity.class));
               }

              /*  mModuleAdsManager.getManagerInterstitial().reLoadedInterstitial();

                if (mModuleAdsManager.getManagerInterstitial().isSomeAdLoaded()) {
                    Log.d("TestButton", "1");
                    mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.APPLY);
                    Log.d("TestButton", "2");
                } else {
                    Log.d("TestButton", "3");
                    Toast.makeText(this, "Apply", Toast.LENGTH_SHORT).show();
                }*/
                break;
    }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.BACK);
    }

    @Override
    protected void onDestroy() {
        mModuleAdsManager.getManagerInterstitial().destroyDisplay();
        super.onDestroy();
       // mModuleAdsManager.getManagerNative().nContainer.removeAllViews();
        Log.d("TestButton","onDestroy");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TestButton","onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TestButton","onResume");
        mModuleAdsManager.getManagerInterstitial().adColonyOnResume();
    }
}
