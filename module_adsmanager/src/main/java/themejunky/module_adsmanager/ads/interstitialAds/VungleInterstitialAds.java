package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vungle.warren.AdConfig;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.PlayAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.error.VungleException;
import com.vungle.warren.presenter.AdvertisementPresenter;

import java.util.Arrays;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;

/**
 * Created by Junky2 on 5/24/2018.
 */

public class VungleInterstitialAds extends ManagerBase {
    private final String app_id;
    private AdsListenerManager.ListenerLogs listenerLogs;
    private static VungleInterstitialAds mInstance = null;
    public AdConfig adConfig;
    private String placementId;
    private PlayAdCallback playAdCallback;
    private boolean isVunleLoaded;

    public VungleInterstitialAds(Context context, String app_id, List<String> placement_collection, AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
        this.app_id = app_id;
        initVungleInterstitial(context, app_id, placement_collection);


    }



    public void initVungleInterstitial(Context context, String app_id, List<String> placement_collection) {
        placementId = placement_collection.get(0);
        adConfig = new AdConfig();
        adConfig.setAutoRotate(false);
        adConfig.setMuted(true);
        if(!Vungle.isInitialized()){
            Vungle.init(placement_collection, app_id, context, new InitCallback() {
                @Override
                public void onSuccess() {
                    listenerLogs.logs("Vungle Inter: onSuccess :" + Vungle.getValidPlacements());
                    loadVungle();
                }

                @Override
                public void onError(Throwable throwable) {
                    listenerLogs.logs("Vungle Inter: onError: " + throwable.getMessage());
                }

                @Override
                public void onAutoCacheAdAvailable(String s) {
                    listenerLogs.logs("Vungle Inter: onAutoCacheAdAvailable");
                }
            });
            listenerLogs.logs("Vungle Inter: placementId" + placementId);

        }

        playAdCallback = new PlayAdCallback() {
            @Override
            public void onAdStart(String s) {
                listenerLogs.logs("Vungle Inter: is onAdStart");
            }

            @Override
            public void onAdEnd(String s, boolean b, boolean b1) {
                listenerLogs.logs("Vungle Inter: is onAdEnd");
                listenerLogs.isClosedInterAds();
            }

            @Override
            public void onError(String s, Throwable throwable) {
                listenerLogs.logs("Vungle Inter: onError: " + throwable.getMessage());
            }
        };

    }

    public void loadVungle() {
        if (placementId != null) {
            Vungle.loadAd(placementId, new LoadAdCallback() {
                @Override
                public void onAdLoad(String s) {
                    listenerLogs.logs("Vungle Inter: is Loaded");
                    listenerAds.loadedInterstitialAds();
                    isVunleLoaded = true;
                }

                @Override
                public void onError(String s, Throwable throwable) {
                    listenerLogs.logs("Vungle Inter: onError: " + throwable.getMessage());
                }
            });
        }else {
            listenerLogs.logs("Vungle Inter: is app_id null");
        }
    }


    public boolean isReadyToShow(){
        if (Vungle.canPlayAd(placementId)) {
            listenerLogs.logs("Vungle Inter: isReadyToShow");
           return true;

        }else {
            listenerLogs.logs("Vungle Inter: is not isReadyToShow");
            return false;

        }
    }


    public void showVungleAds() {
        if (Vungle.canPlayAd(placementId)) {
            listenerLogs.logs("Vungle Inter: showVungleAds");
            Vungle.playAd(placementId, adConfig, playAdCallback);
        }else {
            listenerLogs.logs("Vungle Inter: showVungleAds Failed");
        }
    }
    public boolean isVungleLoaded(){
        if(isVunleLoaded){
            return true;
        }else {
            return false;
        }
    }

    public synchronized static VungleInterstitialAds getmInstance(Context context, String app_id, List<String> placement_collection, AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null)
            mInstance = new VungleInterstitialAds(context, app_id, placement_collection, listenerLogs);
        return mInstance;

    }
}
