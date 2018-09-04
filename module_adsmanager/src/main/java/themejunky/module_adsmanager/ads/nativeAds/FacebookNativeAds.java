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
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.utils.AdsListenerManager;

public class FacebookNativeAds extends NativeBase {
    private final Context activity;
    private static final String TAG = "InfoAds" ;
    private static FacebookNativeAds instance = null;
    private AdsListenerManager.NativeListener nativeListener;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;

    public FacebookNativeAds (Context activity,String keyFacebook, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.NativeListener nativeListener){
        this.activity = activity;
        this.listenerLogs = listenerLogs;
        this.nativeListener = nativeListener;
        //AdSettings.addTestDevice("DDEDEA49939260C29B6F6F7B48C9BFF4");
        AdSettings.addTestDevice("0b6d426f86e3d921cb483a300febbc76");
        //init(keyFacebook);
        initFacebookNative(keyFacebook, activity);
    }

    //public void initFacebookNative(final View view, String keynativeFacebook, final Context activity){
    public void initFacebookNative(String keynativeFacebook, final Context activity){
        nativeAd = new NativeAd(activity, keynativeFacebook);
        AdSettings.addTestDevice("af893730-8d04-4ed6-8d88-6782fbd56306");
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
                //inflateAd(view, nativeAd, activity);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                listenerAds.loadedNativeAds("facebook");
                listenerLogs.logs("Facebook Native: onAdLoaded");
                //loadListener.loadNativeAds("facebook");
                //logsListener.logs("Facebook: onAdLoaded");
                inflateAd(nativeAd, activity);
                // Native ad is loaded and ready to be displayed
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        });

        Log.d(TAG, "Native ad load");
        // Request an ad
        nativeAd.loadAd();
    }

    private void inflateAd(NativeAd nativeAd, Context activity) {

        nativeAd.unregisterView();
        // Add the Ad view into the ad container.
        nativeAdContainer = (LinearLayout) adView.findViewById(R.id.native_ad_container);
        nativeAdContainer.setVisibility(View.VISIBLE);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdContainer, false);
        nativeAdContainer.addView(adView);

/*
        nativeAdContainer = (LinearLayout) view.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        //adView = (LinearLayout) inflater.inflate(R.layout.native_ad, nativeAdContainer, false);
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdContainer, false);
        nativeAdContainer.addView(adView);
        */

        // Add the AdChoices icon
        LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(activity, nativeAd, true);
        adChoicesContainer.addView(adChoicesView, 0);

        // Create native UI using the ad metadata.
        AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);
    }

    public void dismissAd() {
        nativeAdContainer.setVisibility(View.GONE);
        nativeAd.unregisterView();
        nativeAd.destroy();
    }


    /*
    private void init(String keynativeFacebook){
        nativeAd = new NativeAd(nContext, keynativeFacebook);
        nativeAd.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                listenerLogs.logs("Facebook Native: error -  "+adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                mAdView = mInflateLayout(R.layout.native_ad);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon =  mAdView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle =  mAdView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia =  mAdView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext =  mAdView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody =  mAdView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = mAdView.findViewById(R.id.native_ad_call_to_action);

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
                LinearLayout adChoicesContainer = mAdView.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(nContext, nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
                nativeAd.registerViewForInteraction(mAdView,clickableViews);

                if(listenerAds!=null){
                    listenerAds.loadedNativeAds("facebook");
                }
                listenerLogs.logs("Facebook Native: onAdLoaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG,"Facebook clicked");
            }

        });
        nativeAd.loadAd();
    }
    */

    public static FacebookNativeAds getmInstance(Context activity,String keyFacebook, AdsListenerManager.ListenerLogs listenerLogs,AdsListenerManager.NativeListener nativeListener) {
        if(instance == null) {
            instance = new FacebookNativeAds(activity,keyFacebook,listenerLogs,nativeListener);
        }
        return instance;
    }
}
