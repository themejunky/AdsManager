package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import io.display.sdk.Controller;
import io.display.sdk.EventListener;
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
                listenerLogs.logs("Display.Io Intersitial error: "+ msg.toString());
            }

            @Override
            public void onAdShown(String placementId) {
                super.onAdShown(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdShown");
            }

            @Override
            public void onAdClose(String placementId) {
                super.onAdClose(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdClose");
                listenerLogs.isClosedInterAds();
            }

            @Override
            public void onAdReady(String placementId) {
                super.onAdReady(placementId);
                listenerLogs.logs("Display.Io Intersitial: onAdReady");
            }

            @Override
            public void inactivate() {
                super.inactivate();
                listenerLogs.logs("Display.Io Intersitial: inactivate");
            }

            @Override
            public void activate() {
                super.activate();
                listenerLogs.logs("Display.Io Intersitial: activate");
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
