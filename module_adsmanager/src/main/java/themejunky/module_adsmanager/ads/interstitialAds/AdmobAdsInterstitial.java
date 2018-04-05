package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;


/**
 * Created by Alin on 4/24/2017.
 */

public class AdmobAdsInterstitial {


    private final AdsListenerManager.ListenerLogs listenerLogs;
    public InterstitialAd interstitialAdmob;
    private static AdmobAdsInterstitial mInstance = null;
    public static String adUnitId;
    private List<String> logs = new ArrayList<>();
    private AdsListenerManager.ListenerAds listenerAds;
    private MobileAds mobileAds;
    private boolean IAdmobIsLoaded = false;

    public AdmobAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs){
        this.listenerLogs = listenerLogs;
    }

    public void setAdmobInterMuted(Context context){
        mobileAds.initialize(context);
        mobileAds.setAppMuted(true);
    }


    public void initAdmobInterstitial(Context context, String adUnitId, final AdsListenerManager.ListenerAds listenerAds) {
        this.listenerAds = listenerAds;
        interstitialAdmob = new com.google.android.gms.ads.InterstitialAd(context);
        if (adUnitId != null) {
            interstitialAdmob.setAdUnitId(adUnitId);

            listenerLogs.logs("Admob Inter: initialized");
            AdListener adListener = new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    interstitialAdmob.loadAd(new AdRequest.Builder().addTestDevice("74df1a5b43f90b50dd8ea33699814380").build());
                    listenerLogs.logs("Admob Inter: Closed");
                    listenerLogs.isClosedInterAds();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    listenerLogs.logs("Admob Inter: Failed To Load: "+i);
                    listenerAds.loadInterFailed();
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    listenerLogs.logs("Admob Inter: Lef Application");
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    listenerLogs.logs("Admob Inter: Opened");

                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    listenerLogs.logs("Admob Inter: is Loaded");
                    listenerAds.loadedInterAds();
                    IAdmobIsLoaded = true;

                    if (!isLoadedAdmob()){
                        showAdmobAds();
                    }
                }
            };
            interstitialAdmob.setAdListener(adListener);
            requestNewInterstitial();
        }

    }

    public boolean isLoadedAdmob() {
        listenerLogs.logs("Admob Inter: is really Loaded? "+interstitialAdmob.isLoaded()+ " IAdmobIsLoaded "+IAdmobIsLoaded);
        if (interstitialAdmob!=null && IAdmobIsLoaded) {
            return true;
        } else {
            return false;
        }
    }

    public void showAdmobAds() {
        if (interstitialAdmob!=null && interstitialAdmob.isLoaded()) {
            interstitialAdmob.show();
        }
    }


    public void requestNewInterstitial() {
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
        interstitialAdmob.loadAd(adRequest);
    }

    public synchronized static AdmobAdsInterstitial getmInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new AdmobAdsInterstitial(listenerLogs);
        return mInstance;

    }


}
