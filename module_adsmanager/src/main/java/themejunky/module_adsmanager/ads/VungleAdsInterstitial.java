package themejunky.module_adsmanager.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.vungle.publisher.AdConfig;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

/**
 * Created by Junky2 on 12/8/2017.
 */

public class VungleAdsInterstitial {
    private  AdsListenerManager.ListenerLogs listenerLogs;
    private static VungleAdsInterstitial mInstance = null;
    public VunglePub vunglePub;
    public    AdConfig adConfig;

    public VungleAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    public void initVungleInterstitial(Context context, String app_id, final AdsListenerManager.ListenerAds listenerAds){
      vunglePub = VunglePub.getInstance();

        vunglePub.init(context, app_id);

        adConfig= new AdConfig();
        adConfig.setSoundEnabled(false);

        vunglePub.setEventListeners(new EventListener() {
            @Override
            public void onAdEnd(boolean b, boolean b1) {
                listenerLogs.logs("Vungle Inter: Closed");
                listenerLogs.isClosedInterAds();
            }

            @Override
            public void onAdStart() {
                listenerLogs.logs("Vungle Inter: Opened");
            }

            @Override
            public void onAdUnavailable(String s) {
                listenerLogs.logs("Vungle Inter: Failed To Load: "+s);
                listenerAds.loadInterFailed();
            }

            @Override
            public void onAdPlayableChanged(boolean b) {
                if(b){
                    listenerLogs.logs("Admob Inter: is Loaded");
                    listenerAds.loadedInterAds();
                }else {
                    listenerLogs.logs("Admob Inter: is NOT Loaded");
                }
            }
        });

    }

    public void showVungleAds() {
        if (vunglePub!=null ) {
            vunglePub.playAd();
        }
    }

    public synchronized static VungleAdsInterstitial getmInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new VungleAdsInterstitial(listenerLogs);
        return mInstance;

    }
}
