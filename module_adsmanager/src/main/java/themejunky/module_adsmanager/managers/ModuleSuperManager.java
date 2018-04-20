package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;


/**
 * Created by Junky2 on 4/19/2018.
 */

public class ModuleSuperManager extends ManagerBase implements AdsListenerManager.ListenerLogs{

    private ManagerNative managerNative;
    private ManagerInterstitial managerInterstitial;
    private AdsListenerManager.ListenerAds listenerAds;

    public ModuleSuperManager(Context nContext, boolean showAds) {
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
        nameLogs = nNameLog;
    }



    public void setListenerAds(AdsListenerManager.ListenerAds nListenerAds){
        listenerAds = nListenerAds;
    }

    @Override
    public void isClosedInterAds() {
        listenerAds.afterInterstitialIsClosed(action);
    }
}
