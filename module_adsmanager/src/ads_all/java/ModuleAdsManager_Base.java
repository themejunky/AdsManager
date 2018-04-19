import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import themejunky.module_adsmanager.R;

public class ModuleAdsManager_Base {

    Context mContext;

    View mAd_NativeAdmob;


    protected View mInflateLayout(int nLayout) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View DialogInflateView = factory.inflate(nLayout, null);
        return DialogInflateView.findViewById(R.id.nContainer);
    }
}
