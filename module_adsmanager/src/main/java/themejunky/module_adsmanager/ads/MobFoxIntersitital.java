package themejunky.module_adsmanager.ads;

import android.content.Context;

import com.mobfox.sdk.interstitialads.InterstitialAd;
import com.mobfox.sdk.interstitialads.InterstitialAdListener;

/**
 * Created by Junky2 on 11/20/2017.
 */

public class MobFoxIntersitital {

    private final AdsListenerManager.ListenerLogs listenerLogs;
    public InterstitialAd interstitial;
    InterstitialAdListener listener;

    private static MobFoxIntersitital mInstance = null;

    public MobFoxIntersitital(AdsListenerManager.ListenerLogs listenerLogs){
        this.listenerLogs = listenerLogs;
    }


    public void initMobFoxInterstitial(Context context,String id,final AdsListenerManager.ListenerAds listenerAds){
        interstitial = new InterstitialAd(context);

        InterstitialAdListener listener = new InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(InterstitialAd interstitial) {
                listenerLogs.logs("Mobfox Inter: Loaded");
                listenerAds.loadedInterAds();
              //  interstitial.show();
            }
            @Override
            public void onInterstitialFailed(InterstitialAd interstitial, Exception e) {
                listenerLogs.logs("Mobfox Inter: Failed: " + e.getMessage());
                listenerAds.loadInterFailed();
            }
            @Override
            public void onInterstitialClosed(InterstitialAd interstitial) {
                listenerLogs.logs("Mobfox Inter: Closed");
                listenerLogs.isClosedInterAds();
            }
            @Override
            public void onInterstitialFinished() {
                listenerLogs.logs("Mobfox Inter: Finished");
            }
            @Override
            public void onInterstitialClicked(InterstitialAd interstitial) {
                listenerLogs.logs("Mobfox Inter: Clicked");
            }
            @Override
            public void onInterstitialShown(InterstitialAd interstitial) {
                listenerLogs.logs("Mobfox Inter: Shown");
            }
        };

        interstitial.setListener(listener);
        interstitial.setInventoryHash(id);
        interstitial.load();

    }






    public synchronized static MobFoxIntersitital getmInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new MobFoxIntersitital(listenerLogs);
        return mInstance;

    }
}
