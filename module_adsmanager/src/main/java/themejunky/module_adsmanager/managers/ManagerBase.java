package themejunky.module_adsmanager.managers;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.ads.AdsListenerManager;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerBase extends SlaveListener {
    protected int next;
    protected static List<String> addsFlowNative = new ArrayList<>();
    protected static List<String> addsFlowInterstitial = new ArrayList<>();
    public static String nameLogs="debugTest";
    public static String nAction="testAction";
    protected View containerNativeView;
    protected static AdsListenerManager.ListenerAds listenerAds ;
    protected static boolean isReloaded;




    @Override
    public void logs(String logs) {
        Log.d(nameLogs, logs);

    }


}
