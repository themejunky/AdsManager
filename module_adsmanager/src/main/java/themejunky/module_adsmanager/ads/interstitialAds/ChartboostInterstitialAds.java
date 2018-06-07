package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;

/**
 * Created by Alin on 4/24/2017.
 */

public class ChartboostInterstitialAds extends ManagerBase {
    public static ChartboostInterstitialAds instance = null;
    private AdsListenerManager.ListenerLogs listenerLogs;

    public ChartboostInterstitialAds(Activity activity, String appId, String appSignature, AdsListenerManager.ListenerLogs listenerLogs){
        this.listenerLogs = listenerLogs;

        Chartboost.startWithAppId(activity, appId, appSignature);
        Chartboost.onCreate(activity);
        Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
        Chartboost.setActivityCallbacks(true);
        Chartboost.setDelegate(delegate);

    }

    public void showChartboostAds() {
        if (isAdReadyToDisplay()) {
            Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
        }else {
            listenerLogs.logs("Chartboost Inter: showChartboostAds Failed");
        }
    }

    public boolean isAdReadyToDisplay(){
        if (delegate.shouldDisplayInterstitial(CBLocation.LOCATION_DEFAULT) && delegate.shouldRequestInterstitial(CBLocation.LOCATION_DEFAULT)) {
            return true;
        } else {
          return false;
        }
    }

    public ChartboostDelegate delegate = new ChartboostDelegate() {
        @Override
        public boolean shouldRequestInterstitial(String location) {
            listenerLogs.logs("Chartboost Should request interstitial at " + location + "?");
            return super.shouldRequestInterstitial(location);
        }

        @Override
        public boolean shouldDisplayInterstitial(String location) {
            listenerLogs.logs("Chartboost Should display interstitial at " + location + "?");
            return super.shouldDisplayInterstitial(location);
        }

        @Override
        public void didCacheInterstitial(String location) {
            super.didCacheInterstitial(location);
            listenerLogs.logs("Chartboost Interstitial cached at " + location);
        }

        @Override
        public void didFailToLoadInterstitial(String location, CBImpressionError error) {
            super.didFailToLoadInterstitial(location, error);
            listenerLogs.logs("Chartboost Interstitial failed to load at " + location + " with error: " + error.name());

        }

        @Override
        public void willDisplayInterstitial(String location) {
            super.willDisplayInterstitial(location);
            listenerLogs.logs("Chartboost Will display interstitial at " + location);
        }

        @Override
        public void didDismissInterstitial(String location) {
            super.didDismissInterstitial(location);
            listenerLogs.logs("Chartboost Interstitial dismissed at " + location);

        }

        @Override
        public void didCloseInterstitial(String location) {
            super.didCloseInterstitial(location);
            listenerLogs.logs("Chartboost Interstitial closed at " + location);
            listenerLogs.isClosedInterAds();

        }

        @Override
        public void didClickInterstitial(String location) {
            super.didClickInterstitial(location);
            listenerLogs.logs("Chartboost Interstitial click at " + location);
        }

        @Override
        public void didDisplayInterstitial(String location) {
            super.didDisplayInterstitial(location);
            listenerLogs.logs("Chartboost Interstitial display at " + location);
        }

        @Override
        public void didFailToRecordClick(String uri, CBClickError error) {
            super.didFailToRecordClick(uri, error);
            listenerLogs.logs("Chartboost Interstitial didFailToRecordClick: " + error.name());
        }

        @Override
        public void willDisplayVideo(String location) {
            super.willDisplayVideo(location);
            listenerLogs.logs("Chartboost Interstitial willDisplayVideo at " +location);
        }

        @Override
        public void didCacheInPlay(String location) {
            super.didCacheInPlay(location);
            listenerLogs.logs("Chartboost Interstitial didCacheInPlay at " +location);
        }

        @Override
        public void didFailToLoadInPlay(String location, CBImpressionError error) {
            super.didFailToLoadInPlay(location, error);
            listenerLogs.logs("Chartboost Interstitial didFailToLoadInPlay: " + error.name());
        }

        @Override
        public void didInitialize() {
            super.didInitialize();
            listenerLogs.logs("Chartboost SDK is initialized and ready!");
        }
    };

    public static synchronized ChartboostInterstitialAds getInstance(Activity activity, String appId, String appSignature, AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) {
            return new ChartboostInterstitialAds((Activity) activity, appId, appSignature, listenerLogs);
        } else {
            return instance;
        }
    }



}