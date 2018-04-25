package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.AppnextNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.FacebookNativeAds;
import themejunky.module_adsmanager.utils.ConstantsAds;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerNative extends ManagerBase {

    private Context mContext;
    private AdmobNativeAds admobNativeAds;
    private AppnextNativeAds appnextNativeAds;
    private FacebookNativeAds facebookNativeAds;
    private static ManagerNative instance = null;

    private View mChoosenAd = null;

    private boolean wasShown = false;

    public ManagerNative(Context nContext) {
        this.mContext = nContext;
    }

    public void initNativeAdmob(String keyAdmob, boolean newInstance) {
        if (newInstance) {
            admobNativeAds = AdmobNativeAds.getInstance(mContext, keyAdmob, this, this);
        } else {
            admobNativeAds = new AdmobNativeAds(mContext, keyAdmob, this, this);
        }
    }

    public void iniNativeAppnext(Context nContext, String keyAppnext, boolean newInstance) {
        mContext = nContext;
        if (newInstance) {
            appnextNativeAds = new AppnextNativeAds(mContext, keyAppnext, this, this);
        } else {
            appnextNativeAds = AppnextNativeAds.getInstance(mContext, keyAppnext, this, this);
        }
    }

    public void iniNativeFacebook(String keyFacebook, boolean newInstance) {
        if (newInstance) {
            facebookNativeAds = new FacebookNativeAds(mContext, keyFacebook, this, this);
        } else {
            facebookNativeAds = FacebookNativeAds.getmInstance(mContext, keyFacebook, this, this);
        }
    }

    public void setViewNative(View nContainerView) {
        if (nContainerView != null) containerNativeView = nContainerView;
    }

    public void showNativeAds() {
        Log.d("wwawq", "showNativeAds");
        runAdds_Part1Native();
    }


    public void setNativeFlow(List<String> flow) {
        if (flow != null && flow.size() > 0) addsFlowNative = flow;
    }

    private synchronized void runAdds_Part1Native() {
        next = -1;
        runAdds_Part2Native();
    }

    private synchronized void runAdds_Part2Native() {
        next++;
        if (next < addsFlowNative.size()) {
            switch (addsFlowNative.get(next)) {
                case ConstantsAds.ADMOB:
                    if (admobNativeAds.getViewNativeAd() != null && containerNativeView != null) {
                        ((RelativeLayout) containerNativeView).removeAllViews();
                        ((RelativeLayout) containerNativeView).addView(admobNativeAds.getViewNativeAd());
                        wasShown = true;
                    } else {
                        runAdds_Part2Native();
                    }
                    break;
                case ConstantsAds.APPNEXT:
                    if (appnextNativeAds.getViewNativeAd() != null && containerNativeView != null) {
                        ((RelativeLayout) containerNativeView).removeAllViews();
                        ((RelativeLayout) containerNativeView).addView(appnextNativeAds.getViewNativeAd());
                        wasShown = true;
                    } else {
                        runAdds_Part2Native();
                    }
                    break;
                case ConstantsAds.FACEBOOK:
                    if (admobNativeAds.getViewNativeAd() != null) {
                        Log.d("asdfasdf", "FACEBOOK getViewNativeAd nu este null");
                    } else {
                        Log.d("asdfasdf", "FACEBOOK getViewNativeAd este null");
                    }

                    if (containerNativeView != null) {
                        Log.d("asdfasdf", "FACEBOOK containerNativeView nu este null");
                    } else {
                        Log.d("asdfasdf", "FACEBOOK containerNativeView este null");
                    }
                    if (facebookNativeAds.getViewNativeAd() == null) {
                    }
                    if (facebookNativeAds.getViewNativeAd() != null && containerNativeView != null) {
                        ((RelativeLayout) containerNativeView).removeAllViews();
                        ((RelativeLayout) containerNativeView).addView(facebookNativeAds.getViewNativeAd());
                        wasShown = true;
                    } else {
                        runAdds_Part2Native();
                    }
                    break;
                default:
                    runAdds_Part2Native();
                    break;
            }
        }
    }


    private synchronized void runAdds_Part2Native_Test() {
        wasShown = false;
        next++;
        if (next < addsFlowNative.size()) {
            if (addsFlowNative.get(next).equals(ConstantsAds.ADMOB)) {
                if (admobNativeAds != null && admobNativeAds.getViewNativeAd() != null) {
                    wasShown = true;
                    mChoosenAd = admobNativeAds.getViewNativeAd();
                } else {
                     runAdds_Part2Native_Test();
                }
            } else if (addsFlowNative.get(next).equals(ConstantsAds.APPNEXT)) {
                if (appnextNativeAds != null && appnextNativeAds.getViewNativeAd() != null) {
                    wasShown = true;
                    mChoosenAd = appnextNativeAds.getViewNativeAd();
                } else {
                     runAdds_Part2Native_Test();
                }
            } else if (addsFlowNative.get(next).equals(ConstantsAds.FACEBOOK)) {
                if (facebookNativeAds != null && facebookNativeAds.getViewNativeAd() != null) {
                    wasShown = true;
                    mChoosenAd = facebookNativeAds.getViewNativeAd();
                } else {
                    runAdds_Part2Native_Test();
                }
            }
        }
    }

    public void showAds(List<String> nFlow, ViewGroup nContainer) {
        mChoosenAd = null;
        next = -1;

        if (((appnextNativeAds!=null &&appnextNativeAds.getViewNativeAd() != null) || (admobNativeAds!=null && admobNativeAds.getViewNativeAd() != null) || (facebookNativeAds!=null && facebookNativeAds.getViewNativeAd() != null)) && (nFlow != null && nFlow.size() > 0)) {
            Log.d("mChoosenAd","null 1");
            addsFlowNative = nFlow;
            runAdds_Part2Native_Test();
        }

        Log.d("mChoosenAd","mChoosenAd : "+mChoosenAd);


        if (mChoosenAd!=null && nContainer!=null) {
            Log.d("mChoosenAd","null 2");
            nContainer.addView(mChoosenAd);
        }
        Log.d("mChoosenAd","null 3");
    }


    public static synchronized ManagerNative getInstance(Context nContext) {
        if (instance == null) {
            return new ManagerNative(nContext);
        } else {
            return instance;
        }
    }


}
