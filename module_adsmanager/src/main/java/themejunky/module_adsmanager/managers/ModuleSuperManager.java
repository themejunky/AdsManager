package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;


import themejunky.module_adsmanager.ads.AdsListenerManager;


/**
 * Created by Junky2 on 4/19/2018.
 */

public class ModuleSuperManager extends ManagerBase implements AdsListenerManager.ListenerLogs{

    private ManagerNative managerNative;
    private ManagerInterstitial managerInterstitial;

    public void initManagers(Context nContext, boolean showAds){
        if (showAds){
            managerNative = ManagerNative.getInstance(nContext);
            managerInterstitial = ManagerInterstitial.getInstance(nContext);
        }
    }

    public ManagerNative getManagerNative() {
        return managerNative;
    }

    public ManagerInterstitial getManagerInterstitial() {
        return managerInterstitial;
    }

    public void setLogName(String nNameLog){
        Log.d("TestLogs","1 "+nameLogs);
        nameLogs = nNameLog;
        Log.d("TestLogs","2 "+nameLogs);
    }



    public void setListenerAds(AdsListenerManager.ListenerAds nListenerAds){
        listenerAds = nListenerAds;
        if(listenerAds==null){
            Log.d("TestLogs","setListenerAds este null");
        }else {
            Log.d("TestLogs","setListenerAds nu este null");
        }
    }

    @Override
    public void isClosedInterAds() {
        if(listenerAds!=null)listenerAds.afterInterstitialIsClosed(action);
        if(listenerAds==null){
            Log.d("TestLogs","isClosedInterAds este null");
        }else {
            Log.d("TestLogs","isClosedInterAds nu este null");
        }

    }
}
