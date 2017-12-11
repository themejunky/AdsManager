package themejunky.module_adsmanager.ads;

import android.content.Context;

import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.Tapjoy;

import java.util.Hashtable;

/**
 * Created by Junky2 on 12/8/2017.
 */

public class TabjoyAdsInterstitial {
    private static TabjoyAdsInterstitial mInstance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    public Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
    public boolean isTapjoyLoaded;
    private TJPlacement tjPlacement;

    public TabjoyAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    public void initTapjoyInterstitial(final Context context, String adUnitId, final AdsListenerManager.ListenerAds listenerAds) {
        connectFlags.put("ENABLE_LOGGING",true);
        Tapjoy.setDebugEnabled(true);
        Tapjoy.connect(context, adUnitId, connectFlags, new TJConnectListener() {
            @Override
            public void onConnectSuccess() {
                listenerLogs.logs("Tapjoy Inter: is Loaded");
                listenerAds.loadedInterAds();
                isTapjoyLoaded = true;
                TJPlacementListener placementListener = new TJPlacementListener() {
                    @Override
                    public void onRequestSuccess(TJPlacement tjPlacement) {
                        listenerLogs.logs("Tapjoy Inter: is Success");
                    }

                    @Override
                    public void onRequestFailure(TJPlacement tjPlacement, TJError tjError) {
                        listenerLogs.logs("Tapjoy Inter: Failed To Load: "+tjError.message);
                    }

                    @Override
                    public void onContentReady(TJPlacement tjPlacement) {

                    }

                    @Override
                    public void onContentShow(TJPlacement tjPlacement) {
                        listenerLogs.logs("Tapjoy Inter:  Show");
                    }

                    @Override
                    public void onContentDismiss(TJPlacement tjPlacement) {
                        listenerLogs.logs("Tapjoy Inter: Closed");
                        listenerLogs.isClosedInterAds();
                    }

                    @Override
                    public void onPurchaseRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s) {

                    }

                    @Override
                    public void onRewardRequest(TJPlacement tjPlacement, TJActionRequest tjActionRequest, String s, int i) {

                    }
                };
                tjPlacement = new TJPlacement(context, "Interstitial", placementListener);


            }



            @Override
            public void onConnectFailure() {
                listenerLogs.logs("Tapjoy Inter: Failed To Load: ");
                listenerAds.loadInterFailed();
            }
        });
    }

    public void showTapjoy(){
        tjPlacement.showContent();
    }
    public synchronized static TabjoyAdsInterstitial getmInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (mInstance == null) mInstance = new TabjoyAdsInterstitial(listenerLogs);
        return mInstance;

    }

}
