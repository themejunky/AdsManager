package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinSdk;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;

/**
 * Created by Alin on 4/24/2017.
 */

public class ApplovinInterstitialAds extends ManagerBase {
    public static ApplovinInterstitialAds instance = null;
    private AppLovinAd loadedAd;
    private boolean isAppLovinLoaded = false;
    private Activity activity;
    private AdsListenerManager.ListenerLogs listenerLogs;
    private AppLovinInterstitialAdDialog interstitialAd;

    public ApplovinInterstitialAds(Activity activity, AdsListenerManager.ListenerLogs listenerLogs){
        this.activity = activity;
        this.listenerLogs = listenerLogs;
        init();
    }


    public void init(){
        AppLovinSdk.initializeSdk(activity);
        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance(activity), activity);
        interstitialAd.setAdDisplayListener(new AppLovinAdDisplayListener() {
            @Override
            public void adDisplayed(AppLovinAd appLovinAd) {
                listenerLogs.logs("Applovin Inter: adDisplayed");
            }

            @Override
            public void adHidden(AppLovinAd appLovinAd) {
                listenerLogs.logs("Applovin Inter: adHidden");
                listenerLogs.isClosedInterAds();
            }
        });

    }

    public void showApplovinAds() {
        if (isAdReadyToDisplay()) {
            listenerLogs.logs("Applovin Inter: showApplovinAds Success");
            interstitialAd.showAndRender( loadedAd );
        }else {
            listenerLogs.logs("Applovin Inter: showApplovinAds Failed");
        }

    }




    public boolean isAdReadyToDisplay(){
        // Load an Interstitial Ad
        AppLovinSdk.getInstance(activity).getAdService().loadNextAd( AppLovinAdSize.INTERSTITIAL, new AppLovinAdLoadListener()
        {
            @Override
            public void adReceived(AppLovinAd ad)
            {
                loadedAd = ad;
                isAppLovinLoaded = true;
                listenerLogs.logs("Applovin Inter: adReceived Success");
            }

            @Override
            public void failedToReceiveAd(int errorCode)
            {
                isAppLovinLoaded = false;
                listenerLogs.logs("Applovin Inter: adReceived Failed "+errorCode);
                // Look at AppLovinErrorCodes.java for list of error codes.
            }
        } );
        return isAppLovinLoaded;
    }





    public static synchronized ApplovinInterstitialAds getInstance(Activity activity, AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) {
            return new ApplovinInterstitialAds((Activity) activity,listenerLogs);
        } else {
            return instance;
        }
    }



}