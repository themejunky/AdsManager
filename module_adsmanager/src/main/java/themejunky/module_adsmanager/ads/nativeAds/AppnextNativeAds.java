package themejunky.module_adsmanager.ads.nativeAds;

import android.content.Context;
import android.util.Log;
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
import com.appnext.nativeads.PrivacyIcon;

import java.util.ArrayList;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.utils.AdsListenerManager;

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
        init(idUnitAppnext);
    }

    private void init(String idUnitAppnext) {
        Log.d("testing","step 0 ");
        Appnext.init(nContext);
        Log.d("testing","step 1" );
        nativeAd = new NativeAd(nContext, idUnitAppnext);
        nativeAd.setPrivacyPolicyColor(PrivacyIcon.PP_ICON_COLOR_LIGHT);
        Log.d("testing","step 2 ");




        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onAdLoaded(NativeAd nativeAd) {
                super.onAdLoaded(nativeAd);
                Log.d("testaree","3");
                mAdView = mInflateLayout(R.layout.ad_appnext);
                setViews(mAdView);

                mediaView.setMute(true);
                mediaView.setAutoPLay(true);
                mediaView.setClickEnabled(true);

                nativeAd.setMediaView(mediaView);
                nativeAd.downloadAndDisplayImage(imageView, nativeAd.getIconURL());
                textView.setText(nativeAd.getAdTitle());
                nativeAd.setMediaView(mediaView);
                description.setText(nativeAd.getAdDescription());
                nativeAd.registerClickableViews(viewArrayList);
                nativeAd.setNativeAdView(nativeAdView);
                isAppnextNativeLoaded = true;

                if(listenerAds!=null) listenerAds.loadedNativeAds("appnext");
                listenerLogs.logs("Appnex Native: Loaded");
            }

            @Override
            public void onAdClicked(NativeAd nativeAd) {
                super.onAdClicked(nativeAd);
                Log.d("testaree","4");
            }

            @Override
            public void onError(NativeAd nativeAd, AppnextError appnextError) {
                super.onError(nativeAd, appnextError);
                Log.d("testaree","5");
                listenerLogs.logs("Appnex Native: onError: " +appnextError.getErrorMessage());
            }

            @Override
            public void adImpression(NativeAd nativeAd) {
                super.adImpression(nativeAd);
                Log.d("testaree","6");
            }
        });
        Log.d("testaree","7");

        nativeAd.loadAd(new NativeAdRequest()
                // optional - config your ad request:
                .setPostback("")
                .setCategories("")
                .setCachingPolicy(NativeAdRequest.CachingPolicy.STATIC_ONLY)
                .setCreativeType(NativeAdRequest.CreativeType.ALL)
                .setVideoLength(NativeAdRequest.VideoLength.SHORT)
                .setVideoQuality(NativeAdRequest.VideoQuality.HIGH)
        );
        Log.d("testaree","8");
    }
    private void setViews(View views) {
        nativeAdView = views.findViewById(R.id.na_view);
        imageView =  views.findViewById(R.id.na_icon);
        textView = views.findViewById(R.id.na_title);
        mediaView = views.findViewById(R.id.na_media);
        button = views.findViewById(R.id.install);
        description = views.findViewById(R.id.description);
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
