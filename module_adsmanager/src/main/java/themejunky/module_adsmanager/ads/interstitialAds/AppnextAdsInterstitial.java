package themejunky.module_adsmanager.ads.interstitialAds;


import android.content.Context;


import com.appnext.ads.interstitial.Interstitial;
import com.appnext.ads.interstitial.InterstitialConfig;
import com.appnext.base.Appnext;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;

import themejunky.module_adsmanager.ads.AdsListenerManager;


/**
 * Created by Alin on 5/21/2017.
 */

public class AppnextAdsInterstitial {


    public static AppnextAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;

    public Interstitial interstitialAppnext;


    public AppnextAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    //initAppnext(activity,"8106d659-a20b-4640-943b-d6b0aab18d08");
    public void initAppnext(Context context, String placementID, final AdsListenerManager.ListenerAds listenerAds) {
        Appnext.init(context);
        interstitialAppnext = new Interstitial(context, placementID);
        listenerLogs.logs("Appnext inter: initialized");
        interstitialAppnext.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                listenerLogs.logs("Appnext inter: Closed");
                listenerLogs.isClosedInterAds();
            }
        });
        interstitialAppnext.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String s) {
                listenerLogs.logs("Appnext inter error: " + s.toString());
                listenerAds.loadInterFailed();
            }
        });
        interstitialAppnext.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String s) {
                listenerAds.loadedInterAds();
                listenerLogs.logs("Appnext inter: is Loaded");
            }
        });

        interstitialAppnext.setMute(true);
        interstitialAppnext.setAutoPlay(true);
        interstitialAppnext.loadAd();
        interstitialAppnext.setMute(true);
        interstitialAppnext.setAutoPlay(true);

    }

    public void showAppNext() {
        if (interstitialAppnext != null && interstitialAppnext.isAdLoaded()) {
            interstitialAppnext.showAd();
        }
    }

    public boolean isLoadedAppNext() {
        if (interstitialAppnext != null && interstitialAppnext.isAdLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized static AppnextAdsInterstitial getInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new AppnextAdsInterstitial(listenerLogs);
        return instance;
    }
}
