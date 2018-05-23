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
    public InterstitialAd interstitialAd;
    private AdsListenerManager.ListenerLogs listenerLogs;
    private boolean isLoaded;
    private _Interface mListenerComeBack;

    public FacebookAdsInterstitial(Context context, String keyFacebook,AdsListenerManager.ListenerLogs listenerLogs){
        this.listenerLogs = listenerLogs;
        this.context = context;
        initFacebookInterstitial(keyFacebook);
        AdSettings.addTestDevice("f5726d6130e7bc96ef669e32ea0ae59e");
    }



    public void initFacebookInterstitial(String keyFacebook) {
        interstitialAd = new InterstitialAd(context, keyFacebook);
        listenerLogs.logs("Facebook Interstitial:  initialized");
        interstitialAd.setAdListener(new InterstitialAdListener() {

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                listenerLogs.logs("Facebook Interstitial: displayed!");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                if(isReloaded){
                    interstitialAd.loadAd();
                }
                listenerLogs.logs("Facebook Interstitial: dismissed!");
                listenerLogs.isClosedInterAds();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                listenerLogs.logs("Faceboook Interstitial error: "+ adError.getErrorMessage());
                mListenerComeBack.mGoBackFromDisplay();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                isLoaded =true;
                if(listenerAds!=null)listenerAds.loadedInterstitialAds();
                listenerLogs.logs("Faceboook Interstitial: is Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                listenerLogs.logs("Faceboook Interstitial: clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        interstitialAd.loadAd();
    }

    public  void showInterstitialFacebook(ManagerBase._Interface nListenerBack) {
        mListenerComeBack = nListenerBack;
        if (interstitialAd !=null && isLoaded) {
            interstitialAd.show();
            listenerLogs.logs("Faceboook Interstitial: is shown");
        } else {
            listenerLogs.logs("Faceboook Interstitial: show failed");


        }

    }
    public boolean isFacebookLoaded(){
        if(interstitialAd!=null /*&& interstitialAd.isAdLoaded()*/){
            Log.d("TestButton", "isFacebookLoaded true;");
            return true;
        }else {
            Log.d("TestButton", "isFacebookLoaded false;");
            return false;
        }
    }

    public synchronized static FacebookAdsInterstitial getInstance(Context context, String keyFacebook,AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new FacebookAdsInterstitial(context,keyFacebook,listenerLogs);
        return mInstance;
    }



}
