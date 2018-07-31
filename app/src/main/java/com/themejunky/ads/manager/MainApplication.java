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

        flowAdsInceput.add("adcolony");

        moduleAdsManager = new ModuleAdsManager(this, true);
        moduleAdsManager.setLogName("TestButton");

    }
}
