package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

public class ApplovinAdsInterstitial extends Activity implements
        AppLovinAdLoadListener, AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdVideoPlaybackListener {
    private AppLovinInterstitialAdDialog interstitialAd;
    public static ApplovinAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;

    public ApplovinAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }


    public void initApplovin(Context context, String placementID, final AdsListenerManager.ListenerAds listenerAds) {
        listenerLogs.logs("app loving init");
        AppLovinSdk.initializeSdk( getApplicationContext() );
        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( this ), this );
        interstitialAd.setAdLoadListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdDisplayListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdClickListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdVideoPlaybackListener( ApplovinAdsInterstitial.this ); // This will only ever be used if you have video ads enabled.
    }


    public void showAppLovin() {
        interstitialAd.show();
    }

    public boolean isLoadedAppLovin() {
        //if (interstitialAd != null && interstitialAd.isAdLoaded()) {
        if (interstitialAd != null) {
            Log.d("InfoAds","isLoadedAppLovin is not null");
            return true;
        } else {
            Log.d("InfoAds","isLoadedAppLovin is null");
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
    public void adReceived(AppLovinAd appLovinAd)
    {
        //log( "Interstitial loaded" );
    }

    @Override
    public void failedToReceiveAd(int errorCode)
    {
        // Look at AppLovinErrorCodes.java for list of error codes
        //log( "Interstitial failed to load with error code " + errorCode );
    }

    //
    // Ad Display Listener
    //
    @Override
    public void adDisplayed(AppLovinAd appLovinAd)
    {
        //log( "Interstitial Displayed" );
    }

    @Override
    public void adHidden(AppLovinAd appLovinAd)
    {
        //log( "Interstitial Hidden" );
    }

    //
    // Ad Click Listener
    //
    @Override
    public void adClicked(AppLovinAd appLovinAd)
    {
        //log( "Interstitial Clicked" );
    }

    //
    // Ad Video Playback Listener
    //
    @Override
    public void videoPlaybackBegan(AppLovinAd appLovinAd)
    {
        //log( "Video Started" );
    }

    @Override
    public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed)
    {
        //log( "Video Ended" );
    }



}
