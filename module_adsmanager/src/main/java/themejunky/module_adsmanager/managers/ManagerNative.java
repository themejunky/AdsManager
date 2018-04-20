package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;
import android.view.View;
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

    private final Context mContext;
    private AdmobNativeAds admobNativeAds;
    private AppnextNativeAds appnextNativeAds;
    private FacebookNativeAds facebookNativeAds;
    private static ManagerNative instance =null;
    private boolean isOneLoaded=true;

    public ManagerNative(Context nContext){
        this.mContext = nContext;
    }

    public void initNativeAdmob(String keyAdmob){
        admobNativeAds = AdmobNativeAds.getInstance(mContext,keyAdmob,this,this);

    }
    public void iniNativeAppnext (String keyAppnext){
        appnextNativeAds = AppnextNativeAds.getInstance(mContext,keyAppnext,this,this);
    }

    public void iniNativeFacebook (String keyFacebook){
        facebookNativeAds = FacebookNativeAds.getmInstance(mContext,keyFacebook,this,this);
    }

    public void setViewNative(View nContainerView){
        containerNativeView=nContainerView ;
    }

    @Override
    public void nativeLoaded() {
        runAdds_Part1Native();
    }

    @Override
    public void logs(String logs) {
        Log.d(nameLogs, logs);

    }

    public void setNativeFlow(List<String> flow){
        addsFlow = flow;
    }

    private void runAdds_Part1Native() {
        next=-1;
        runAdds_Part2Native();
    }

    private void runAdds_Part2Native() {
        next++;
        if (next < addsFlow.size()) {
            switch (addsFlow.get(next)) {
                case ConstantsAds.ADMOB:
                    Log.d(nameLogs, "Flow Native: Admob  1");
                    if (admobNativeAds.getViewNativeAd()!=null && containerNativeView!=null ) {
                        Log.d(nameLogs, "Flow Native: Admob  2");
                        ((RelativeLayout)containerNativeView).removeAllViews();
                        ((RelativeLayout)containerNativeView).addView(admobNativeAds.getViewNativeAd());
                        Log.d(nameLogs, "Flow Native: Admob  3");
                    } else {
                        Log.d(nameLogs, "Flow Native: Admob  4");
                        runAdds_Part2Native();
                    }
                    break;
               case ConstantsAds.APPNEXT:
                   Log.d(nameLogs, "Flow Native: Appnext  1");
                   if (appnextNativeAds.getViewNativeAd()!=null && containerNativeView!=null ) {
                       Log.d(nameLogs, "Flow Native: Appnext  2");
                       ((RelativeLayout)containerNativeView).removeAllViews();
                       ((RelativeLayout)containerNativeView).addView(appnextNativeAds.getViewNativeAd());
                       Log.d(nameLogs, "Flow Native: Appnext  3");
                   } else {
                       Log.d(nameLogs, "Flow Native: Appnext  4");
                       runAdds_Part2Native();
                   }
                    break;
                 case ConstantsAds.FACEBOOK:
                     Log.d(nameLogs, "Flow Native: Facebook  1");
                     if(facebookNativeAds.getViewNativeAd()==null){
                         Log.d(nameLogs, "Flow Native: Facebook  1 este null");
                     }
                     if (facebookNativeAds.getViewNativeAd()!=null && containerNativeView!=null ) {
                         Log.d(nameLogs, "Flow Native: Facebook  2");
                         ((RelativeLayout)containerNativeView).removeAllViews();
                         ((RelativeLayout)containerNativeView).addView(facebookNativeAds.getViewNativeAd());
                         Log.d(nameLogs, "Flow Native: Facebook  3");
                     } else {
                         Log.d(nameLogs, "Flow Native: Facebook  4");
                         runAdds_Part2Native();
                     }
                    break;
                default:
                    Log.d(nameLogs, "Flow Native: Default");
                    runAdds_Part2Native();
                    break;
            }
        }
    }

    public static synchronized ManagerNative getInstance(Context nContext){
        if(instance==null){
            return new ManagerNative(nContext);
        }else {
            return instance;
        }
    }


}
