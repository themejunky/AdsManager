package themejunky.module_adsmanager.managers;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;

import java.util.Arrays;
import java.util.List;

import themejunky.module_adsmanager.ads.interstitialAds.AdColonyInterstitialAds;
import themejunky.module_adsmanager.ads.interstitialAds.AdmobInterstitialAds;
import themejunky.module_adsmanager.ads.interstitialAds.AppnextAdsInterstitial;
import themejunky.module_adsmanager.ads.interstitialAds.DisplayInterstitialAds;
import themejunky.module_adsmanager.ads.interstitialAds.FacebookAdsInterstitial;
import themejunky.module_adsmanager.ads.interstitialAds.VungleInterstitialAds;
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
    private DisplayInterstitialAds displayInterstitialAds;
    private AdColonyInterstitialAds adColonyInterstitialAds;
    private VungleInterstitialAds vungleInterstitialAds;
    private String placementId;
    private String whichAdIsLoaded;
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

    public void initInterstitialDisplay(String appid, String placementId) {
        this.placementId = placementId;
        displayInterstitialAds = DisplayInterstitialAds.getInstance(mContext, appid, this);
    }

    public void initInterstitialAdColony(String colonyAppID, String zoneId) {
        adColonyInterstitialAds = AdColonyInterstitialAds.getInstance(mContext, colonyAppID, zoneId, this);
    }

    public void initInterstitialVungle(String appId, String[] placements) {
        vungleInterstitialAds = VungleInterstitialAds.getmInstance(mContext, appId, placements, this);
    }



    public void showDisplayIo(String placementId) {
        displayInterstitialAds.showAd(mContext, placementId, this);
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


    public void showInterstitial(List<String> flow, String action) {
        if (flow != null && action != null) {
            Log.d(nameLogs, "the flow is " + flow);
            nAction = action;
            addsFlowInterstitial = flow;

            boolean adShown = false;
            for (int i = 0; i < flow.size(); i++) {
                if (!adShown) {
                    if (flow.get(i).equals("facebook")) {
                        if (facebookAdsInterstitial != null && facebookAdsInterstitial.interstitialAd.isAdLoaded()) {
                            Log.d(nameLogs, "isSomeAdLoaded : Facebook, show ad...");
                            facebookAdsInterstitial.showInterstitialFacebook();
                            whichAdIsLoaded = "facebook";
                            adShown = true;
                        }
                    } else if (flow.get(i).equals("admob")) {
                        if (admobInterstitialAds != null && admobInterstitialAds.isLoadedAdmob()) {
                            Log.d(nameLogs, "isSomeAdLoaded : Admob, show ad...");
                            admobInterstitialAds.showAdmobAds();
                            whichAdIsLoaded = "admob";
                            adShown = true;
                        }
                    } else if (flow.get(i).equals("appnext")) {
                        if (appnextAdsInterstitial != null && appnextAdsInterstitial.isLoadedAppNext()) {
                            Log.d(nameLogs, "isSomeAdLoaded : Appnext, show ad...");
                            appnextAdsInterstitial.showAppNext();
                            whichAdIsLoaded = "appnext";
                            adShown = true;
                        }
                    } else if (flow.get(i).equals("display")) {
                        if (displayInterstitialAds != null && displayInterstitialAds.ctrl.isInitialized() && displayInterstitialAds.isReadyDisplay) {
                            Log.d(nameLogs, "isSomeAdLoaded : Display, show ad...");
                            displayInterstitialAds.showAd(mContext, placementId, this);
                            whichAdIsLoaded = "appnext";
                            adShown = true;
                        }
                    } else if (flow.get(i).equals("adcolony")) {
                        if (adColonyInterstitialAds != null && adColonyInterstitialAds.isAdColonyLoaded()) {
                            Log.d(nameLogs, "isSomeAdLoaded : AdColony, show ad...");
                            adColonyInterstitialAds.showAds();
                            whichAdIsLoaded = "adcolony";
                            adShown = true;
                        }
                    } else if (flow.get(i).equals("vungle")) {
                        if (vungleInterstitialAds != null && vungleInterstitialAds.isVungleLoaded()) {
                            Log.d(nameLogs, "isSomeAdLoaded : Vungle, show ad...");
                            vungleInterstitialAds.showVungleAds();
                            whichAdIsLoaded = "vungle";
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
        } else if (displayInterstitialAds != null) {
            io.display.sdk.Controller.getInstance().onDestroy();
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

    public void adColonyOnResume() {
        if (adColonyInterstitialAds != null) {
            adColonyInterstitialAds.adColonyOnResume();
        }

    }
}
