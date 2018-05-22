package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Calendar;
import java.util.List;

import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.AppnextNativeAds;
import themejunky.module_adsmanager.ads.nativeAds.FacebookNativeAds;
import themejunky.module_adsmanager.utils.ConstantsAds;
import themejunky.module_adsmanager.utils.Stuff;

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

    public void iniNativeAppnext( String keyAppnext, boolean newInstance) {
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

    /*public void showNativeAds() {
        Log.d("wwawq", "showNativeAds");
        runAdds_Part1Native();
    }*/


    public void setNativeFlow(List<String> flow) {
        if (flow != null && flow.size() > 0) addsFlowNative = flow;
    }

    /*private synchronized void runAdds_Part1Native() {
        next = -1;
        runAdds_Part2Native();
    }*/

   /* private synchronized void runAdds_Part2Native() {
        next++;
        if (next < addsFlowNative.size()) {
            switch (addsFlowNative.get(next)) {
                case ConstantsAds.ADMOB:
                    if (admobNativeAds!=null && admobNativeAds.getViewNativeAd() != null && containerNativeView != null) {
                        Log.d(nameLogs, "AdmobNativeAds: 1" );
                        ((RelativeLayout) containerNativeView).removeAllViews();
                        ((RelativeLayout) containerNativeView).addView(admobNativeAds.getViewNativeAd());
                        wasShown = true;
                    } else {
                        Log.d(nameLogs, "AdmobNativeAds: 2" );
                        runAdds_Part2Native();
                    }
                    break;
                case ConstantsAds.APPNEXT:
                    if (appnextNativeAds!=null && appnextNativeAds.getViewNativeAd() != null && containerNativeView != null) {
                        Log.d(nameLogs, "AppnextNativeAds: 1" );
                        ((RelativeLayout) containerNativeView).removeAllViews();
                        ((RelativeLayout) containerNativeView).addView(appnextNativeAds.getViewNativeAd());
                        wasShown = true;
                    } else {
                        Log.d(nameLogs, "AppnextNativeAds: 2" );
                        runAdds_Part2Native();
                    }
                    break;
                case ConstantsAds.FACEBOOK:
                    if (facebookNativeAds!=null && facebookNativeAds.getViewNativeAd() != null && containerNativeView != null) {
                        Log.d(nameLogs, "FacebookNativeAds: 1" );
                        ((RelativeLayout) containerNativeView).removeAllViews();
                        ((RelativeLayout) containerNativeView).addView(facebookNativeAds.getViewNativeAd());
                        wasShown = true;
                    } else {
                        Log.d(nameLogs, "FacebookNativeAds: 2" );
                        runAdds_Part2Native();
                    }
                    break;
                default:
                    runAdds_Part2Native();
                    break;
            }
        }
    }*/



    private synchronized void runAdds_Part2Native_Test() {
        wasShown = false;
        next++;
        if (next < addsFlowNative.size()) {
            if (addsFlowNative.get(next).equals(ConstantsAds.ADMOB)) {
                Log.d(nameLogs, "AdmobNativeAds: 1" );
                if (admobNativeAds != null && admobNativeAds.getViewNativeAd() != null) {
                    Log.d(nameLogs, "AdmobNativeAds: 2" );
                    wasShown = true;
                    mChoosenAd = admobNativeAds.getViewNativeAd();
                    Log.d(nameLogs, "AdmobNativeAds: 3" );
                } else {
                    Log.d(nameLogs, "AdmobNativeAds: 4" );
                     runAdds_Part2Native_Test();
                }
            } else if (addsFlowNative.get(next).equals(ConstantsAds.APPNEXT)) {
                Log.d(nameLogs, "AppnextNativeAds: 1" );
                if (appnextNativeAds != null && appnextNativeAds.getViewNativeAd() != null) {
                    Log.d(nameLogs, "AppnextNativeAds: 2" );
                    wasShown = true;
                    mChoosenAd = appnextNativeAds.getViewNativeAd();
                    Log.d(nameLogs, "AppnextNativeAds: 3" );
                } else {
                    Log.d(nameLogs, "AppnextNativeAds: 4" );
                     runAdds_Part2Native_Test();
                }
            } else if (addsFlowNative.get(next).equals(ConstantsAds.FACEBOOK)) {
                Log.d(nameLogs, "FacebookNativeAds: 1" );
                if (facebookNativeAds != null && facebookNativeAds.getViewNativeAd() != null) {
                    Log.d(nameLogs, "FacebookNativeAds: 2" );
                    wasShown = true;
                    mChoosenAd = facebookNativeAds.getViewNativeAd();
                    Log.d(nameLogs, "FacebookNativeAds: 3" );
                } else {
                    Log.d(nameLogs, "FacebookNativeAds: 4" );
                    runAdds_Part2Native_Test();
                }
            }
        }
    }

    public void showAds(final List<String> nFlow, final ViewGroup nContainer) {
        mChoosenAd = null;
        next = -1;
        Log.d(nameLogs, "Native Ads showAds: 1" );
        if (((appnextNativeAds!=null &&appnextNativeAds.getViewNativeAd() != null) || (admobNativeAds!=null && admobNativeAds.getViewNativeAd() != null) || (facebookNativeAds!=null && facebookNativeAds.getViewNativeAd() != null)) && (nFlow != null && nFlow.size() > 0)) {
            Log.d(nameLogs, "Native Ads showAds: 2" );
            addsFlowNative = nFlow;
            Log.d(nameLogs, "Native Ads showAds: 3" );
            runAdds_Part2Native_Test();
            Log.d(nameLogs, "Native Ads showAds: 4" );
            if (mChoosenAd!=null && nContainer!=null) {
                nContainer.removeAllViews();
                nContainer.addView(mChoosenAd);
                Log.d(nameLogs, "Native Ads showAds: 5" );
            } else {
                Log.d("loop","nasoale "+ Calendar.getInstance().getTime());
            }
        } else if (nContainer!=null) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showAds(nFlow,nContainer);
                }
            }, 250);
            Log.d(nameLogs, "Native Ads showAds: 6" );
        }
        else {
            Log.d(nameLogs, "Native Ads showAds: 7" );
            Log.d("loop","nasoale "+ Calendar.getInstance().getTime());
        }



    }


    public static synchronized ManagerNative getInstance(Context nContext) {
        if (instance == null) {
            return new ManagerNative(nContext);
        } else {
            return instance;
        }
    }


}
