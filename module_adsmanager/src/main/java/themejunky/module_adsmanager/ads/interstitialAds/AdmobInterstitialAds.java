package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import themejunky.module_adsmanager.ads.AdsListenerManager;


/**
 * Created by Alin on 4/24/2017.
 */

public class AdmobInterstitialAds extends InterstitialBase {
    public InterstitialAd interstitialAdmob;
    private static AdmobInterstitialAds mInstance = null;
    public static String adUnitId;
    private AdsListenerManager.ListenerAds listenerAds;
    private AdsListenerManager.ListenerLogs listenerLogs;


    public AdmobInterstitialAds(Context context, String adUnitId, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds){
        nContext = context;
        this.listenerLogs = listenerLogs;
        this.listenerAds = listenerAds;
        initAdmobInterstitial(adUnitId);
    }

    public void setAdmobInterMuted(){
        MobileAds.initialize(nContext);
        MobileAds.setAppMuted(true);
    }


    public void initAdmobInterstitial(String adUnitId ) {
        interstitialAdmob = new com.google.android.gms.ads.InterstitialAd(nContext);
        if (adUnitId != null) {
            interstitialAdmob.setAdUnitId(adUnitId);
            listenerLogs.logs("Admob Interstitial: initialized");
            AdListener adListener = new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    interstitialAdmob.loadAd(new AdRequest.Builder().addTestDevice("74df1a5b43f90b50dd8ea33699814380").build());
                    listenerLogs.logs("Admob Interstitial: Closed");
                    listenerLogs.isClosedInterAds();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    listenerLogs.logs("Admob Interstitial: Failed To Load: "+i);
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    listenerLogs.logs("Admob Interstitial: Lef Application");
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    listenerLogs.logs("Admob Interstitial: Opened");

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    listenerLogs.logs("Admob Interstitial: Loaded");
                    if(listenerAds!=null)listenerAds.loadedInterstitialAds();
                }
            };
            interstitialAdmob.setAdListener(adListener);
            requestNewInterstitial();
        }

    }

    public boolean isLoadedAdmob() {
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            return true;
        } else {
            return false;
        }
    }

    public void showAdmobAds() {
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            listenerLogs.logs("Admob Interstitial: show");
            interstitialAdmob.show();
        }
    }


    public void requestNewInterstitial() {
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
        interstitialAdmob.loadAd(adRequest);
    }

    public synchronized static AdmobInterstitialAds getmInstance(Context context, String adUnitId, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds) {
        if (mInstance == null) mInstance = new AdmobInterstitialAds(context,adUnitId,listenerLogs,listenerAds);
        return mInstance;

    }


}