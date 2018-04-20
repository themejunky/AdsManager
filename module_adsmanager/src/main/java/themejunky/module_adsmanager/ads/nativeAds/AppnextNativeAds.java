package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnext.base.Appnext;
import com.appnext.core.AppnextError;
import com.appnext.nativeads.MediaView;
import com.appnext.nativeads.NativeAd;
import com.appnext.nativeads.NativeAdListener;
import com.appnext.nativeads.NativeAdRequest;
import com.appnext.nativeads.NativeAdView;

import java.util.ArrayList;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.ads.AdsListenerManager;

/**
 * Created by Junky2 on 12/11/2017.
 */

public class AppnextNativeAds extends NativeBase {
    public static AppnextNativeAds instance = null;
    private AdsListenerManager.NativeListener nativeListener;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeAd nativeAd;
    public MediaView mediaView;
    private TextView textView;
    private TextView description;
    private ImageView imageView;
    private Button button;
    private NativeAdView nativeAdView;
    private  ArrayList<View> viewArrayList;
    public boolean isAppnextNativeLoaded;

    public AppnextNativeAds(Context context,String idUnitAppnext, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.NativeListener nativeListener) {
        nContext = context;
        this.listenerLogs = listenerLogs;
        this.nativeListener = nativeListener;
        initAppnextNativeAdvance(idUnitAppnext);
    }



    public void initAppnextNativeAdvance(String idUnitAppnext) {
        Appnext.init(nContext);

        mAdView = mInflateLayout(R.layout.ad_appnext);
        setViews(mAdView);

        mediaView.setMute(true);
        mediaView.setAutoPLay(true);
        mediaView.setClickEnabled(true);
        nativeAd = new NativeAd(nContext, idUnitAppnext);

        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onAdLoaded(NativeAd nativeAd) {
                super.onAdLoaded(nativeAd);
                nativeAd.setMediaView(mediaView);
                nativeAd.downloadAndDisplayImage(imageView, nativeAd.getIconURL());
                textView.setText(nativeAd.getAdTitle());
                nativeAd.setMediaView(mediaView);
                description.setText(nativeAd.getAdDescription());
                nativeAd.registerClickableViews(viewArrayList);
                nativeAd.setNativeAdView(nativeAdView);
                if(nativeAd.getAdTitle()!=null){
                    listenerLogs.logs("Appnex Native: Loaded");
                    isAppnextNativeLoaded = true;
                    if(listenerAds!=null) listenerAds.loadedNativeAds("appnext");
                    nativeListener.nativeLoaded();

                }

            }

            @Override
            public void onAdClicked(NativeAd nativeAd) {
                super.onAdClicked(nativeAd);
            }

            @Override
            public void onError(NativeAd nativeAd, AppnextError appnextError) {
                super.onError(nativeAd, appnextError);
                listenerLogs.logs("Appnex Native: onError: " +appnextError.getErrorMessage());
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
        description = (TextView) views.findViewById(R.id.description);
        viewArrayList = new ArrayList<>();
        viewArrayList.add(button);
        viewArrayList.add(mediaView);
    }


    public static synchronized AppnextNativeAds getInstance(Context activity,String idUnitAppnext, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.NativeListener nativeListener){
        if (instance==null){
            instance = new AppnextNativeAds(activity,idUnitAppnext,listenerLogs,nativeListener);
        }
        return instance;

    }

}
