package com.themejunky.ads.manager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ModuleAdsManager;
import themejunky.module_adsmanager.utils.Action;


public class MainActivity extends AppCompatActivity implements AdsListenerManager.ListenerAds,View.OnClickListener {

    private List<String>flowAds = new ArrayList<>();
    private ModuleAdsManager moduleAdsManager;
    private Button apply,rate,getMore;
    private View viewButtons;
    private ImageView splash;
    private LinearLayout layoutButtons;
    private byte nrFailedLoad=0;
    private RelativeLayout containerFacebook,mContainerAdmob ,containerAppnext;
    private boolean isLoaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        flowAds.add("admob");



       moduleAdsManager = new ModuleAdsManager();
        moduleAdsManager.setListenerAds(this);
        moduleAdsManager.initManagers(this,true);
        moduleAdsManager.setLogName("wwawq");

        moduleAdsManager.getManagerNative().setNativeFlow(flowAds);
        moduleAdsManager.getManagerNative().setViewNative(findViewById(R.id.containerAdmob));

        moduleAdsManager.getManagerNative().iniNativeFacebook("833164856890775_838240766383184");
        moduleAdsManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/5081303159");
        moduleAdsManager.getManagerNative().iniNativeAppnext("cdd052e2-9394-407c-99d4-323439dd7398");


        moduleAdsManager.getManagerInterstitial().initInterstitialAppnext("95620754-d968-4e6a-a5da-7ece51d9cacd");
        moduleAdsManager.getManagerInterstitial().initInterstitialFacebook("156810341634297_156813291634002");
        moduleAdsManager.getManagerInterstitial().initInterstitialAdmob("ca-app-pub-5322508131338449/2877444211");






    }

    public void initView(){
        apply = (Button) findViewById(R.id.applyid);


        Log.d("TestLogs2","onCreate");

        apply.setOnClickListener(this);

    }


    @Override
    public void afterInterstitialIsClosed(String action) {
        Log.d("TestLogs","afterInterstitialIsClosed");
        switch (action){
            case Action.APPLY:
                startActivity(new Intent(this,ApplyActivity.class));
                break;
            case "rate":
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case "more":
                Toast.makeText(this, "GetMore", Toast.LENGTH_SHORT).show();
                break;
            case "back":

                break;

        }

    }

    @Override
    public void loadedInterstitialAds() {
        isLoaded=true;
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
                moduleAdsManager.getManagerInterstitial().reLoadedInterstitial();

                if ( moduleAdsManager.getManagerInterstitial().isSomeAdLoaded()) {
                    Log.d("TestButton", "1");
                    moduleAdsManager.getManagerInterstitial().showInterstitial(flowAds,"intro");
                    Log.d("TestButton", "2");
                } else {
                    Log.d("TestButton", "3");
                    Toast.makeText(this, "Apply", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
