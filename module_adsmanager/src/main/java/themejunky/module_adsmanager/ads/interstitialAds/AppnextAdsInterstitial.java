package themejunky.module_adsmanager.ads.interstitialAds;


import android.content.Context;
import android.util.Log;


import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;


/**
 * Created by Alin on 5/21/2017.
 */

public class AppnextAdsInterstitial extends ManagerBase {
    public static AppnextAdsInterstitial instance = null;
    private final Context context;
    public Interstitial interstitialAppnext;
    private AdsListenerManager.ListenerLogs listenerLogs;

    public AppnextAdsInterstitial(Context context,String keyAppext,AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
        this.context = context;
        initAppnext(keyAppext);

    }

    //initAppnext(activity,"8106d659-a20b-4640-943b-d6b0aab18d08");
    public void initAppnext(String placementID) {
        Log.d("aew3we","1");
        Appnext.init(context);
        Log.d("aew3we","2");
        interstitialAppnext = new Interstitial(context, placementID);
        Log.d("aew3we","3");
        listenerLogs.logs("Appnext inter: initialized");
        Log.d("aew3we","4");
        interstitialAppnext.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                Log.d("aew3we","5");
                listenerLogs.logs("Appnext inter: Closed");
                listenerLogs.isClosedInterAds();
            }
        });
        interstitialAppnext.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String s) {
                listenerLogs.logs("Appnext inter error: " + s.toString());
            }
        });
        interstitialAppnext.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String s) {
                if(listenerAds!=null)listenerAds.loadedInterstitialAds();
                listenerLogs.logs("Appnext inter: is Loaded");
            }
        });
        Log.d("aew3we","5");
        interstitialAppnext.setBackButtonCanClose(true);
        Log.d("aew3we","6");
        interstitialAppnext.setMute(true);
        Log.d("aew3we","7");
        interstitialAppnext.setAutoPlay(true);
        Log.d("aew3we","8");
        interstitialAppnext.loadAd();
        Log.d("aew3we","9");

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

    public synchronized static AppnextAdsInterstitial getInstance(Context context,String keyAppext,AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new AppnextAdsInterstitial(context,keyAppext,listenerLogs);
        return instance;
    }
}
