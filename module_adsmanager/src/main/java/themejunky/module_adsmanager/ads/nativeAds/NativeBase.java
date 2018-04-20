package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.managers.ManagerBase;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class NativeBase extends ManagerBase {

    protected Context nContext;
    protected View mAdView;

    protected View mInflateLayout(int nLayout) {
        LayoutInflater factory = LayoutInflater.from(nContext);
        View DialogInflateView = factory.inflate(nLayout, null);
        return DialogInflateView.findViewById(R.id.nContainer);
    }

    public View getViewNativeAd() {
        return mAdView;
    }


}
