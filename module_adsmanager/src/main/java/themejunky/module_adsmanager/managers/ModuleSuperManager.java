package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ModuleSuperManager extends ManagerBase {

    public ManagerNative managerNative;
    private ManagerInterstitial managerInterstitial;

    public ModuleSuperManager(Context nContext, boolean showAds) {
        if (showAds){
            managerNative = ManagerNative.getInstance(nContext);
        }
    }

    public void setLogName(String nNameLog){
        nameLogs = nNameLog;

    }
}
