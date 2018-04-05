package themejunky.module_adsmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ads.interstitialAds.AdmobAdsInterstitial;
import themejunky.module_adsmanager.ads.interstitialAds.FacebookAdsInterstitial;
import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;
import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ads.interstitialAds.AppnextAdsInterstitial;

import themejunky.module_adsmanager.ads.nativeAds.AppnextNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.FacebookNativeAds;
import themejunky.module_adsmanager.utils.ConstantsAds;

/**
 * Created by Alin on 11/16/2017.
 */

public class ModuleAdsManager implements AdsListenerManager.ListenerLogs {

    public static ModuleAdsManager instance = null;
    private final boolean showAds;
    private FacebookNativeAds facebookNativeAds;
    private AdmobNativeAds admobNativeAds;
    private View facebookView, admobView, appnextView;
    private String nameLogs;
    private LayoutInflater factory;
    private int next;
    private Context activity;
    private static List<String> addsFlow = new ArrayList<>();
    private View inflateView;
    private boolean isFacebookInitialized, isAdmobInitialized;
    private FacebookAdsInterstitial facebookAds;
    private AdmobAdsInterstitial admobAds;
    private String action;
    private AppnextAdsInterstitial appnextAds;
    private AdsListenerManager.ListenerAds listenerAds;
    private AppnextNativeAds appnextNativeAds;


    public ModuleAdsManager(Context activity, boolean showAds) {
        this.activity = activity;
        this.showAds = showAds;
    }

    public void initializeNativeAds(boolean isNewInstance, boolean isFacebook) {
        if (showAds) {
            if (isNewInstance) {
                if (isFacebook) {
                    appnextNativeAds = new AppnextNativeAds(activity, this, listenerAds);
                    admobNativeAds = new AdmobNativeAds(activity, this, listenerAds);
                    facebookNativeAds = new FacebookNativeAds(activity, this, listenerAds);
                } else {
                    appnextNativeAds = new AppnextNativeAds(activity, this, listenerAds);
                    admobNativeAds = new AdmobNativeAds(activity, this, listenerAds);
                }
            } else {
                if (isFacebook) {
                    appnextNativeAds = new AppnextNativeAds(activity, this, listenerAds);
                    admobNativeAds = AdmobNativeAds.getInstance(activity, this, listenerAds);
                    facebookNativeAds = new FacebookNativeAds(activity, this, listenerAds);
                } else {
                    appnextNativeAds = new AppnextNativeAds(activity, this, listenerAds);
                    admobNativeAds = AdmobNativeAds.getInstance(activity, this, listenerAds);
                }
            }
        }
    }

    public void initializeInterlAds(boolean isFacebook) {
        if (showAds) {
            if (isFacebook) {
                facebookAds = FacebookAdsInterstitial.getmInstance(this, listenerAds);
                admobAds = AdmobAdsInterstitial.getmInstance(this, listenerAds);
                appnextAds = AppnextAdsInterstitial.getInstance(this, listenerAds);
            } else {
                admobAds = AdmobAdsInterstitial.getmInstance(this, listenerAds);
                appnextAds = AppnextAdsInterstitial.getInstance(this, listenerAds);
            }
        }
    }

    public void setListenerAds(AdsListenerManager.ListenerAds listenerAds) {
        this.listenerAds = listenerAds;
    }

    public void setNativeFlowAndShowAds(List<String> flowAds, View facebookView, View admobView, View appnextView) {
        this.facebookView = facebookView;
        this.admobView = admobView;
        this.appnextView = appnextView;
        addsFlow = flowAds;
        runAdds_Part1Native();

    }

    public void setInterFlowAndShowAds(List<String> flowAds, String action) {
        this.action = action;
        addsFlow = flowAds;
        runAdds_Part1Inter();

    }

    public View getAllViewAds(String type) {
        factory = LayoutInflater.from(activity);
        if (type.equals("facebook")) {
            inflateView = factory.inflate(R.layout.container_facebook_ads, null);
        } else if (type.equals("admob")) {
            inflateView = factory.inflate(R.layout.container_admob_ads, null);
        } else if (type.equals("appnext")) {
            Log.d("InfoA", "inflate appnext");
            inflateView = factory.inflate(R.layout.container_appnext_ads, null);
        }
        return inflateView;

    }

    public void initInterstitialFacebookAds(String keyFacebook) {
        if (showAds) {
            if (keyFacebook != null && !keyFacebook.equals("")) {
                facebookAds.initFacebookInterstitial(activity, keyFacebook);
            }
        }
    }

    public void initInterstitialAdmobAds(String keyAdmob) {
        if (showAds) {
            if (keyAdmob != null && !keyAdmob.equals("")) {
                admobAds.initAdmobInterstitial(activity, keyAdmob);
            }
        }
    }

    public void initInterstitialAppNextAds(String keyAppNext) {
        if (showAds) {
            if (keyAppNext != null && !keyAppNext.equals("")) {
                appnextAds.initAppnext(activity, keyAppNext);
            }
        }
    }

    public void setAdmobeMute(Context context) {
        if (showAds) {
            admobAds.setAdmobInterMuted(context);
        }
    }

    public void showInterstitialAdmob() {
        admobAds.showAdmobAds();
    }

    public void showInterstitialAppNext() {
        appnextAds.showAppNext();
    }
    public void showInterstitialFacebook() {
        facebookAds.showInterstitialFacebook();
    }

    public boolean isLoadedFacebook() {
        return facebookAds.isFacebookLoaded();
    }

    public boolean isLoadedAdmob() {
        return admobAds.isLoadedAdmob();
    }

    public boolean isLoadedAppNext() {
        return appnextAds.isLoadedAppNext();
    }

    public boolean isSomeAdLoaded() {
        return facebookAds.isFacebookLoaded()|| admobAds.isLoadedAdmob() || appnextAds.isLoadedAppNext();
    }

    public void initFacebookNativeAds(View view, String keynativeFacebook) {
        if (!isFacebookInitialized) {
            facebookNativeAds.initFacebookNative(view, keynativeFacebook);
            isFacebookInitialized = true;
        }
    }

    public void initAdmobNativeAds(View view, String idUnitAdmob, int typeAdmobAds) {
        if (!isAdmobInitialized) {
            admobNativeAds.initAdmobNativeAdvance(view, idUnitAdmob, typeAdmobAds);
            isAdmobInitialized = true;
        }

    }

    public void initAdmobNativeAds(View view, String idUnitAdmob) {
        if (showAds) {
            admobNativeAds.initAdmobNativeAdvance(view, idUnitAdmob);
        }
    }

    public void initAppnextNativeAds(View view, String idAppnext) {
        if (showAds) {
            appnextNativeAds.initAppnextNativeAdvance(view, idAppnext);
        }
    }

    public void setLogs(String nameLog) {
        this.nameLogs = nameLog;
    }


    private void runAdds_Part1Native() {
        this.next = -1;
        runAdds_Part2Native();
        Log.d("ShowFlow", "flow: runAdds_Part1Native");
    }

    private void runAdds_Part1Inter() {
        this.next = -1;
        runAdds_Part2Inter();
        Log.d("ShowFlow", "flow: runAdds_Part1Inter");
    }

    private void runAdds_Part2Native() {
        Log.d("ShowFlow", "flow: runAdds_Part2Native");
        this.next++;
        if (next < addsFlow.size() && activity != null) {
            Log.d("ShowFlow", "flow: " + addsFlow.get(next));
            switch (addsFlow.get(next)) {

                case ConstantsAds.ADMOB:
                    Log.d("ShowFlow", "ADMOB 1");
                    if (admobNativeAds.isLoaded) {
                        Log.d("ShowFlow", "ADMOB 2");
                        admobView.setVisibility(View.VISIBLE);
                        Log.d("ShowFlow", "ADMOB 3");
                    } else {
                        Log.d("ShowFlow", "ADMOB 4");
                        runAdds_Part2Native();
                    }
                    break;
                case ConstantsAds.APPNEXT:
                    Log.d("ShowFlow", "APPNEXT  1");
                    if (appnextNativeAds.isLoadedAppnextAds()) {
                        Log.d("ShowFlow", "APPNEXT 2");
                        appnextView.setVisibility(View.VISIBLE);
                        Log.d("ShowFlow", "APPNEXT 3");
                    } else {
                        Log.d("ShowFlow", "APPNEXT 4");
                        runAdds_Part2Native();
                    }
                    break;
                case ConstantsAds.FACEBOOK:
                    Log.d("ShowFlow", "FACEBOOK 1");
                    if (facebookNativeAds != null) {
                        Log.d("ShowFlow", "FACEBOOK 1.2");
                        if (facebookNativeAds.nativeAd != null) {
                            if (facebookNativeAds.nativeAd.isAdLoaded()) {
                                Log.d("ShowFlow", "FACEBOOK 2");
                                facebookView.setVisibility(View.VISIBLE);
                                Log.d("ShowFlow", "FACEBOOK 3");

                            }
                        } else {
                            Log.d("ShowFlow", "FACEBOOK 4");
                            runAdds_Part2Native();
                        }
                        Log.d("ShowFlow", "FACEBOOK 5");
                    } else {
                        runAdds_Part2Native();
                    }
                    break;
                default:
                    Log.d("ShowFlow", "default ");
                    runAdds_Part2Native();
                    break;
            }
        }
    }


    private void runAdds_Part2Inter() {

        this.next++;
        if (next < addsFlow.size() && activity != null) {
            switch (addsFlow.get(next)) {

                case ConstantsAds.ADMOB:
                    Log.d("ShowFlow", "ADMOB INTER 1");
                    if (admobAds.isLoadedAdmob()) {
                        Log.d("ShowFlow", "ADMOB INTER 2");
                        admobAds.showAdmobAds();
                        Log.d("ShowFlow", "ADMOB INTER 3");
                    } else {
                        Log.d("ShowFlow", "ADMOB INTER 4");
                        runAdds_Part2Inter();
                    }
                    break;
                case ConstantsAds.FACEBOOK:
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
                    break;
                default:
                    runAdds_Part2Inter();
                    break;
            }
        }
    }


    public synchronized static ModuleAdsManager getInstance(Context activity, boolean showAds) {
        if (instance == null) instance = new ModuleAdsManager(activity, showAds);
        return instance;
    }

    @Override
    public void logs(String logs) {
        Log.d(nameLogs, logs);
    }

    @Override
    public void isClosedInterAds() {
        listenerAds.afterInterstitialIsClosed(action);
    }
}
