package themejunky.module_adsmanager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ads.interstitialAds.AppnextAdsInterstitial;
import themejunky.module_adsmanager.ads.interstitialAds.FacebookAdsInterstitial;
import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.AppnextNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.FacebookNativeAds;
import themejunky.module_adsmanager.managers.ManagerBase;
import themejunky.module_adsmanager.managers.ManagerInterstitial;
import themejunky.module_adsmanager.managers.ManagerNative;
import themejunky.module_adsmanager.utils.ConstantsAds;

public class ModuleAdsManager extends ManagerBase implements AdsListenerManager.ListenerLogs{
    private Context activity;
    private final boolean showAds;
    private ManagerNative managerNative;
    private ManagerInterstitial managerInterstitial;
    private String action;
    private AdsListenerManager.ListenerAds listenerAds;
    private AppnextNativeAds appnextNativeAds;
    private FacebookNativeAds facebookNativeAds;
    private AdmobNativeAds admobNativeAds;
    private static List<String> addsFlow = new ArrayList<>();
    private View facebookView, admobView, appnextView;
    private boolean isFacebookInitialized, isAdmobInitialized;
    private View inflateView;
    private LayoutInflater factory;

    public ModuleAdsManager(Context activity, boolean showAds) {
        this.activity = activity;
        this.showAds = showAds;
    }

    public void initManagers(Activity nContext, boolean reloaded){
        isReloaded = reloaded;
        //managerNative = ManagerNative.getInstance(nContext);
        managerInterstitial = ManagerInterstitial.getInstance(nContext);
    }

    public ManagerNative getManagerNative() {
        return managerNative;
    }

    public ManagerInterstitial getManagerInterstitial() {
        return managerInterstitial;
    }

    public void setLogName(String nNameLog){
        nameLogs = nNameLog;
    }

    public void setListenerAds(AdsListenerManager.ListenerAds nListenerAds){
        if(nListenerAds!=null) listenerAds = nListenerAds;
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

    public void setNativeFlowAndShowAds(List<String> flowAds, View facebookView, View admobView, View appnextView) {
        this.facebookView = facebookView;
        this.admobView = admobView;
        this.appnextView = appnextView;
        addsFlow = flowAds;
        runAdds_Part1Native();

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

    private void runAdds_Part2Native() {
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


}
