package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.vungle.publisher.AdConfig;
import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;

import themejunky.module_adsmanager.ads.AdsListenerManager;

public class VungleAdsInterstitial extends android.app.Activity {
    // get the VunglePub instance
    final VunglePub vunglePub = VunglePub.getInstance();
    //final AdConfig globalAdConfig = vunglePub.getGlobalAdConfig();

    public static VungleAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    private Context context;
    private String placementID;

    public VungleAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    public void initVungle(Context context, final String appId, final String placementID, final AdsListenerManager.ListenerAds listenerAds) {
        listenerLogs.logs("Vungle inter: initialized");
        this.placementID = placementID;
        vunglePub.init(context, appId, new String[]{placementID}, new VungleInitListener() {
                @Override
                public void onSuccess() {
                    listenerLogs.logs("Vungle inter: onSuccess");
                    vunglePub.clearAndSetEventListeners(vungleListener);
                    if (vunglePub != null && vunglePub.isInitialized()) {
                        // Load an ad using a Placement ID
                        Log.d("InfoAds","placementID3 "+placementID);
                        vunglePub.loadAd(placementID);
                        //showVungle();
                    }
                }
                @Override
                public void onFailure(Throwable e){
                    listenerLogs.logs("Vungle inter: Error"+e.getMessage());
                }
            });

        }


    public void showVungle() {
        if (vunglePub.isAdPlayable(placementID)) {
            Log.d("InfoAds","isAdPlayable true");
            vunglePub.playAd(placementID, null);
        } else {
            vunglePub.playAd(placementID, null);
            Log.d("InfoAds","isAdPlayable false");
        }

        /*
        // Check a Placement if it is ready to play the Ad
        if (isLoadedVungle()) {
            Log.d("InfoAds","isLoadedVungle true");
            // Play a Placement ad with Placement ID, you can pass AdConfig to customize your ad
            vunglePub.playAd(placementID, null);
        } else {
            Log.d("InfoAds","isLoadedVungle false");
        }
        */
    }

    public boolean isLoadedVungle() {
        if (vunglePub != null && vunglePub.isAdPlayable(placementID)) {
            Log.d("InfoAds","isLoadedVungle true");
            return true;
        } else {
            Log.d("InfoAds","isLoadedVungle false");
            return false;
        }

    }

    private final VungleAdEventListener vungleListener = new VungleAdEventListener(){

        @Override
        public void onAdEnd(String placementReferenceId, boolean wasSuccessfulView, boolean wasCallToActionClicked) {
            // Called when user exits the ad and control is returned to your application
            // if wasSuccessfulView is true, the user watched the ad and should be rewarded
            // (if this was a rewarded ad).
            // if wasCallToActionClicked is true, the user clicked the call to action
            // button in the ad.
            listenerLogs.logs("Vungle inter: onAdEnd");
            listenerLogs.isClosedInterAds();
        }

        @Override
        public void onAdStart(String placementReferenceId) {
            // Called before playing an ad
            listenerLogs.logs("Vungle inter: onAdStart");
        }

        @Override
        public void onUnableToPlayAd(String placementReferenceId, String reason) {
            // Called after playAd(placementId, adConfig) is unable to play the ad
            listenerLogs.logs("Vungle inter: onUnableToPlayAd "+reason);
        }

        @Override
        public void onAdAvailabilityUpdate(String placementReferenceId, boolean isAdAvailable) {
            // Notifies ad availability for the indicated placement
            // There can be duplicate notifications
            listenerLogs.logs("Vungle inter: onAdAvailabilityUpdate "+isAdAvailable);
        }
    };

    public synchronized static VungleAdsInterstitial getInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new VungleAdsInterstitial(listenerLogs);
        return instance;
    }
}
