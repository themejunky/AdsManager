import android.content.Context;
import android.view.View;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.ads.nativeAds.AdmobNativeAds;


public class ModuleAdsManager extends ModuleAdsManager_Base implements AdsListenerManager.ListenerLogs {


    private boolean mShowAds;

    private AdsListenerManager.ListenerAds mListener;

    private AdmobNativeAds mNativeAdmob;

    public ModuleAdsManager(Context nContext, boolean nShowAds,AdsListenerManager.ListenerAds nListener ) {
        this.mContext = nContext;
        this.mShowAds = nShowAds;
        this.mListener = nListener;
    }

    public void setNativeAdmobKey(String nNativeAdmobKey) {
        if (mShowAds) {
            mNativeAdmob = new AdmobNativeAds(mContext, this, mListener);
            mAd_NativeAdmob = mInflateLayout(R.layout.ads_native_admob);
            mNativeAdmob.initAdmobNativeAdvance(mAd_NativeAdmob, nNativeAdmobKey);
        }
    }


















    @Override
    public void logs(String logs) {

    }

    @Override
    public void isClosedInterAds() {

    }
}
