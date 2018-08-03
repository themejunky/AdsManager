package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.util.Log;
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

public class FacebookNativeAds extends NativeBase {

    private static final String TAG ="Module_ManagerNativeAds" ;
    private static FacebookNativeAds instance = null;
    private AdsListenerManager.NativeListener nativeListener;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeAd nativeAd;


    public FacebookNativeAds (Context mContext,String keyFacebook, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.NativeListener nativeListener){
        nContext = mContext;
        this.listenerLogs = listenerLogs;
        this.nativeListener = nativeListener;
        AdSettings.addTestDevice("DDEDEA49939260C29B6F6F7B48C9BFF4");
        init(keyFacebook);
    }

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

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeAd.loadAd();
    }

    public static FacebookNativeAds getmInstance(Context activity,String keyFacebook, AdsListenerManager.ListenerLogs listenerLogs,AdsListenerManager.NativeListener nativeListener) {
        if(instance == null) {
            instance = new FacebookNativeAds(activity,keyFacebook,listenerLogs,nativeListener);
        }
        return instance;
    }
}
