package themejunky.module_adsmanager;

import android.content.Context;
import android.util.Log;


import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;
import themejunky.module_adsmanager.managers.ManagerInterstitial;
import themejunky.module_adsmanager.managers.ManagerNative;


/**
 * Created by Junky2 on 4/19/2018.
 */

public class ModuleAdsManager extends ManagerBase implements AdsListenerManager.ListenerLogs{

    private ManagerNative managerNative;
    private ManagerInterstitial managerInterstitial;

    /**
     *
     * @param nContext
     * @param showAds
     * @param reloaded
     */
    public void initManagers(Context nContext, boolean showAds,boolean reloaded){
        if (showAds){
            isReloaded = reloaded;
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
       if(listenerAds!=null) listenerAds = nListenerAds;
    }

    @Override
    public void isClosedInterAds() {
        if(listenerAds!=null)listenerAds.afterInterstitialIsClosed(action);

    }
}
