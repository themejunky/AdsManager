package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;

import java.util.List;

import themejunky.module_adsmanager.ads.interstitialAds.AdmobInterstitialAds;
import themejunky.module_adsmanager.ads.interstitialAds.AppnextAdsInterstitial;
import themejunky.module_adsmanager.ads.interstitialAds.DisplayInterstitialAds;
import themejunky.module_adsmanager.ads.interstitialAds.FacebookAdsInterstitial;
import themejunky.module_adsmanager.utils.ConstantsAds;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerInterstitial extends ManagerBase {
    private static ManagerInterstitial instance = null;
    private final Context mContext;
    private AdmobInterstitialAds admobInterstitialAds;
    private AppnextAdsInterstitial appnextAdsInterstitial;
    private FacebookAdsInterstitial facebookAdsInterstitial;
    private DisplayInterstitialAds displayInterstitialAds;
    private String placementId;

    public ManagerInterstitial(Context nContext) {
        this.mContext = nContext;
    }

    public void initInterstitialAdmob(String keyInterstitialAdmob) {
        admobInterstitialAds = AdmobInterstitialAds.getInstance(mContext, keyInterstitialAdmob, this);}

    public void initInterstitialAppnext(String keyInterstitialAppnext) {
        appnextAdsInterstitial = AppnextAdsInterstitial.getInstance(mContext, keyInterstitialAppnext, this);}

    public void initInterstitialFacebook(String keyInterstitialFacebook) {
        facebookAdsInterstitial = FacebookAdsInterstitial.getInstance(mContext, keyInterstitialFacebook, this);}

    public void initInterstitialDisplay(String appid,String placementId) {
        this.placementId = placementId;
        displayInterstitialAds = DisplayInterstitialAds.getInstance(mContext, appid,this);}

    public boolean isSomeAdLoaded() {
        if(facebookAdsInterstitial!=null && facebookAdsInterstitial.isFacebookLoaded()){
            return true;
        }else if (admobInterstitialAds!=null && admobInterstitialAds.isLoadedAdmob()){
            return true;
        }else if (appnextAdsInterstitial!=null && appnextAdsInterstitial.isLoadedAppNext()){
            return true;
        }else if (displayInterstitialAds!=null && displayInterstitialAds.ctrl.isInitialized()){
            return true;
        }else {
            return false;
        }

    }

    public void showDisplayIo(String placementId){
        displayInterstitialAds.showAd(mContext,placementId);
    }

    public void reLoadedInterstitial(){
        if(admobInterstitialAds!=null){
            admobInterstitialAds.interstitialAdmob.loadAd(new AdRequest.Builder().addTestDevice("74df1a5b43f90b50dd8ea33699814380").build());
        }else if(facebookAdsInterstitial!=null){
            facebookAdsInterstitial.interstitialAd.loadAd();
        }


    }

    public void destroyDisplay(){
        io.display.sdk.Controller.getInstance().onDestroy();
    }

    private void runAdds_Part1Interstitial() {
        next = -1;
        runAdds_Part2Interstitial();
    }

    public void showInterstitial(List<String> flow, String action) {
        if(flow!=null && action!=null){
            nAction = action;
            addsFlowInterstitial = flow;
            runAdds_Part1Interstitial();
        }

    }

    private void runAdds_Part2Interstitial() {
        next++;
        if (next < addsFlowInterstitial.size()) {
            switch (addsFlowInterstitial.get(next)) {
                case ConstantsAds.ADMOB:

                    Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 1");
                    if (admobInterstitialAds != null) {
                        if (admobInterstitialAds.isLoadedAdmob()) {
                            Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 2");
                            admobInterstitialAds.showAdmobAds();
                            Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 3");
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
                    if (facebookAdsInterstitial != null) {
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
                case ConstantsAds.DISPLAY:
                    Log.d(nameLogs, "Flow Interstitial: Display.Io Interstitial 1");
                    if (displayInterstitialAds!=null && displayInterstitialAds.ctrl.isInitialized()) {
                        Log.d(nameLogs, "Flow Interstitial: Display.Io Interstitial 2");
                        displayInterstitialAds.showAd(mContext,placementId);
                        Log.d(nameLogs, "Flow Interstitial: Display.Io Interstitial 3");
                    } else {
                        Log.d(nameLogs, "Flow Interstitial: Display Interstitial 4");
                        runAdds_Part2Interstitial();
                    }
                    break;
                default:
                    runAdds_Part2Interstitial();
                    break;
            }
        }
    }


    @Override
    public void isClosedInterAds() {
        if (listenerAds != null) listenerAds.afterInterstitialIsClosed(nAction);
        Log.d(nameLogs, "Flow Interstitial: Facebook Interstitial 4");
    }

    public static synchronized ManagerInterstitial getInstance(Context nContext) {
        if (instance == null) {
            return new ManagerInterstitial(nContext);
        } else {
            return instance;
        }
    }

    public void destroyInterstitial(){
        if (facebookAdsInterstitial != null) {
            facebookAdsInterstitial.interstitialAd.destroy();
        }else if(displayInterstitialAds!=null){
            io.display.sdk.Controller.getInstance().onDestroy();
        }
    }
}
