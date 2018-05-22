package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import io.display.sdk.Controller;
import io.display.sdk.EventListener;
import io.display.sdk.ads.supers.RewardedVideoAd;
import themejunky.module_adsmanager.ads.AdsListenerManager;

/**
 * Created by Junky2 on 5/15/2018.
 */

public class DisplayInterstitialAds {

    public static DisplayInterstitialAds instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    private final Context context;
    public Controller ctrl;
    boolean isDisplayLoaded;
    public boolean isNoAds;

    public DisplayInterstitialAds(Context context, String appId,AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs =listenerLogs;
        this.context = context;
        init(context, appId);
    }

    public void init(final Context context, String appId) {
        ctrl = io.display.sdk.Controller.getInstance();
        ctrl.init(context, appId);
        listenerLogs.logs("Display.Io Intersitial: isInitialized: " + ctrl.isInitialized());
        ctrl.setEventListener(new EventListener() {
            @Override
            public void onInit() {
                super.onInit();
                listenerLogs.logs("Display.Io Intersitial: onInit");
            }

            @Override
            public void onInitError(String msg) {
                super.onInitError(msg);
                listenerLogs.logs("Display.Io Intersitial: error - "+msg);
                isNoAds =true;
            }

            @Override
            public void onAdShown(String placementId) {
                super.onAdShown(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdShown");
            }

            @Override
            public void onAdFailedToShow(String placementId) {
                super.onAdFailedToShow(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdFailedToShow");
                isNoAds =true;
            }

            @Override
            public void onNoAds(String placementId) {
                super.onNoAds(placementId);
                listenerLogs.logs("Display.Io Intersitial: onNoAds");
                isNoAds  =true;
            }

            @Override
            public void onAdCompleted(String placementId) {
                super.onAdCompleted(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdCompleted");
            }

            @Override
            public void onAdClose(String placementId) {
                super.onAdClose(placementId);
                listenerLogs.isClosedInterAds();
                listenerLogs.logs("Display.Io Intersitial: onAdClose");
            }

            @Override
            public void onAdClick(String placementId) {
                super.onAdClick(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdClick");
            }

            @Override
            public void onAdReady(String placementId) {
                super.onAdReady(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdReady");
            }

            @Override
            public void onRewardedVideoCompleted(String placementId, RewardedVideoAd.Reward reward) {
                super.onRewardedVideoCompleted(placementId, reward);
                listenerLogs.logs("Display.Io Intersitial: onRewardedVideoCompleted");
            }

            @Override
            public void inactivate() {
                super.inactivate();
                listenerLogs.logs("Display.Io Intersitial: inactivate");
                isNoAds  =true;
            }

            @Override
            public void activate() {
                super.activate();
                listenerLogs.logs("Display.Io Intersitial: activate");
            }

            @Override
            public Boolean isActive() {
                listenerLogs.logs("Display.Io Intersitial: isActive");
                return super.isActive();

            }
        });

    }




    public void showAd(Context context, String placementId) {
        if (ctrl != null) {
            ctrl.showAd(context, placementId);
        }
    }



    public static synchronized DisplayInterstitialAds getInstance(Context context, String appId,AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) {
            return new DisplayInterstitialAds(context, appId,listenerLogs);
        } else {
            return instance;
        }
    }

}
