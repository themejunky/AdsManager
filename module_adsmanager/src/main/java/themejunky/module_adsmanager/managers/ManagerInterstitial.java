package themejunky.module_adsmanager.managers;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;

import java.util.Arrays;
import java.util.List;

import themejunky.module_adsmanager.ads.interstitialAds.AdmobInterstitialAds;
import themejunky.module_adsmanager.ads.interstitialAds.AppnextAdsInterstitial;
import themejunky.module_adsmanager.ads.interstitialAds.FacebookAdsInterstitial;
import themejunky.module_adsmanager.utils.ConstantsAds;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerInterstitial extends ManagerBase implements ManagerBase._Interface {
    private static ManagerInterstitial instance = null;
    private final Activity mContext;
    private AdmobInterstitialAds admobInterstitialAds;
    private AppnextAdsInterstitial appnextAdsInterstitial;
    private FacebookAdsInterstitial facebookAdsInterstitial;
    private String placementId;
    public static boolean isNoAdsFacebook;
    public static boolean isNoAdsDisplay;


    public ManagerInterstitial(Activity nContext) {
        this.mContext = nContext;
    }

    public void initInterstitialAdmob(String keyInterstitialAdmob) {
        admobInterstitialAds = AdmobInterstitialAds.getInstance(mContext, keyInterstitialAdmob, this);
    }

    public void initInterstitialAppnext(String keyInterstitialAppnext) {
        appnextAdsInterstitial = AppnextAdsInterstitial.getInstance(mContext, keyInterstitialAppnext, this);
    }

    public void initInterstitialFacebook(String keyInterstitialFacebook) {
        facebookAdsInterstitial = FacebookAdsInterstitial.getInstance(mContext, keyInterstitialFacebook, this, this);
    }


    public void reLoadedInterstitial() {
        if (admobInterstitialAds != null) {
            admobInterstitialAds.interstitialAdmob.loadAd(new AdRequest.Builder().addTestDevice("74df1a5b43f90b50dd8ea33699814380").build());
        } else if (facebookAdsInterstitial != null) {
            facebookAdsInterstitial.interstitialAd.loadAd();
        }


    }

    public void destroyDisplay() {
        io.display.sdk.Controller.getInstance().onDestroy();
    }


    public boolean isSomeAdLoaded() {
        if (facebookAdsInterstitial != null && facebookAdsInterstitial.interstitialAd.isAdLoaded()) {
            Log.d(nameLogs, "isSomeAdLoaded : Facebook");
            return true;
        } else if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
            Log.d(nameLogs, "isSomeAdLoaded : Admob");
            return true;
        } else if (appnextAdsInterstitial != null && appnextAdsInterstitial.isLoadedAppNext()) {
            Log.d(nameLogs, "isSomeAdLoaded : Appnext");
            return true;
        } else {
            Log.d(nameLogs, "isSomeAdLoaded : Nimic nu este Loaded");
            return false;
        }
    }

    public void showInterstitial(List<String> flow, String action) {
        if (flow != null && action != null) {
            Log.d(nameLogs, "the flow is " + flow);
            nAction = action;
            addsFlowInterstitial = flow;

            if (isSomeAdLoaded()) {
                boolean adShown = false;
                for (int i = 0; i < flow.size(); i++) {
                    if (!adShown) //show ad only once
                    {
                        if (flow.get(i).equals("facebook")) {
                            if (facebookAdsInterstitial != null && facebookAdsInterstitial.interstitialAd.isAdLoaded()) {
                                Log.d(nameLogs, "Show " + flow.get(i) + " ad...");
                                facebookAdsInterstitial.showInterstitialFacebook();
                                adShown = true;
                            }
                        } else if (flow.get(i).equals("admob")) {
                            if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
                                Log.d(nameLogs, "Show " + flow.get(i) + " ad...");
                                admobInterstitialAds.showAdmobAds();
                                adShown = true;
                            }
                        } else if (flow.get(i).equals("appnext")) {
                            if (appnextAdsInterstitial != null && appnextAdsInterstitial.isLoadedAppNext()) {
                                Log.d(nameLogs, "Show " + flow.get(i) + " ad...");
                                appnextAdsInterstitial.showAppNext();
                                adShown = true;
                            }
                        } else {
                            Log.d(nameLogs, "isSomeAdLoaded : Nimic nu este Loaded");
                            adShown = false;
                        }
                    }
                }
            }
        }

    }


    @Override
    public void isClosedInterAds() {
        if (listenerAds != null) listenerAds.afterInterstitialIsClosed(nAction);
    }

    public static synchronized ManagerInterstitial getInstance(Activity nContext) {
        if (instance == null) {
            return new ManagerInterstitial(nContext);
        } else {
            return instance;
        }
    }

    public void destroyInterstitial() {
        if (facebookAdsInterstitial != null) {
            facebookAdsInterstitial.interstitialAd.destroy();
        }
    }

    @Override
    public void mGoBackFromDisplay() {
        Log.d(nameLogs, "mCOMEBACK");
        //showInterstitial(addsFlowInterstitial,"action");
        //runAdds_Part2Interstitial();
        if (isNoAdsDisplay) {
            if (listenerAds != null) listenerAds.afterInterstitialIsClosed(nAction);
        }
    }

}
