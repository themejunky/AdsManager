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
    public ModuleAdsManager Mam;
    @Override
    public void onCreate() {
        super.onCreate();
        flowAdsInceput.add("facebook");
        flowAdsInceput.add("appnext");
        flowAdsInceput.add("admob");

        flowAdsSfarsit.add("facebook");
        flowAdsSfarsit.add("admob");
        flowAdsSfarsit.add("appnext");




    }
}
