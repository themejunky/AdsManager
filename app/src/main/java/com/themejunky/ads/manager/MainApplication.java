package com.themejunky.ads.manager;

import android.app.Activity;
import android.app.Application;

import com.appnext.base.Appnext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import themejunky.module_adsmanager.ModuleAdsManager;


/**
 * Created by Junky2 on 8/2/2017.
 */

public class MainApplication extends Application {
    public List<String> flowAdsInceput = new ArrayList<>();
    public List<String> flowAdsSfarsit = new ArrayList<>();
    public ModuleAdsManager moduleAdsManager;
    private List<String> placementVungle = Arrays.asList("DEFAULT-1982645");

    @Override
    public void onCreate() {
        super.onCreate();

        Appnext.init(this);

        flowAdsInceput.add("chartboost");
        /*flowAdsInceput.add("display");
        flowAdsInceput.add("facebook");
        flowAdsInceput.add("vungle");
        flowAdsInceput.add("admob");
        flowAdsInceput.add("appnext");*/



        moduleAdsManager = new ModuleAdsManager();
        //moduleAdsManager.initManagers(this, true);
        moduleAdsManager.setLogName("TestButton");

//
      /*  moduleAdsManager.getManagerNative().iniNativeFacebook("833164856890775_838240766383184",false);
        moduleAdsManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/5081303159",false);
        moduleAdsManager.getManagerNative().iniNativeAppnext("cdd052e2-9394-407c-99d4-323439dd7398",false);
        moduleAdsManager.getManagerInterstitial().initInterstitialVungle("5b06af181f1e8a3626d6bc9d", placementVungle);
        moduleAdsManager.getManagerInterstitial().initInterstitialDisplay("6363","3079");*/

        //moduleAdsManager.getManagerInterstitial().initInterstitialChartboost("5af56f18e113780b0e5a1360", "46cfc662d3d840bf07db9f500244dc7820453682"); //neon colors
        //moduleAdsManager.getManagerInterstitial().initInterstitialChartboost((Activity) getApplicationContext(),"4f7b433509b6025804000002", "dd2d41b69ac01b80f443f5b6cf06096d457f82bd"); //test

        // moduleAdsManager.getManagerNative().iniNativeAppnext("cdd052e2-9394-407c-99d4-323439dd7398",true);
        // moduleAdsManager.getManagerInterstitial().initInterstitialAppnext("95620754-d968-4e6a-a5da-7ece51d9cacd");
        // moduleAdsManager.getManagerInterstitial().initInterstitialAdmob("ca-app-pub-5322508131338449/2877444211");

    }
}
