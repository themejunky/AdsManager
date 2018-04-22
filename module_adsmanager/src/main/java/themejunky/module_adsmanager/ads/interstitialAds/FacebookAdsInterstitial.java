package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.managers.ManagerBase;


public class FacebookAdsInterstitial extends ManagerBase {
    public static FacebookAdsInterstitial mInstance = null;
    private final Context context;
    private InterstitialAd interstitialAd;
    private AdsListenerManager.ListenerLogs listenerLogs;

    public FacebookAdsInterstitial(Context context, String keyFacebook,AdsListenerManager.ListenerLogs listenerLogs){
        this.listenerLogs = listenerLogs;
        this.context = context;
        initFacebookInterstitial(keyFacebook);
        AdSettings.addTestDevice("f5726d6130e7bc96ef669e32ea0ae59e");
    }



    public void initFacebookInterstitial(String keyFacebook) {
        Log.d("oopo","1");
        interstitialAd = new InterstitialAd(context, keyFacebook);
        Log.d("oopo","2");
        listenerLogs.logs("Facebook:  initialized");
        Log.d("oopo","3");
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
                Log.d("oopo","Faceboook error: "+ adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if(listenerAds!=null)listenerAds.loadedInterstitialAds();
                listenerLogs.logs("Faceboook: is Loaded");
                Log.d("oopo","Faceboook: is Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                listenerLogs.logs("Faceboook: clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        Log.d("oopo","4");
        interstitialAd.loadAd();
        Log.d("oopo","5");
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

    public synchronized static FacebookAdsInterstitial getInstance(Context context, String keyFacebook,AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new FacebookAdsInterstitial(context,keyFacebook,listenerLogs);
        return mInstance;
    }



}
