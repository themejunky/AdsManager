package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;
import themejunky.module_adsmanager.utils.ConstantsAds;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerNative extends ManagerBase {

    private AdmobNativeAds admobNativeAds;
    private static ManagerNative instance =null;

    public ManagerNative(Context nContext){
        mContext = nContext;
    }


    public void initNativeAdmob(String keyIdUnitAdmob){
        admobNativeAds = AdmobNativeAds.getInstance(mContext,keyIdUnitAdmob,this,this);

    }
    public void setViewNativeAdmob(View nContainerView){
        mContainerView=nContainerView ;
    }

    @Override
    public void loadNativeAds(String type) {
        runAdds_Part1Native();
        Log.d(nameLogs, "Flow: Admob native 1");
    }

    private void runAdds_Part1Native() {
        this.next = -1;
        runAdds_Part2Native();
    }

    private void runAdds_Part2Native() {
        this.next++;
        if (next < addsFlow.size()) {
            switch (addsFlow.get(next)) {
                case ConstantsAds.ADMOB:
                    Log.d(nameLogs, "Flow: Admob native 1");
                    if (admobNativeAds.getViewNativeAd()!=null && containerNativeView!=null ) {
                        Log.d(nameLogs, "Flow: Admob native 2");
                        ((RelativeLayout)containerNativeView).addView(admobNativeAds.getViewNativeAd());
                        Log.d(nameLogs, "Flow: Admob native 3");
                    } else {
                        Log.d(nameLogs, "Flow: Admob native 4");
                        runAdds_Part2Native();
                    }
                    break;
/*                case ConstantsAds.APPNEXT:
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
                    break;*/
                default:
                    Log.d(nameLogs, "flow d");
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
