package themejunky.module_adsmanager.ads.interstitialAds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.TJPlacementVideoListener;
import com.tapjoy.Tapjoy;
import java.util.Hashtable;
import themejunky.module_adsmanager.ads.AdsListenerManager;

public class TapJoyAdsInterstitial extends android.app.Activity implements TJPlacementListener, TJPlacementVideoListener {
    public static TapJoyAdsInterstitial instance = null;
    private final AdsListenerManager.ListenerLogs listenerLogs;
    private Context context;
    private TJPlacement examplePlacement;
    private boolean isSdkConnected = false;
    private boolean isAdAvailable = false;

    public TapJoyAdsInterstitial(AdsListenerManager.ListenerLogs listenerLogs) {
        this.listenerLogs = listenerLogs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    public void initTapJoy(Context context, final String tapjoySDKKey) {
        Tapjoy.setDebugEnabled(true);

        listenerLogs.logs("TapJoy : initialized "+tapjoySDKKey);

        // NOTE: This is the only step required if you're an advertiser.
        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        Tapjoy.connect(context, tapjoySDKKey, connectFlags, new TJConnectListener() {
            @Override
            public void onConnectSuccess() {
                listenerLogs.logs("TapJoy : onConnectSuccess "+tapjoySDKKey);
                TapJoyAdsInterstitial.this.onConnectSuccess();
            }

            @Override
            public void onConnectFailure() {
                listenerLogs.logs("TapJoy : onConnectFailure "+tapjoySDKKey);
                TapJoyAdsInterstitial.this.onConnectFail();
            }
        });
    }



    public boolean isLoadedTapJoy() {
        if (isSdkConnected) {
            this.listenerLogs.logs("TapJoy isSdkConnected "+isSdkConnected);
            return true;
        } else {
            this.listenerLogs.logs("TapJoy isSdkConnected "+isSdkConnected);
            return false;
        }
    }

    public void showTapJoy() {
        this.listenerLogs.logs("TapJoy requestPlacement isAdAvailable "+isAdAvailable);
        if (isAdAvailable) {
            requestPlacement();
        }
    }

    /**
     * Handles a successful connect to Tapjoy. Pre-loads direct play placement
     * and sets up Tapjoy listeners
     */
    public void onConnectSuccess() {
        isSdkConnected = true;
        listenerLogs.logs("Tapjoy SDK connected");
    }

    /**
     * Handles a failed connect to Tapjoy
     */
    public void onConnectFail() {
        isSdkConnected = false;
        listenerLogs.logs("Tapjoy connect call failed");
    }

    /**
     * Notify Tapjoy the start of this activity for session tracking
     */
    @Override
    protected void onStart() {
        super.onStart();
        Tapjoy.onActivityStart(this);
    }

    /**
     * Notify Tapjoy the end of this activity for session tracking
     */
    @Override
    protected void onStop() {
        super.onStop();
        Tapjoy.onActivityStop(this);
    }


    private void requestPlacement() {
        // Grab placement name from input field
        String placementName = "video_unit";

        examplePlacement = Tapjoy.getPlacement(placementName, TapJoyAdsInterstitial.this);

        // Set Video Listener to anonymous callback
        examplePlacement.setVideoListener(new TJPlacementVideoListener() {
            @Override
            public void onVideoStart(TJPlacement placement) {
                listenerLogs.logs("TapJoy Video has started has started for: " + placement.getName());
            }

            @Override
            public void onVideoError(TJPlacement placement, String message) {
                listenerLogs.logs("TapJoy Video error: " + message + " for " + placement.getName());
            }

            @Override
            public void onVideoComplete(TJPlacement placement) {
                listenerLogs.isClosedInterAds();
                listenerLogs.logs("TapJoy Video has completed for: " + placement.getName());
            }

        });

        // Construct TJPlacement
        examplePlacement = Tapjoy.getPlacement(placementName, new TJPlacementListener() {
            @Override
            public void onRequestSuccess(TJPlacement placement) {

                if (placement.isContentAvailable()) {
                    examplePlacement.showContent();
                    listenerLogs.logs("TapJoy showContent");
                }

            }

            @Override
            public void onRequestFailure(TJPlacement placement, TJError error) {
                listenerLogs.logs("TapJoy onRequestFailure for placement " + placement.getName() + " -- error: " + error.message);
            }

            @Override
            public void onContentReady(TJPlacement placement) {
                listenerLogs.logs("TapJoy onContentReady for placement, show video");
                examplePlacement.showContent();
            }

            @Override
            public void onContentShow(TJPlacement placement) {
                listenerLogs.logs("TapJoy onContentShow for placement " + placement.getName());
            }

            @Override
            public void onContentDismiss(TJPlacement placement) {
                listenerLogs.logs("TapJoy onContentDismiss for placement " + placement.getName());
            }

            @Override
            public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
                // Dismiss the placement content
                Intent intent = new Intent(getApplicationContext(), TapJoyAdsInterstitial.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                String message = "onPurchaseRequest -- product id: " + productId + ", token: " + request.getToken() + ", request id: " + request.getRequestId();
                AlertDialog dialog = new AlertDialog.Builder(TapJoyAdsInterstitial.this).setTitle("Got on purchase request").setMessage(message)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

                // Your app must call either callback.completed() or callback.cancelled() to complete the lifecycle of the request
                request.completed();
            }

            @Override
            public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
                // Dismiss the placement content
                Intent intent = new Intent(getApplicationContext(), TapJoyAdsInterstitial.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                String message = "onRewardRequest -- item id: " + itemId + ", quantity: " + quantity + ", token: " + request.getToken() + ", request id: " + request.getRequestId();
                AlertDialog dialog = new AlertDialog.Builder(TapJoyAdsInterstitial.this).setTitle("Got on reward request").setMessage(message)
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

                // Your app must call either callback.completed() or callback.cancelled() to complete the lifecycle of the request
                request.completed();
            }
        });

        // Add this class as a video listener
        examplePlacement.setVideoListener(this);

        listenerLogs.logs("TapJoy Requesting placement content");
        examplePlacement.requestContent();
    }




    /*
     * TJPlacement callbacks
     */
    @Override
    public void onRequestSuccess(TJPlacement placement) {
        // If content is not available you can note it here and act accordingly as best suited for your app
        listenerLogs.logs("Tapjoy on request success, contentAvailable: " + placement.isContentAvailable());
        if (!placement.isContentAvailable()){
            isAdAvailable = false;
        } else {
            isAdAvailable = true;
        }
    }

    @Override
    public void onRequestFailure(TJPlacement placement, TJError error) {
        listenerLogs.logs("Tapjoy send event " + placement.getName() + " failed with error: " + error.message);
    }

    @Override
    public void onContentReady(TJPlacement placement) {
    }

    @Override
    public void onContentShow(TJPlacement placement) {
    }

    @Override
    public void onContentDismiss(TJPlacement placement) {
        listenerLogs.logs("Tapjoy direct play content did disappear");
    }

    @Override
    public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
    }

    @Override
    public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
    }

    /**
     * Video listener callbacks
     */
    @Override
    public void onVideoStart(TJPlacement placement) {
        listenerLogs.logs("TapJoy Video has started has started for: " + placement.getName());
    }

    @Override
    public void onVideoError(TJPlacement placement, String errorMessage) {
        listenerLogs.logs("TapJoy Video error: " + errorMessage +  " for " + placement.getName());
    }

    @Override
    public void onVideoComplete(TJPlacement placement) {
        listenerLogs.isClosedInterAds();
        listenerLogs.logs("TapJoy Video has completed for: " + placement.getName());
    }



    public synchronized static TapJoyAdsInterstitial getInstance(AdsListenerManager.ListenerLogs listenerLogs) {
        if (instance == null) instance = new TapJoyAdsInterstitial(listenerLogs);
        return instance;
    }


}
