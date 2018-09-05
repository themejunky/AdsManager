package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.utils.AdsListenerManager;

public class FacebookNativeAds extends NativeBase {
    private static final String TAG = "InfoAds" ;
    private static FacebookNativeAds instance = null;
    private AdsListenerManager.NativeListener nativeListener;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;

    public FacebookNativeAds (Context context,String keyFacebook, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.NativeListener nativeListener){
        nContext = context;
        this.listenerLogs = listenerLogs;
        this.nativeListener = nativeListener;
        //AdSettings.addTestDevice("DDEDEA49939260C29B6F6F7B48C9BFF4");
        AdSettings.addTestDevice("0b6d426f86e3d921cb483a300febbc76");
        //init(keyFacebook);
        initFacebookNative(keyFacebook, context);
    }

    //public void initFacebookNative(final View view, String keynativeFacebook, final Context activity){
    public void initFacebookNative(String keyFacebook, final Context context){
        nativeAd = new NativeAd(context, keyFacebook);
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
                inflateAd(nativeAd, context);
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

    private void inflateAd(NativeAd nativeAd, Context context) {
        nativeAd.unregisterView();

        mAdView = mInflateLayout(R.layout.native_ad_layout_1);
        nativeAdContainer = mAdView.findViewById(R.id.nContainer);

        nativeAdContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater = LayoutInflater.from(context);
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_1, nativeAdContainer, false);
        nativeAdContainer.addView(adView);

        // Add the AdChoices icon
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdChoicesView adChoicesView = new AdChoicesView(context, nativeAd, true);
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



    public static FacebookNativeAds getmInstance(Context activity,String keyFacebook, AdsListenerManager.ListenerLogs listenerLogs,AdsListenerManager.NativeListener nativeListener) {
        if(instance == null) {
            instance = new FacebookNativeAds(activity,keyFacebook,listenerLogs,nativeListener);
        }
        return instance;
    }
}
