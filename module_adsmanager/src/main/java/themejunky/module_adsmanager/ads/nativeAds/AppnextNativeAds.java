package themejunky.module_adsmanager.ads.nativeAds;

import android.app.Activity;
import android.content.Context;
import android.icu.text.BreakIterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import themejunky.module_adsmanager.ads.interstitialAds.AppnextAdsInterstitial;

import static android.R.attr.button;

/**
 * Created by Junky2 on 12/11/2017.
 */

public class AppnextNativeAds {
    public static AppnextNativeAds instance = null;
    private final Context context;
    private AdsListenerManager.ListenerAds listenerAds;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeAd nativeAd;
    public MediaView mediaView;
    private TextView textView;
    private TextView rating;
    private TextView description;
    private ImageView imageView;
    private Button button;
    private NativeAdView nativeAdView;
    private  ArrayList<View> viewArrayList;
    private ProgressBar progressBar;
    private LayoutInflater factory;
    private View inflateView;
    private View view2;


    public AppnextNativeAds(Context context, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds) {
        this.context = context;
        this.listenerLogs = listenerLogs;
        this.listenerAds = listenerAds;
    }



    public void initAppnextNativeAdvance(final View view, String idUnitAppnext) {
        Appnext.init(context);

        LayoutInflater factory = LayoutInflater.from(context);
        View DialogInflateView = factory.inflate(R.layout.ad_appnext, null);
        RelativeLayout container = (RelativeLayout) DialogInflateView.findViewById(R.id.nContainer);
        setViews(DialogInflateView);
        ((RelativeLayout) view).addView(container);

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        factory = LayoutInflater.from(context);
//        view2 = inflater.inflate(R.layout.ad_appnext, null, false);
//        RelativeLayout mContainer = view2.findViewById(R.id.nContainer);


       // inflateView= factory.inflate(R.layout.ad_content,null);*/
    /*    setViews(view2);
        frameLayout.removeAllViews();
        frameLayout.addView(view2);*/

        mediaView.setMute(true);
        mediaView.setAutoPLay(true);
        mediaView.setClickEnabled(true);
        nativeAd = new NativeAd(context, idUnitAppnext);

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
                    listenerLogs.logs("Appnex: onAdLoaded");
                    listenerAds.loadNativeAds("appnext");
                    Log.d("loadsometd","is text Loaded");
                }else{
                    Log.d("loadsometd","is  text not Loaded");
                }


            }

            @Override
            public void onAdClicked(NativeAd nativeAd) {
                super.onAdClicked(nativeAd);
            }

            @Override
            public void onError(NativeAd nativeAd, AppnextError appnextError) {
                super.onError(nativeAd, appnextError);
                listenerLogs.logs("Appnex: onError: " +appnextError.getErrorMessage());
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

    public boolean isLoadedAppnextAds(){
        if(nativeAd.getAdTitle()!=null){
            return true;
        }else {
            return false;
        }
    }

    public static synchronized AppnextNativeAds getInstance(Context activity, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds){
        if (instance==null){
            instance = new AppnextNativeAds(activity,listenerLogs,listenerAds);
        }
        return instance;

    }

}
