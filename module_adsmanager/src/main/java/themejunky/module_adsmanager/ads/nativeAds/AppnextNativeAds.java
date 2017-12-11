package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.icu.text.BreakIterator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appnext.base.Appnext;
import com.appnext.core.AppnextError;
import com.appnext.nativeads.MediaView;
import com.appnext.nativeads.NativeAd;
import com.appnext.nativeads.NativeAdListener;
import com.appnext.nativeads.NativeAdRequest;
import com.appnext.nativeads.NativeAdView;


import org.w3c.dom.Text;

import java.util.ArrayList;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.ads.AdsListenerManager;

import static android.R.attr.button;

/**
 * Created by Junky2 on 12/11/2017.
 */

public class AppnextNativeAds {
    public static AppnextNativeAds instance = null;
    private final Context context;
    private final AdsListenerManager.ListenerLogs logsListener;
    private final AdsListenerManager.ListenerAds loadListener;
    public NativeAd nativeAd;
    public MediaView mediaView;
    private TextView textView;
    private TextView rating;
    private TextView description;
    private ImageView imageView;
    private Button button;
    private NativeAdView nativeAdView;
    private  ArrayList<View> viewArrayList;


    public AppnextNativeAds(Context context, AdsListenerManager.ListenerLogs logs, AdsListenerManager.ListenerAds loadListener) {
        this.context = context;
        this.logsListener = logs;
        this.loadListener = loadListener;

    }



    public void initAppnextNativeAdvance(final View view, String idUnitAppnext) {
        Appnext.init(context);
        mediaView.setMute(true);
        mediaView.setAutoPLay(true);
        mediaView.setClickEnabled(true);
        nativeAd = new NativeAd(context, idUnitAppnext);
        setViews(view);
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onAdLoaded(NativeAd nativeAd) {
                super.onAdLoaded(nativeAd);
                nativeAd.setMediaView(mediaView);
                logsListener.logs("Appnex: onAdLoaded");
                loadListener.loadNativeAds("appnext");
                nativeAd.downloadAndDisplayImage(imageView, nativeAd.getIconURL());
                textView.setText(nativeAd.getAdTitle());
                nativeAd.setMediaView(mediaView);
                rating.setText(nativeAd.getStoreRating());
                description.setText(nativeAd.getAdDescription());
                nativeAd.registerClickableViews(viewArrayList);
                nativeAd.setNativeAdView(nativeAdView);
            }

            @Override
            public void onAdClicked(NativeAd nativeAd) {
                super.onAdClicked(nativeAd);
            }

            @Override
            public void onError(NativeAd nativeAd, AppnextError appnextError) {
                super.onError(nativeAd, appnextError);
                logsListener.logs("Appnex: onError: " +appnextError.getErrorMessage());
            }

            @Override
            public void adImpression(NativeAd nativeAd) {
                super.adImpression(nativeAd);
            }
        });

        nativeAd.loadAd(new NativeAdRequest()
                // optional - config your ad request:
                .setPostback("")
                .setCategories("")
                .setCachingPolicy(NativeAdRequest.CachingPolicy.ALL)
                .setCreativeType(NativeAdRequest.CreativeType.ALL)
                .setVideoLength(NativeAdRequest.VideoLength.SHORT)
                .setVideoQuality(NativeAdRequest.VideoQuality.HIGH)
        );
    }
    private void setViews(View views) {
        nativeAdView = (NativeAdView) views.findViewById(R.id.na_view);
        imageView = (ImageView) views.findViewById(R.id.na_icon);
        textView = (TextView) views.findViewById(R.id.na_title);
        mediaView = (MediaView) views.findViewById(R.id.na_media);
        button = (Button) views.findViewById(R.id.install);
        rating = (TextView) views.findViewById(R.id.rating);
        description = (TextView) views.findViewById(R.id.description);
        viewArrayList = new ArrayList<>();
        viewArrayList.add(button);
        viewArrayList.add(mediaView);
    }


}