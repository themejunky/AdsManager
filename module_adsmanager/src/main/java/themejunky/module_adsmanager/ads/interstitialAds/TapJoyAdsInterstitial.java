package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tapjoy.TJConnectListener;
import com.tapjoy.Tapjoy;
import java.util.Hashtable;
import themejunky.module_adsmanager.ads.AdsListenerManager;

public class TapJoyAdsInterstitial extends android.app.Activity {
    public static TapJoyAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    private Context context;

    public TapJoyAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    public void initTapJoy(Context context, final String appId, final String placementID, final AdsListenerManager.ListenerAds listenerAds) {
        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        Tapjoy.setDebugEnabled(true);
        //connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");      // remember to turn this off for your production builds!
        listenerLogs.logs("TapJoy inter: initialized");

        //Tapjoy.connect(this.getApplicationContext(), "IeqSOuOsTTyeh7oo8am8vgECt6nMg1MriaFZM0cq3mpDb9IVWJf-37EN3YCW", connectFlags, TapJoyAdsInterstitial.this);

        Tapjoy.connect(context, "IeqSOuOsTTyeh7oo8am8vgECt6nMg1MriaFZM0cq3mpDb9IVWJf-37EN3YCW", connectFlags, new TJConnectListener() {
            @Override
            public void onConnectSuccess() {
                listenerLogs.logs("TapJoy inter: onConnectSuccess");
                this.onConnectSuccess();
            }

            @Override
            public void onConnectFailure() {
                listenerLogs.logs("TapJoy inter: onConnectFailure");
                this.onConnectFailure();
            }
        });


    }

        /*

    public void showTapJoy() {
        if (vunglePub.isAdPlayable(placementID)) {
            Log.d("InfoAds","isAdPlayable true");
            vunglePub.playAd(placementID, null);
        } else {
            vunglePub.playAd(placementID, null);
            Log.d("InfoAds","isAdPlayable false");
        }
    }

    public boolean isLoadedTapJoy() {
        if (vunglePub != null && vunglePub.isAdPlayable(placementID)) {
            Log.d("InfoAds","isLoadedVungle true");
            return true;
        } else {
            Log.d("InfoAds","isLoadedVungle false");
            return false;
        }
    }
    */


    public synchronized static TapJoyAdsInterstitial getInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new TapJoyAdsInterstitial(listenerLogs);
        return instance;
    }
}
