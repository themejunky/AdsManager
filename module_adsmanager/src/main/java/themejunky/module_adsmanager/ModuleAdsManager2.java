package themejunky.module_adsmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;

public class ModuleAdsManager2 implements AdsListenerManager.ListenerLogs {

    private boolean mShowAds;

    private AdsListenerManager.ListenerAds mListener;

    private AdmobNativeAds mNativeAdmob;

    Context mContext;

    View mAd_NativeAdmob;
    private String nameLogs;

    public ModuleAdsManager2(Context nContext, boolean nShowAds,AdsListenerManager.ListenerAds nListener ) {
        this.mContext = nContext;
        this.mShowAds = nShowAds;
        this.mListener = nListener;
    }

    public void setNativeAdmobKey(String nNativeAdmobKey) {
        if (mShowAds) {
            mNativeAdmob = new AdmobNativeAds(mContext, this, mListener);
            mAd_NativeAdmob = mInflateLayout(R.layout.ads_native_admob);

            ((RelativeLayout) mAd_NativeAdmob.findViewById(R.id.containerAdmobNativeAds)).removeAllViews();

            mNativeAdmob.initAdmobNativeAdvance(mAd_NativeAdmob, nNativeAdmobKey);
        }
    }
    public void setLogs(String nameLog) {
        this.nameLogs = nameLog;
    }

    public View getAds() {
        return mAd_NativeAdmob;
    }






    protected View mInflateLayout(int nLayout) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View DialogInflateView = factory.inflate(nLayout, null);
        return DialogInflateView.findViewById(R.id.containerAdmob);
    }









    @Override
    public void logs(String logs) {
        Log.d(nameLogs, logs);
    }

    @Override
    public void isClosedInterAds() {

    }
}
