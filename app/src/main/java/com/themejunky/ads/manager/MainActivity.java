package com.themejunky.ads.manager;


import android.app.MediaRouteButton;
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

import themejunky.module_adsmanager.ModuleAdsManager;
import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.utils.Action;


public class MainActivity extends AppCompatActivity implements AdsListenerManager.ListenerAds,View.OnClickListener {

    private List<String>flowAds = new ArrayList<>();
    private ModuleAdsManager moduleAdsManager;
    private Button apply,rate,getMore;
    private View viewButtons;
    private ImageView splash;
    private LinearLayout layoutButtons;
    private byte nrFailedLoad=0;
    private RelativeLayout containerFacebook,containerAdmob ,containerAppnext, containerVungle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        flowAds.add("facebook");
        flowAds.add("vungle");
        flowAds.add("admob");
        flowAds.add("appnext");


        moduleAdsManager = ModuleAdsManager.getInstance(this,true);
        moduleAdsManager.setListenerAds(this);
        moduleAdsManager.initializeInterlAds(true);
        moduleAdsManager.initializeNativeAds(true,false);

        moduleAdsManager.initInterstitialAdmobAds("ca-app-pub-5322508131338449/2877444211");
        moduleAdsManager.initInterstitialFacebookAds("989309397888813_993580754128344");
        moduleAdsManager.initInterstitialAppNextAds("8106d659-a20b-4640-943b-d6b0aab18d08");

        //moduleAdsManager.initInterstitialVungle("5abb495d1a839166af6a8e62","DEFAULT-6433071"); //red velvet
        moduleAdsManager.initInterstitialVungle("5916309cb46f6b5a3e00009c","DEFAULT32590"); //test vungle ad

        containerFacebook.removeAllViews();
        containerFacebook.addView(moduleAdsManager.getAllViewAds("facebook"));
        containerAdmob.removeAllViews();
        containerAdmob.addView(moduleAdsManager.getAllViewAds("admob"));
        containerAppnext.removeAllViews();
        containerAppnext.addView(moduleAdsManager.getAllViewAds("appnext"));
        containerVungle.removeAllViews();
        containerVungle.addView(moduleAdsManager.getAllViewAds("vungle"));

        moduleAdsManager.setLogs("InfoAds");
        moduleAdsManager.initAdmobNativeAds(containerAdmob,"ca-app-pub-8562466601970101/5081303159");
        //moduleAdsManager.initAppnextNativeAds(containerAppnext,"cdd052e2-9394-407c-99d4-323439dd7398");
        //moduleAdsManager.initFacebookNativeAds(containerFacebook,"989309397888813_993580754128344");
    }

    public void initView(){
        apply = (Button) findViewById(R.id.applyid);
        rate = (Button) findViewById(R.id.rateId);
        getMore = (Button) findViewById(R.id.getmoreId);
        splash = (ImageView)findViewById(R.id.loadingId);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButonsId);
        containerFacebook = (RelativeLayout) findViewById(R.id.containerFacebook);
        containerAdmob = (RelativeLayout) findViewById(R.id.containerAdmob);
        containerAppnext = (RelativeLayout) findViewById(R.id.containerAppnext);
        containerVungle = (RelativeLayout) findViewById(R.id.containerVungle);

        Log.d("TestLogs2","onCreate");

        apply.setOnClickListener(this);
        rate.setOnClickListener(this);
        getMore.setOnClickListener(this);
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
    public void loadedInterAds() {

        if (splash.getVisibility()== View.VISIBLE ) {
            Log.d("TestLogs","intra in metoda loaded");
            Log.d("loadereee","dispari");
            splash.setVisibility(View.GONE);
            layoutButtons.setVisibility(View.VISIBLE);

            // showAds(this,"in");

            moduleAdsManager.setInterFlowAndShowAds(flowAds,"NADA");

        } else {
            Log.d("loadereee","ai disparut");
        }

    }

    @Override
    public void loadInterFailed() {
        Log.d("TestLogs","loadeFailed");
        Log.d("TestLogs", "" +(View.GONE));
        Log.d("TestLogs", String.valueOf(splash.getVisibility()));
        nrFailedLoad++;
        if(nrFailedLoad==3){
            Log.d("TestLogs","inra in metoda faild 1");
            splash.setVisibility(View.GONE);
            Log.d("TestLogs","inra in metoda faild 2");
            layoutButtons.setVisibility(View.VISIBLE);
            layoutButtons.bringToFront();
            Log.d("TestLogs","inra in metoda faild 3");



            nrFailedLoad=0;
        }
        Log.d("TestLogs", "nr: "+String.valueOf(nrFailedLoad));
    }

    @Override
    public void loadNativeAds(String type) {
        if (type.equals("admob") && flowAds.size() > 0) {
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob,containerAppnext, containerVungle);
        } else if(type.equals("appnext") && flowAds.size() > 0){
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob,containerAppnext, containerVungle);
        } else if(type.equals("vungle") && flowAds.size() > 0){
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob, containerAppnext, containerVungle);
        }
    }


    @Override
    public void onClick(View v) {
        viewButtons = v;
        switch (viewButtons.getId()) {
            case R.id.applyid:
                if(moduleAdsManager.isSomeAdLoaded()){
                    Log.d("TestButton", "1");
                    moduleAdsManager.setInterFlowAndShowAds(flowAds,"apply");
                    Log.d("TestButton", "2");
                }else {
                    Log.d("TestButton", "3");
                    Toast.makeText(this, "Apply", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rateId:
                if(moduleAdsManager.isSomeAdLoaded()){
                    moduleAdsManager.setInterFlowAndShowAds(flowAds,"rate");
                    // mam.setAction("rate");
                }else {
                    Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.getmoreId:
                if(moduleAdsManager.isSomeAdLoaded()){
                    moduleAdsManager.setInterFlowAndShowAds(flowAds,"more");
                    // mam.setAction("more");
                }else {
                    Toast.makeText(this, "GetMore", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        moduleAdsManager.setAdmobeMute(this);
    }
}
