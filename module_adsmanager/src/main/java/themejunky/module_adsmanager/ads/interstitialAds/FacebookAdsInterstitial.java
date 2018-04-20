package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import themejunky.module_adsmanager.ads.AdsListenerManager;


public class FacebookAdsInterstitial {
    public static FacebookAdsInterstitial mInstance = null;
    private InterstitialAd interstitialAd;
    private AdsListenerManager.ListenerAds listenerAds;
    private AdsListenerManager.ListenerLogs listenerLogs;

    public FacebookAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds){
        this.listenerLogs = listenerLogs;
        this.listenerAds = listenerAds;
        AdSettings.addTestDevice("f5726d6130e7bc96ef669e32ea0ae59e");
    }



    public void initFacebookInterstitial(Context activity, String keyFacebook) {
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

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if(listenerAds!=null)listenerAds.loadedInterstitialAds();
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

    public synchronized static FacebookAdsInterstitial getmInstance(AdsListenerManager.ListenerLogs listenerLogs,AdsListenerManager.ListenerAds listenerAds) {
        if (mInstance == null) mInstance = new FacebookAdsInterstitial(listenerLogs,listenerAds);
        return mInstance;
    }



}
