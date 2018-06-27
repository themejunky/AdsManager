package themejunky.module_adsmanager.managers;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.vungle.warren.Vungle;

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
        adColonyInterstitialAds = AdColonyInterstitialAds.getInstance(mContext, colonyAppID,zoneId, this);
    }
    public void initInterstitialVungle(String appId,List<String>placements){
        vungleInterstitialAds = VungleInterstitialAds.getmInstance(mContext,appId,placements,this);
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
        }else if (displayInterstitialAds != null && displayInterstitialAds.ctrl.isInitialized()) {
            Log.d(nameLogs, "isSomeAdLoaded : Display");
            return true;
        }else if (adColonyInterstitialAds != null && adColonyInterstitialAds.isAdColonyLoaded()) {
            Log.d(nameLogs, "isSomeAdLoaded : AdColony");
            return true;
        }else if (vungleInterstitialAds != null &&vungleInterstitialAds.isVungleLoaded()) {
            Log.d(nameLogs, "isSomeAdLoaded : Vungle");
            return true;
        } else {
            Log.d(nameLogs, "isSomeAdLoaded : Nimic nu este Loaded");
            return false;
        }

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

    private void runAdds_Part1Interstitial() {
        next = -1;
        runAdds_Part2Interstitial();


    }

    public void showInterstitial(List<String> flow, String action) {
        if (flow != null && action != null) {
            nAction = action;
            addsFlowInterstitial = flow;
            runAdds_Part1Interstitial();

        }

    }

    public void runAdds_Part2Interstitial() {
        next++;
        if (next < addsFlowInterstitial.size()) {
            Log.d(nameLogs, "Flow Interstitial--- " + addsFlowInterstitial.get(next) + " ---");
                switch (addsFlowInterstitial.get(next)) {
                    case ConstantsAds.ADMOB:
                        Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 1");
                        if (admobInterstitialAds != null) {
                            if (admobInterstitialAds.isLoadedAdmob()) {
                                Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 2");
                                admobInterstitialAds.showAdmobAds();
                                Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 3");
                            } else {
                                Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 4");
                                runAdds_Part2Interstitial();
                            }
                        } else {
                            Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 4");
                            runAdds_Part2Interstitial();
                        }
                        break;
                    case ConstantsAds.APPNEXT:
                        Log.d(nameLogs, "Flow Interstitial: AppNext Interstitial 1");
                        if (appnextAdsInterstitial != null) {
                            if (appnextAdsInterstitial.isLoadedAppNext()) {
                                Log.d(nameLogs, "Flow Interstitial: Appnext Interstitial 2");
                                appnextAdsInterstitial.showAppNext();
                                Log.d(nameLogs, "Flow Interstitial: Appnext Interstitial 3");
                            } else {
                                Log.d(nameLogs, "Flow Interstitial: Appnext Interstitial 4");
                                runAdds_Part2Interstitial();
                            }
                        } else {
                            Log.d(nameLogs, "Flow Interstitial: Appnext Interstitial 4");
                            runAdds_Part2Interstitial();
                        }
                        break;
                    case ConstantsAds.FACEBOOK:
                        Log.d(nameLogs, "Flow Interstitial: Facebook Interstitial 1");
                        if (facebookAdsInterstitial != null && facebookAdsInterstitial.interstitialAd != null) {
                            if (facebookAdsInterstitial.isFacebookLoaded()) {
                                Log.d(nameLogs, "Flow Interstitial: Facebook Interstitial 2");
                                facebookAdsInterstitial.showInterstitialFacebook();
                                Log.d(nameLogs, "Flow Interstitial: Facebook Interstitial 3");
                            } else {
                                Log.d(nameLogs, "Flow Interstitial: Facebook Interstitial 4");
                                runAdds_Part2Interstitial();
                            }
                        } else {
                            Log.d(nameLogs, "Flow Interstitial: Facebook Interstitial 4");
                            runAdds_Part2Interstitial();
                        }
                        break;
                    case ConstantsAds.DISPLAY:
                        Log.d(nameLogs, "Flow Interstitial: Display.Io Interstitial 1");
                        if (displayInterstitialAds != null && displayInterstitialAds.isReadyDisplay) {
                            Log.d(nameLogs, "Flow Interstitial: Display.Io Interstitial 2");
                            displayInterstitialAds.showAd(mContext, placementId, this);
                            Log.d(nameLogs, "Flow Interstitial: Display.Io Interstitial 3");
                        } else {
                            Log.d(nameLogs, "Flow Interstitial: Display Interstitial 4");
                            runAdds_Part2Interstitial();
                        }
                        break;
                    case ConstantsAds.ADCOLONY:
                        Log.d(nameLogs, "Flow Interstitial: AdColony Interstitial 1");
                        if (adColonyInterstitialAds != null ) {
                            Log.d(nameLogs, "Flow Interstitial: AdColony Interstitial 2");
                            adColonyInterstitialAds.showAds();
                            Log.d(nameLogs, "Flow Interstitial: AdColony Interstitial 3");
                        } else {
                            Log.d(nameLogs, "Flow Interstitial: AdColony Interstitial 4");
                            runAdds_Part2Interstitial();
                        }
                        break;
                    case ConstantsAds.VUNGLE:
                        Log.d(nameLogs, "Flow Interstitial: Vungle Interstitial 1");
                        if (vungleInterstitialAds != null && Vungle.canPlayAd(placementId) ) {
                            Log.d(nameLogs, "Flow Interstitial: Vungle Interstitial 2");
                            vungleInterstitialAds.showVungleAds();
                            Log.d(nameLogs, "Flow Interstitial: Vungle Interstitial 3");
                        } else {
                            Log.d(nameLogs, "Flow Interstitial: Vungle Interstitial 4");
                            runAdds_Part2Interstitial();
                        }
                        break;
                    default:
                        Log.d(nameLogs, "Flow Interstitial: ---Default---");
                        // runAdds_Part2Interstitial();
                        break;
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
        runAdds_Part2Interstitial();
        if (isNoAdsDisplay) {
            if (listenerAds != null) listenerAds.afterInterstitialIsClosed(nAction);
        }
    }
    public void adColonyOnResume(){
        if(adColonyInterstitialAds!=null){
            adColonyInterstitialAds.adColonyOnResume();
        }

    }
}
