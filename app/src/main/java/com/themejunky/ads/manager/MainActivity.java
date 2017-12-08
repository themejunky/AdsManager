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
    private RelativeLayout containerFacebook,containerAdmob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        flowAds.add("vungle");
        flowAds.add("admob");
        flowAds.add("appnext");



        moduleAdsManager = ModuleAdsManager.getInstance(this);
        moduleAdsManager.setListenerAds(this);
        moduleAdsManager.initializeInterlAds(true);
        moduleAdsManager.initializeNativeAds(true,true);

        moduleAdsManager.initInterstitialAdmobAds("ca-app-pub-5322508131338449/2877444211");
       // moduleAdsManager.initInterstitialFacebookAds("698838770248387_848026318662964");
        moduleAdsManager.initInterstitialAppNextAds("8106d659-a20b-4640-943b-d6b0aab18d08");
        moduleAdsManager.initInterstitialVungle("5a2a60bdcbc18a6325000ef4");

        containerFacebook.removeAllViews();
        containerFacebook.addView(moduleAdsManager.getAllViewAds("facebook"));
        containerAdmob.removeAllViews();
        containerAdmob.addView(moduleAdsManager.getAllViewAds("admob"));


        moduleAdsManager.setLogs("InfoAds");
        moduleAdsManager.initAdmobNativeAds(containerAdmob,"ca-app-pub-8562466601970101/5081303159");
       //moduleAdsManager.initFacebookNativeAds(containerFacebook,"83194690296345_932932096864070");
    }

    public void initView(){
        apply = (Button) findViewById(R.id.applyid);
        rate = (Button) findViewById(R.id.rateId);
        getMore = (Button) findViewById(R.id.getmoreId);
        splash = (ImageView)findViewById(R.id.loadingId);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButonsId);
        containerFacebook = (RelativeLayout) findViewById(R.id.containerFacebook);
        containerAdmob = (RelativeLayout) findViewById(R.id.containerAdmob);

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
        Log.d("TestLogs","loadedAds");

        Log.d("TestLogs","loadedAds: "+splash.getVisibility());

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
            moduleAdsManager.setNativeFlowAndShowAds(flowAds, containerFacebook, containerAdmob);

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
