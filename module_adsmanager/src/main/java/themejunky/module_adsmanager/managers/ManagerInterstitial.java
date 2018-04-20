package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ads.interstitialAds.AdmobInterstitialAds;
import themejunky.module_adsmanager.utils.ConstantsAds;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerInterstitial extends ManagerBase {
    private static ManagerInterstitial instance =null;
    private final Context mContext;
    private AdmobInterstitialAds admobInterstitialAds;
    private String action;

    public ManagerInterstitial(Context nContext){
        this.mContext = nContext;
    }

    public void initInterstitialAdmob(String keyInterstitialAdmob){
        admobInterstitialAds = AdmobInterstitialAds.getmInstance(mContext,keyInterstitialAdmob,this,listenerAds);

    }

    public boolean isSomeAdLoaded() {
        return admobInterstitialAds.isLoadedAdmob();
    }

    private void runAdds_Part1Interstitial() {
        next=-1;
        runAdds_Part2Interstitial();
    }

    public void showInterstitial(List<String> flow,String action){
        this.action = action;
        addsFlow = flow;
        runAdds_Part1Interstitial();

    }

    public void setInterstitialListener(AdsListenerManager.ListenerAds listener){

    }



    private void runAdds_Part2Interstitial() {
        next++;
        if (next < addsFlow.size()) {
            switch (addsFlow.get(next)) {
                case ConstantsAds.ADMOB:
                Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 1");
                    if (admobInterstitialAds.isLoadedAdmob()) {
                        Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 2");
                        admobInterstitialAds.showAdmobAds();
                        Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 3");
                    } else {
                        Log.d(nameLogs, "Flow Interstitial: Admob Interstitial 4");
                        runAdds_Part2Interstitial();
                    }
                    break;
                /*case ConstantsAds.FACEBOOK:
                    Log.d("ShowFlow", "FACEBOOK INTER 1");
                    if (facebookAds != null) {
                        Log.d("ShowFlow", "FACEBOOK INTER 1");
                        if (facebookAds.isFacebookLoaded()) {
                            Log.d("ShowFlow", "FACEBOOK INTER 1");
                            facebookAds.showInterstitialFacebook();
                            Log.d("ShowFlow", "FACEBOOK INTER 1");
                        } else {
                            Log.d("ShowFlow", "FACEBOOK INTER 1");
                            runAdds_Part2Inter();
                        }
                    } else {
                        runAdds_Part2Inter();
                    }

                    break;
                case ConstantsAds.APPNEXT:
                    Log.d("ShowFlow", "APPNEXT INTER 1");
                    if (appnextAds.isLoadedAppNext()) {
                        Log.d("ShowFlow", "APPNEXT INTER 2");
                        appnextAds.showAppNext();
                        Log.d("ShowFlow", "APPNEXT INTER 3");
                    } else {
                        Log.d("ShowFlow", "APPNEXT INTER 4");
                        runAdds_Part2Inter();

                    }
                    break;*/
                default:
                    runAdds_Part2Interstitial();
                    break;
            }
        }
    }

    @Override
    public void isClosedInterAds() {
        listenerAds.afterInterstitialIsClosed(action);
    }

    public static synchronized ManagerInterstitial getInstance(Context nContext){
        if(instance==null){
            return new ManagerInterstitial(nContext);
        }else {
            return instance;
        }
    }
}
