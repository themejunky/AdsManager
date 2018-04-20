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
import themejunky.module_adsmanager.managers.ModuleSuperManager;
import themejunky.module_adsmanager.utils.Action;


public class MainActivity extends AppCompatActivity implements AdsListenerManager.ListenerAds,View.OnClickListener {

    private List<String>flowAds = new ArrayList<>();
    private  ModuleSuperManager moduleSuperManager;
    private Button apply,rate,getMore;
    private View viewButtons;
    private ImageView splash;
    private LinearLayout layoutButtons;
    private byte nrFailedLoad=0;
    private RelativeLayout containerFacebook,mContainerAdmob ,containerAppnext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        flowAds.add("admob");


        moduleSuperManager = new ModuleSuperManager(this,true);
        moduleSuperManager.setLogName("logtest");

        moduleSuperManager.getManagerNative().setViewNative(findViewById(R.id.containerAdmob));
        moduleSuperManager.getManagerNative().setNativeFlow(flowAds);

        moduleSuperManager.getManagerNative().iniNativeFacebook("833164856890775_838240766383184");
        moduleSuperManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/5081303159");
        moduleSuperManager.getManagerNative().iniNativeAppnext("cdd052e2-9394-407c-99d4-323439dd7398");

        moduleSuperManager.getManagerInterstitial().initInterstitialAdmob("ca-app-pub-5322508131338449/2877444211");






      /*  moduleAdsManager = ModuleAdsManager.getInstance(this,true);
      //  moduleAdsManager.initNativeAdmob(this,"ca-app-pub-8562466601970101/5081303159");
        moduleAdsManager.setLogs("testlog");

        moduleAdsManager.setNaiveAdsFlow(flowAds);
        moduleAdsManager.initNativeAdmob(this,"ca-app-pub-8562466601970101/5081303159");
        //moduleAdsManager.setViewAdmobNative( findViewById(R.id.containerAdmob));*/





      /*  moduleAdsManager = ModuleAdsManager.getInstance(this,true);
        moduleAdsManager.setListenerAds(this);
        moduleAdsManager.initializeInterlAds(true);
        moduleAdsManager.initializeNativeAds(true,false);

        moduleAdsManager.initInterstitialAdmobAds("ca-app-pub-5322508131338449/2877444211");
        moduleAdsManager.initInterstitialFacebookAds("989309397888813_993580754128344");
        moduleAdsManager.initInterstitialAppNextAds("8106d659-a20b-4640-943b-d6b0aab18d08");

        containerFacebook.removeAllViews();
        containerFacebook.addView(moduleAdsManager.getAllViewAds("facebook"));
        containerAdmob.removeAllViews();
        containerAdmob.addView(moduleAdsManager.getAllViewAds("admob"));
        containerAppnext.removeAllViews();
        containerAppnext.addView(moduleAdsManager.getAllViewAds("appnext"));


        moduleAdsManager.setLogs("InfoAds");
        moduleAdsManager.initAdmobNativeAds(containerAdmob,"ca-app-pub-8562466601970101/5081303159");
        moduleAdsManager.initAppnextNativeAds(containerAppnext,"cdd052e2-9394-407c-99d4-323439dd7398");*/

       //moduleAdsManager.initFacebookNativeAds(containerFacebook,"989309397888813_993580754128344");
    }

    public void initView(){
        apply = (Button) findViewById(R.id.applyid);


        Log.d("TestLogs2","onCreate");

        apply.setOnClickListener(this);

    }


    @Override
    public void afterInterstitialIsClosed(String action) {
        Log.d("TestLogs","loadedAds");
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

        if (splash.getVisibility()== View.VISIBLE ) {
            Log.d("TestLogs","intra in metoda loaded");
            Log.d("loadereee","dispari");
            splash.setVisibility(View.GONE);
            layoutButtons.setVisibility(View.VISIBLE);

            // showAds(this,"in");


        } else {
            Log.d("loadereee","ai disparut");
        }

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
                if ( moduleSuperManager.getManagerInterstitial().isSomeAdLoaded()) {
                    Log.d("TestButton", "1");
                    moduleSuperManager.getManagerInterstitial().showInterstitial(flowAds,"intro");
                    Log.d("TestButton", "2");
                } else {
                    Log.d("TestButton", "3");
                    Toast.makeText(this, "Apply", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
