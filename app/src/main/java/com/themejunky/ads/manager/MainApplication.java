package com.themejunky.ads.manager;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ModuleAdsManager;


/**
 * Created by Junky2 on 8/2/2017.
 */

public class MainApplication extends Application {
    public List<String> flowAdsInceput = new ArrayList<>();
    public List<String> flowAdsSfarsit = new ArrayList<>();
    public ModuleAdsManager moduleAdsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        flowAdsInceput.add("facebook");
        flowAdsInceput.add("appnext");
        flowAdsInceput.add("admob");

        flowAdsSfarsit.add("facebook");
        flowAdsSfarsit.add("admob");
        flowAdsSfarsit.add("appnext");


        moduleAdsManager = new ModuleAdsManager();
        moduleAdsManager.initManagers(this, true);
        moduleAdsManager.setLogName("wwawq");


        moduleAdsManager.getManagerNative().iniNativeFacebook("833164856890775_838240766383184",false);
        moduleAdsManager.getManagerNative().initNativeAdmob("ca-app-pub-8562466601970101/5081303159",false);
        moduleAdsManager.getManagerNative().iniNativeAppnext("cdd052e2-9394-407c-99d4-323439dd7398",false);

    }
}
