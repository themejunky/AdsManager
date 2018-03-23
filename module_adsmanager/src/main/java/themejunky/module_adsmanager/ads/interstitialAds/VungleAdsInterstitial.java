package themejunky.module_adsmanager.ads.interstitialAds;

import android.content.Context;
import android.os.Bundle;

import com.vungle.publisher.AdConfig;
import com.vungle.publisher.VungleAdEventListener;
import com.vungle.publisher.VungleInitListener;
import com.vungle.publisher.VunglePub;

import themejunky.module_adsmanager.ads.AdsListenerManager;

public class VungleAdsInterstitial extends android.app.Activity {
    // get the VunglePub instance
    final VunglePub vunglePub = VunglePub.getInstance();
    final AdConfig globalAdConfig = vunglePub.getGlobalAdConfig();

    public static VungleAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    Context context;

    public VungleAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    // Get your Vungle App ID and Placement ID information from Vungle Dashboard
    final String app_id = "5916309cb46f6b5a3e00009c";
    final String DEFAULT_PLACEMENT_ID = "DEFAULT32590";
    private final String[] placement_list = { DEFAULT_PLACEMENT_ID, "TESTREW28799", "TESTINT07107" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    public void initVungle(Context context, String placementID, final AdsListenerManager.ListenerAds listenerAds) {
        listenerLogs.logs("Vungle inter: initialized");
        vunglePub.init(context, app_id, placement_list, new VungleInitListener() {
                @Override
                public void onSuccess() {
                    listenerLogs.logs("Vungle inter: onSuccess");
                    vunglePub.clearAndSetEventListeners(vungleListener);
                    for (int i = 0; i < 3; i++) {
                        final int index = i;
                        if (vunglePub != null && vunglePub.isInitialized()) {
                            // Load an ad using a Placement ID
                            vunglePub.loadAd(placement_list[index]);
                            if (vunglePub != null && vunglePub.isInitialized()) {
                                // Check a Placement if it is ready to play the Ad
                                if (vunglePub.isAdPlayable(placement_list[index])) {
                                    // Play a Placement ad with Placement ID, you can pass AdConfig to customize your ad
                                    vunglePub.playAd(placement_list[index], null);
                                }
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Throwable e){
                    listenerLogs.logs("Vungle inter: Error"+e.getMessage());
                }
            });

        }


    public void showVungle() {
        if (vunglePub != null && vunglePub.isInitialized()) {
            // Load an ad using a Placement ID
            vunglePub.loadAd("DEFAULT32590");
            listenerLogs.logs("Vungle inter: load ad");
            if (vunglePub.isAdPlayable("DEFAULT32590")) {
                vunglePub.playAd(app_id, globalAdConfig);
                listenerLogs.logs("Vungle inter: play ad");
            }
        }
    }

    public boolean isLoadedVungle() {
        if (vunglePub.isAdPlayable("DEFAULT32590")) {
            return true;
        } else {
            return false;
        }
        /*
        if (interstitialVungle != null && interstitialVungle.isAdLoaded()) {
            return true;
        } else {
            return false;
        }
        */
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
        }

        @Override
        public void onAdStart(String placementReferenceId) {
            // Called before playing an ad
            listenerLogs.logs("Vungle inter: onAdStart");
        }

        @Override
        public void onUnableToPlayAd(String placementReferenceId, String reason) {
            // Called after playAd(placementId, adConfig) is unable to play the ad
            listenerLogs.logs("Vungle inter: onUnableToPlayAd");
        }

        @Override
        public void onAdAvailabilityUpdate(String placementReferenceId, boolean isAdAvailable) {
            // Notifies ad availability for the indicated placement
            // There can be duplicate notifications
            listenerLogs.logs("Vungle inter: onAdAvailabilityUpdate");
        }
    };

    public synchronized static VungleAdsInterstitial getInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new VungleAdsInterstitial(listenerLogs);
        return instance;
    }
}
