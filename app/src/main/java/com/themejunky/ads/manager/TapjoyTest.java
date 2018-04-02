package com.themejunky.ads.manager;

import java.util.Hashtable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tapjoy.TJActionRequest;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.Tapjoy;
import com.tapjoy.TJAwardCurrencyListener;
import com.tapjoy.TapjoyConnectFlag;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJEarnedCurrencyListener;
import com.tapjoy.TJGetCurrencyBalanceListener;
import com.tapjoy.TapjoyLog;
import com.tapjoy.TJSpendCurrencyListener;
import com.tapjoy.TJPlacementVideoListener;

@SuppressLint("NewApi")
public class TapjoyTest extends Activity implements TJPlacementListener, TJPlacementVideoListener {
    public static final String TAG = "TapjoyEasyApp";

    // UI elements
    private String displayText = "";
    private Button getDirectPlayVideoAd;
    private TextView outputTextView;

    // Tapjoy Placements
    private TJPlacement directPlayPlacement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // This button displays a direct play video
        getDirectPlayVideoAd = (Button) findViewById(R.id.buttonGetDirectPlayVideoAd);
        getDirectPlayVideoAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDirectPlayContent();
            }
        });


        connectToTapjoy();

    }

    /**
     * Attempts to connect to Tapjoy
     */
    private void connectToTapjoy() {
        // OPTIONAL: For custom startup flags.
        Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
        connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "true");

        // If you are not using Tapjoy Managed currency, you would set your own user ID here.
        //	connectFlags.put(TapjoyConnectFlag.USER_ID, "A_UNIQUE_USER_ID");

        // Connect with the Tapjoy server.  Call this when the application first starts.
        // REPLACE THE SDK KEY WITH YOUR TAPJOY SDK Key.

        //String tapjoySDKKey = "NUXZT_MYRLGhxJC_4oD8zAECLrhN9auYWDqtmkVEBYg3lGQKPaEJvuoJAJdm";
        String tapjoySDKKey = "kF1-o4yERMWpveyA3Bw9bwECvScMFXYU8iEs1BbsHzWLYkre7X_bFMJH92Tn";

        //Tapjoy.setGcmSender("34027022155");

        //Tapjoy.connect(this.getApplicationContext(), "NUXZT_MYRLGhxJC_4oD8zAECLrhN9auYWDqtmkVEBYg3lGQKPaEJvuoJAJdm", connectFlags, this);

        // NOTE: This is the only step required if you're an advertiser.
        Tapjoy.connect(TapjoyTest.this, tapjoySDKKey, connectFlags, new TJConnectListener() {
            @Override
            public void onConnectSuccess() {

                TapjoyTest.this.onConnectSuccess();
                showDirectPlayContent();
            }

            @Override
            public void onConnectFailure() {

                TapjoyTest.this.onConnectFail();
            }
        });
    }

    /**
     * Handles a successful connect to Tapjoy. Pre-loads direct play placement
     * and sets up Tapjoy listeners
     */
    public void onConnectSuccess() {
        updateTextInUI("Tapjoy SDK connected");

        // Start preloading direct play event upon successful connect
        //directPlayPlacement = Tapjoy.getPlacement("Content for AppLaunch testing by Tapjoy", this);
        directPlayPlacement = Tapjoy.getPlacement("interstitial", this);

        // Set Video Listener to anonymous callback
        directPlayPlacement.setVideoListener(new TJPlacementVideoListener() {
            @Override
            public void onVideoStart(TJPlacement placement) {
                Log.i(TAG, "Tapjoy SDK Video has started has started for: " + placement.getName());
            }

            @Override
            public void onVideoError(TJPlacement placement, String message) {
                Log.i(TAG, "Tapjoy SDK Video error: " + message + " for " + placement.getName());
            }

            @Override
            public void onVideoComplete(TJPlacement placement) {
                Log.i(TAG, "Tapjoy SDK Video has completed for: " + placement.getName());

                // Best Practice: We recommend calling getCurrencyBalance as often as possible so the user�s balance is always up-to-date.
            }

        });

        directPlayPlacement.requestContent();
    }

    /**
     * Handles a failed connect to Tapjoy
     */
    public void onConnectFail() {
        Log.e(TAG, "Tapjoy SDK Tapjoy connect call failed");
        updateTextInUI("Tapjoy SDK Tapjoy connect failed!");
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


    private void showDirectPlayContent() {
        // Check if content is available and if it is ready to show
        if (directPlayPlacement.isContentAvailable()) {
            if (directPlayPlacement.isContentReady()) {
                directPlayPlacement.showContent();
            } else {
                updateTextInUI("Tapjoy SDK Direct play video not ready to show");
            }

        } else {
            updateTextInUI("No direct play video to show");
        }
    }




    /*
     * TJPlacement callbacks
     */
    @Override
    public void onRequestSuccess(TJPlacement placement) {
        // If content is not available you can note it here and act accordingly as best suited for your app
        Log.i(TAG, "Tapjoy SDK on request success, contentAvailable: " + placement.isContentAvailable());
    }

    @Override
    public void onRequestFailure(TJPlacement placement, TJError error) {
        Log.i(TAG, "Tapjoy SDK send event " + placement.getName() + " failed with error: " + error.message);
    }

    @Override
    public void onContentReady(TJPlacement placement) {
    }

    @Override
    public void onContentShow(TJPlacement placement) {
    }

    @Override
    public void onContentDismiss(TJPlacement placement) {
        Log.i(TAG, "Tapjoy SDK direct play content did disappear");


        // Begin preloading the next placement after the previous one is dismissed
        directPlayPlacement = Tapjoy.getPlacement("video_unit", this);

        // Set Video Listener to anonymous callback
        directPlayPlacement.setVideoListener(new TJPlacementVideoListener() {
            @Override
            public void onVideoStart(TJPlacement placement) {
                Log.i(TAG, "Tapjoy SDK Video has started has started for: " + placement.getName());
            }

            @Override
            public void onVideoError(TJPlacement placement, String errorMessage) {
                Log.i(TAG, "Tapjoy SDK Video error: " + errorMessage +  " for " + placement.getName());
            }

            @Override
            public void onVideoComplete(TJPlacement placement) {
                Log.i(TAG, "Tapjoy SDK Video has completed for: " + placement.getName());
            }
        });

        directPlayPlacement.requestContent();
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
        Log.i(TAG, "Tapjoy SDK Video has started has started for: " + placement.getName());
    }

    @Override
    public void onVideoError(TJPlacement placement, String errorMessage) {
        Log.i(TAG, "Tapjoy SDK Video error: " + errorMessage +  " for " + placement.getName());
    }

    @Override
    public void onVideoComplete(TJPlacement placement) {
        Log.i(TAG, "Tapjoy SDK Video has completed for: " + placement.getName());

    }


    /**
     * Update the text view on the UI thread
     *
     * @param text
     *            text to display in UI
     */
    private void updateTextInUI(final String text) {
        displayText = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (outputTextView != null) {
                    outputTextView.setText(text);
                }
            }
        });
    }

    /**
     * Helper function to show a Toast to the user
     *
     * @param text
     *            text that you want to display in the Toast
     */
    private void showPopupMessage(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

}
