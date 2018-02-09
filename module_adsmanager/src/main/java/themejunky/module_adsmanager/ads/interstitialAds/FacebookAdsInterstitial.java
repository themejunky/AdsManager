package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.Activity;
import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import themejunky.module_adsmanager.ads.AdsListenerManager;


public class FacebookAdsInterstitial {

    private AdsListenerManager.ListenerLogs listenerLogs;
    public static FacebookAdsInterstitial mInstance = null;
    private InterstitialAd interstitialAd;
    private AdsListenerManager.ListenerAds listenerAds;

    public FacebookAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs){
        this.listenerLogs = listenerLogs;
    }



    public void initFacebookInterstitial(Context activity, String keyFacebook, final AdsListenerManager.ListenerAds listenerAds) {
        this.listenerAds = listenerAds;
        AdSettings.addTestDevice("f755429e799a9291a0e305d065db6326");

        interstitialAd = new InterstitialAd(activity, keyFacebook);
        listenerLogs.logs("Facebook:  initialized");
        interstitialAd.setAdListener(new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                listenerLogs.logs("Facebook: displayed!");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                interstitialAd.loadAd();
                listenerLogs.logs("Facebook : dismissed!");
                listenerLogs.isClosedInterAds();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                listenerLogs.logs("Faceboook error: "+ adError.getErrorMessage());
                listenerAds.loadInterFailed();

            }

            @Override
            public void onAdLoaded(Ad ad) {
                listenerAds.loadedInterAds();
                listenerLogs.logs("Faceboook: is Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                listenerLogs.logs("Faceboook: clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        interstitialAd.loadAd();

    }

    public  void showInterstitialFacebook() {

        if (interstitialAd !=null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
            listenerLogs.logs("Faceboook: is shown");
        } else {
            listenerLogs.logs("Faceboook: show failed");


        }

    }
    public boolean isFacebookLoaded(){
        if(interstitialAd!=null && interstitialAd.isAdLoaded()){
            return true;
        }else {
            return false;
        }
    }

    public synchronized static FacebookAdsInterstitial getmInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new FacebookAdsInterstitial(listenerLogs);
        return mInstance;
    }



}
