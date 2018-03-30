package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;
import themejunky.module_adsmanager.ads.AdsListenerManager;

public class ApplovinAdsInterstitial extends Activity implements AppLovinAdLoadListener, AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdVideoPlaybackListener {
    private AppLovinInterstitialAdDialog interstitialAd;
    public static ApplovinAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;

    public ApplovinAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    public void initApplovin(Context context, final AdsListenerManager.ListenerAds listenerAds) {
        listenerLogs.logs("AppLovin Inter:  init");
        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( context ), context );
        interstitialAd.setAdLoadListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdDisplayListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdClickListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdVideoPlaybackListener( ApplovinAdsInterstitial.this ); // This will only ever be used if you have video ads enabled.
    }

    public void showAppLovin() {
        listenerLogs.logs("AppLovin Inter:  Show ad");
        Log.d("InfoAds","AppLovinn show ad");
        interstitialAd.show();
    }

    public boolean isLoadedAppLovin() {
         if (interstitialAd != null) {
             listenerLogs.logs("AppLovin Inter:  isLoaded");
            return true;
        } else {
             listenerLogs.logs("AppLovin Inter:  Loaded Failed");
            return false;
        }
    }

    public synchronized static ApplovinAdsInterstitial getInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new ApplovinAdsInterstitial(listenerLogs);
        return instance;
    }


    //
    // Ad Load Listener
    //
    @Override
    public void adReceived(AppLovinAd appLovinAd){
        listenerLogs.logs("AppLovin Inter: adReceived");
    }

    @Override
    public void failedToReceiveAd(int errorCode)    {
        listenerLogs.logs("AppLovin Inter: Failed - "+errorCode);
    }

    //
    // Ad Display Listener
    //
    @Override
    public void adDisplayed(AppLovinAd appLovinAd){
        listenerLogs.logs("AppLovin Inter: adDisplayed");
    }

    @Override
    public void adHidden(AppLovinAd appLovinAd){
        listenerLogs.logs("AppLovin Inter: Hidden");
    }

    //
    // Ad Click Listener
    //
    @Override
    public void adClicked(AppLovinAd appLovinAd)
    {
        Log.d("InfoAds","AppLovinn Interstitial Clicked" );
    }

    //
    // Ad Video Playback Listener
    //
    @Override
    public void videoPlaybackBegan(AppLovinAd appLovinAd){
        listenerLogs.logs("AppLovin Inter: Video Started");
    }

    @Override
    public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed){
        listenerLogs.isClosedInterAds();
        listenerLogs.logs("AppLovin Inter: Video Ended");
    }



}
