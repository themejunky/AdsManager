package com.themejunky.ads.manager;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Appnext.init(this);

        initView();

        flowAds.add("display");



//
        mModuleAdsManager = ((MainApplication)getApplication()).moduleAdsManager;
        mModuleAdsManager.setListenerAds(this);

        mModuleAdsManager.getManagerNative().setViewNative(findViewById(R.id.containerAdmob));
        mModuleAdsManager.getManagerNative().setNativeFlow(flowAds);

       // ((RelativeLayout) findViewById(R.id.containerAdmob)).addView(mModuleAdsManager.getManagerNative().getAds(flowAds));


     //   ((RelativeLayout) findViewById(R.id.containerAdmob)).addView(mModuleAdsManager.getManagerNative().getAds(flowAds));

       // mModuleAdsManager.getManagerNative().showAds(flowAds,((RelativeLayout) findViewById(R.id.containerAdmob)));
//

//        Log.d("mChoosenAd","start");
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("mChoosenAd","start 1");
//                mModuleAdsManager.getManagerNative().showAds(flowAds,((RelativeLayout) findViewById(R.id.containerAdmob)));
//            }
//        },5000);


        mModuleAdsManager.getManagerNative().showAds(flowAds,((RelativeLayout) findViewById(R.id.containerAdmob)));
       // startActivity(new Intent(this,Second.class));
//
//


//
//


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
                mModuleAdsManager.getManagerInterstitial().showInterstitial(flowAds, Action.APPLY);
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
}
