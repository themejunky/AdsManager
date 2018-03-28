package themejunky.module_adsmanager.ads.interstitialAds;

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
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;
import themejunky.module_adsmanager.ads.AdsListenerManager;

public class ApplovinAdsInterstitial extends AppLovinAdStatusActivity implements
        AppLovinAdLoadListener, AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdVideoPlaybackListener {

    private AppLovinInterstitialAdDialog interstitialAd;
    public static ApplovinAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    private static final String KEY_SHARED_PREFERENCES_NAMESPACE = "com.applovin.apps.demo.shared_preferences";
    private static final String KEY_PROMPTED_CONFIG_FLAGS        = "com.applovin.apps.demo.shared_preferences.prompted_config_flags";

    public ApplovinAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }


    public void initApplovin(Context context, String placementID, final AdsListenerManager.ListenerAds listenerAds) {
        AppLovinSdk.initializeSdk( getApplicationContext() );
        AppLovinSdk.getInstance( getApplicationContext() ).getSettings().setTestAdsEnabled( true );

        // Warn user if SDK key is invalid
        boolean isLegitSdkKey = checkSdkKey();

        // Prompt to add config flags if SDK key is legit
        if ( isLegitSdkKey )
        {
            maybePromptConfigFlags();
        }

        interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( this ), this );

        interstitialAd.setAdLoadListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdDisplayListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdClickListener( ApplovinAdsInterstitial.this );
        interstitialAd.setAdVideoPlaybackListener( ApplovinAdsInterstitial.this ); // This will only ever be used if you have video ads enabled.

        /*
        Applovin.init(context);
        //interstitialApplovin = new Interstitial(context, placementID);
        listenerLogs.logs("Applovin inter: initialized");
        interstitialApplovin.setOnAdClosedCallback(new OnAdClosed() {
            @Override
            public void onAdClosed() {
                listenerLogs.logs("Applovin inter: Closed");
                listenerLogs.isClosedInterAds();
            }
        });
        interstitialApplovin.setOnAdErrorCallback(new OnAdError() {
            @Override
            public void adError(String s) {
                listenerLogs.logs("Applovin inter error: " + s.toString());
                listenerAds.loadInterFailed();
            }
        });
        interstitialApplovin.setOnAdLoadedCallback(new OnAdLoaded() {
            @Override
            public void adLoaded(String s) {
                listenerAds.loadedInterAds();
                listenerLogs.logs("Applovin inter: is Loaded");
            }
        });


        interstitialApplovin.setBackButtonCanClose(true);
        interstitialApplovin.setMute(true);
        interstitialApplovin.setAutoPlay(true);
        interstitialApplovin.loadAd();
*/

    }

    private boolean checkSdkKey()
    {
        final String sdkKey = AppLovinSdk.getInstance( getApplicationContext() ).getSdkKey();
        if ( "YOUR_SDK_KEY".equalsIgnoreCase( sdkKey ) )
        {
            new AlertDialog.Builder( this )
                    .setTitle( "ERROR" )
                    .setMessage( "Please update your sdk key in the manifest file." )
                    .setCancelable( false )
                    .setNeutralButton( "OK", null )
                    .show();

            return false;
        }

        return true;
    }

    private void maybePromptConfigFlags()
    {
        final SharedPreferences sharedPrefs = getSharedPreferences( KEY_SHARED_PREFERENCES_NAMESPACE, Context.MODE_PRIVATE );
        if ( !sharedPrefs.getBoolean( KEY_PROMPTED_CONFIG_FLAGS, false ) )
        {
            new AlertDialog.Builder( this )
                    .setTitle( "IF you are using Android SDK 6.4.0 or above" )
                    .setMessage( "In your manifest file, please set the \"android:configChanges\" attribute for com.applovin.adview.AppLovinInterstitialActivity to be \"orientation|screenSize\"" )
                    .setCancelable( false )
                    .setNeutralButton( "OK", null )
                    .show();

            sharedPrefs.edit().putBoolean( KEY_PROMPTED_CONFIG_FLAGS, true ).apply();
        }
    }

    public void showAppLovin() {
        interstitialAd.show();
        /*
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.showAd();
        }
        */
    }

    public boolean isLoadedAppLovin() {
        //if (interstitialAd != null && interstitialAd.isAdLoaded()) {
        if (interstitialAd != null) {
            Log.d("InfoAds","isLoadedAppLovin true");
            return true;
        } else {
            Log.d("InfoAds","isLoadedAppLovin true");
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
        log( "Interstitial loaded" );
    }

    @Override
    public void failedToReceiveAd(int errorCode)
    {
        // Look at AppLovinErrorCodes.java for list of error codes
        log( "Interstitial failed to load with error code " + errorCode );
    }

    //
    // Ad Display Listener
    //
    @Override
    public void adDisplayed(AppLovinAd appLovinAd)
    {
        log( "Interstitial Displayed" );
    }

    @Override
    public void adHidden(AppLovinAd appLovinAd)
    {
        log( "Interstitial Hidden" );
    }

    //
    // Ad Click Listener
    //
    @Override
    public void adClicked(AppLovinAd appLovinAd)
    {
        log( "Interstitial Clicked" );
    }

    //
    // Ad Video Playback Listener
    //
    @Override
    public void videoPlaybackBegan(AppLovinAd appLovinAd)
    {
        log( "Video Started" );
    }

    @Override
    public void videoPlaybackEnded(AppLovinAd appLovinAd, double percentViewed, boolean wasFullyViewed)
    {
        log( "Video Ended" );
    }



}
