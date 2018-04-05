package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.ads.AdsListenerManager;

public class FacebookNativeAds {

    private static final String TAG ="Module_ManagerNativeAds" ;
    private static FacebookNativeAds instance = null;
    private final Context activity;
    private AdsListenerManager.ListenerAds listenerAds;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;



    public FacebookNativeAds (Context activity, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds){
        this.activity = activity;
        this.listenerLogs = listenerLogs;
        this.listenerAds = listenerAds;
        AdSettings.addTestDevice("f5726d6130e7bc96ef669e32ea0ae59e");

    }

    public void initFacebookNative(final View view, String keynativeFacebook){
        nativeAd = new NativeAd(activity, keynativeFacebook);
        nativeAd.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                listenerLogs.logs("Facebook: error -  "+adError.getErrorMessage());
                Log.d(TAG,"Facebook errror: "+adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.d(TAG,"Facebook loaded");
                listenerAds.loadNativeAds("facebook");
                listenerLogs.logs("Facebook: onAdLoaded");
                nativeAdContainer = (LinearLayout) view.findViewById(R.id.native_ad_container);
                nativeAdContainer.setOrientation(LinearLayout.VERTICAL);
                LayoutInflater inflater = LayoutInflater.from(activity);
                // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
                adView = (LinearLayout) inflater.inflate(R.layout.native_ad, nativeAdContainer, false);
                nativeAdContainer.addView(adView);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdBody.setText(nativeAd.getAdBody());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                // Download and display the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and display the cover image.
                nativeAdMedia.setNativeAd(nativeAd);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = (LinearLayout) view.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(activity, nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
                nativeAd.registerViewForInteraction(nativeAdContainer,clickableViews);
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG,"Facebook clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeAd.loadAd();
    }

    public static synchronized FacebookNativeAds getmInstance(Context activity, AdsListenerManager.ListenerLogs listenerLogs,AdsListenerManager.ListenerAds listenerAds) {
        if(instance == null) {
            instance = new FacebookNativeAds(activity,listenerLogs,listenerAds);
        }

        return instance;
    }

}
