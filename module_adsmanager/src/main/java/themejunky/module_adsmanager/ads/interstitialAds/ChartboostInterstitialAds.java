package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.chartboost.sdk.InPlay.CBInPlay;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Libraries.CBLogging.Level;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Tracking.CBAnalytics;
import com.chartboost.sdk.CBImpressionActivity;
import com.vungle.warren.Vungle;

/**
 * Created by Alin on 4/24/2017.
 */

public class ChartboostInterstitialAds extends ManagerBase {
    public static ChartboostInterstitialAds instance = null;
    //private final Context context;
    private Activity activity;
    private AdsListenerManager.ListenerLogs listenerLogs;

    public ChartboostInterstitialAds(Activity activity, String appId, String appSignature, AdsListenerManager.ListenerLogs listenerLogs){
        this.activity = activity;
        this.listenerLogs = listenerLogs;

        Chartboost.startWithAppId(activity, appId, appSignature);
        Chartboost.onCreate(activity);
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
        showChartboostAds();
    }

    public void showChartboostAds() {
        if (isAdReadyToDisplay()) {
            listenerLogs.logs("Chartboost Inter: showChartboostAds Success");
            Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
        }else {
            listenerLogs.logs("Chartboost Inter: showChartboostAds Failed");
        }
    }


    public boolean isAdReadyToDisplay(){
        //if (delegate.shouldDisplayInterstitial(CBLocation.LOCATION_DEFAULT) && delegate.shouldRequestInterstitial(CBLocation.LOCATION_DEFAULT)) {
            return true;
        //} else {
        //    return false;
        //}
    }


    /*

    public ChartboostDelegate delegate = new ChartboostDelegate() {
        @Override
        public boolean shouldRequestInterstitial(String location) {
            listenerLogs.logs("Chartboost Should request interstitial at " + location + "?");
            return true;
        }

        @Override
        public boolean shouldDisplayInterstitial(String location) {
            listenerLogs.logs("Chartboost Should display interstitial at " + location + "?");
            return true;
        }

        @Override
        public void didCacheInterstitial(String location) {
            listenerLogs.logs("Chartboost Interstitial cached at " + location);
        }

        @Override
        public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {
            listenerLogs.logs("Chartboost Interstitial failed to load at " + location + " with error: " + error.name());
        }

        @Override
        public void willDisplayInterstitial(String location) {
            listenerLogs.logs("Chartboost Will display interstitial at " + location);
        }

        @Override
        public void didDismissInterstitial(String location) {
            listenerLogs.logs("Chartboost Interstitial dismissed at " + location);
        }

        @Override
        public void didCloseInterstitial(String location) {
            listenerLogs.logs("Chartboost Interstitial closed at " + location);
        }

        @Override
        public void didClickInterstitial(String location) {
            listenerLogs.logs("Chartboost Interstitial clicked at " + location );
        }

        @Override
        public void didDisplayInterstitial(String location) {
            listenerLogs.logs("Chartboost Interstitial displayed at " + location);
        }

        @Override
        public void didInitialize() {
            listenerLogs.logs("Chartboost SDK is initialized and ready!");
        }

    };
*/
    public static synchronized ChartboostInterstitialAds getInstance(Activity activity, String appId, String appSignature, AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) {
            return new ChartboostInterstitialAds((Activity) activity, appId, appSignature, listenerLogs);
        } else {
            return instance;
        }
    }



}