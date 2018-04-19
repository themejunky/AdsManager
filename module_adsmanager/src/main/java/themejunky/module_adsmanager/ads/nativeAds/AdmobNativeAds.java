package themejunky.module_adsmanager.ads.nativeAds;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.List;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.ads.AdsListenerManager;
import themejunky.module_adsmanager.utils.ConstantsTypeAmobAds;


/**
 * Created by Junky2 on 9/26/2017.
 */

public class AdmobNativeAds  {

    private static final String LOG_TAG = "Module_ManagerNativeAds" ;
    public static AdmobNativeAds instance = null;
    private final Context activity;
    private AdsListenerManager.ListenerAds listenerAds;
    private AdsListenerManager.ListenerLogs listenerLogs;
    public NativeExpressAdView mAdView;
    private AdLoader adLoader;
    public boolean isLoaded=false;



    public AdmobNativeAds(Context activity, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds){
        this.activity = activity;
        this.listenerLogs = listenerLogs;
        this.listenerAds = listenerAds;

    }


    public void initAdmobNativeAdvance(final View view, String idUnitAdmob,int type ){
        Log.d("dasdadadas","0 : "+view.getTag());
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if(type== ConstantsTypeAmobAds.AD_CONTENT){

            Log.d("infoadmob","initAdmobNativeAdvance 1-2");

            adLoader = new AdLoader.Builder(activity, idUnitAdmob)
                    .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                        @Override
                        public void onContentAdLoaded(NativeContentAd contentAd) {


                            Log.d("infoadmob","initAdmobNativeAdvance 1-2-1");

                            listenerLogs.logs("Admob: onContentAdLoaded");
                            Log.d("Tesada","onContentAdLoaded");
                            RelativeLayout frameLayout = view.findViewById(R.id.containerAdmobNativeAds);
                            NativeContentAdView adView = (NativeContentAdView)inflater.inflate(R.layout.ad_content, null);
                            populateContentAdView(contentAd, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);

                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {

                            Log.d("infoadmob","initAdmobNativeAdvance 1-2-2 : "+errorCode);


                            switch (errorCode) {
                                case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                                    listenerLogs.logs("Admob: Failed to received ad! Internal error code: '%s'."+ errorCode);
                                    break;
                                case AdRequest.ERROR_CODE_INVALID_REQUEST:
                                    listenerLogs.logs("Admob: Failed to received ad! Invalid request error code: '%s'."+ errorCode);
                                    break;
                                case AdRequest.ERROR_CODE_NETWORK_ERROR:
                                    listenerLogs.logs("Admob: Failed to received ad! Network error code: '%s'."+ errorCode);
                                    break;
                                case AdRequest.ERROR_CODE_NO_FILL:
                                    listenerLogs.logs("Admob: Failed to received ad! No fill error code: '%s'."+ errorCode);
                                    break;
                                default:
                                    listenerLogs.logs("Admob: Failed to received ad! Error code: '%s'."+ errorCode);
                            }
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build();

            Log.d("infoadmob","initAdmobNativeAdvance 1-2-3");


        }else if(type==ConstantsTypeAmobAds.APP_INSTAL){

            Log.d("dasdadadas","initAdmobNativeAdvance 1-1");


            adLoader = new AdLoader.Builder(activity, idUnitAdmob)
                    .forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                        @Override
                        public void onAppInstallAdLoaded(NativeAppInstallAd appInstallAd) {

                            Log.d("dasdadadas","1 : "+view.getTag());

                            RelativeLayout frameLayout = view.findViewById(R.id.containerAdmobNativeAds);
                            NativeAppInstallAdView adView = (NativeAppInstallAdView)inflater.inflate(R.layout.ad_app_install, null);
                            populateAppInstallAdView(appInstallAd, adView);
                            frameLayout.removeAllViews();
                            frameLayout.addView(adView);

                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            Log.d("infoadmob","initAdmobNativeAdvance 1-1-2 : "+errorCode);
                            switch (errorCode) {
                                case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                                    listenerLogs.logs("Admob: Failed to received ad! Internal error code: '%s'."+ errorCode);
                                    break;
                                case AdRequest.ERROR_CODE_INVALID_REQUEST:
                                    listenerLogs.logs("Admob: Failed to received ad! Invalid request error code: '%s'."+ errorCode);
                                    break;
                                case AdRequest.ERROR_CODE_NETWORK_ERROR:
                                    listenerLogs.logs("Admob: Failed to received ad! Network error code: '%s'."+ errorCode);
                                    break;
                                case AdRequest.ERROR_CODE_NO_FILL:
                                    listenerLogs.logs("Admob: Failed to received ad! No fill error code: '%s'."+ errorCode);
                                    break;
                                default:
                                    listenerLogs.logs("Admob: Failed to received ad! Error code: '%s'."+ errorCode);
                            }
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build();
        }


        adLoader.loadAd(new AdRequest.Builder().build());


    }

    public void initAdmobNativeAdvance(final View view, String idUnitAdmob ){
        Log.d("infoadmob","initAdmobNativeAdvance 2");
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        Log.d("infoadmob","initAdmobNativeAdvance 21 : "+adLoader);


        adLoader = new AdLoader.Builder(activity, idUnitAdmob)
                .forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                    @Override
                    public void onContentAdLoaded(NativeContentAd contentAd) {

                        Log.d("infoadmob","initAdmobNativeAdvance 22");

                        listenerLogs.logs("Admob: onContentAdLoaded");
                        Log.d("infoadmob","onContentAdLoaded");
                        RelativeLayout frameLayout = view.findViewById(R.id.containerAdmobNativeAds);
                        NativeContentAdView adView = (NativeContentAdView) inflater.inflate(R.layout.ad_content, null);
                        populateContentAdView(contentAd, adView);

                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);

                    }
                })
                .forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                    @Override
                    public void onAppInstallAdLoaded(NativeAppInstallAd appInstallAd) {
                        Log.d("infoadmob","initAdmobNativeAdvance 23");
                        listenerLogs.logs("Admob: onAppInstallAdLoaded");
                        Log.d("infoadmob","infosasda onAppInstallAdLoaded ");

                        Log.d("cevadasda",""+view.getTag());


                        RelativeLayout frameLayout =view.findViewById(R.id.containerAdmobNativeAds);
                        NativeAppInstallAdView adView = (NativeAppInstallAdView) inflater
                                .inflate(R.layout.ad_app_install, null);
                        populateAppInstallAdView(appInstallAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);

                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {

                        Log.d("infoadmob","initAdmobNativeAdvance 24 : "+errorCode);

                        switch (errorCode) {
                            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                                listenerLogs.logs("Admob: Failed to received ad! Internal error code: '%s'."+ errorCode);
                                break;
                            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                                listenerLogs.logs("Admob: Failed to received ad! Invalid request error code: '%s'."+ errorCode);
                                break;
                            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                                listenerLogs.logs("Admob: Failed to received ad! Network error code: '%s'."+ errorCode);
                                break;
                            case AdRequest.ERROR_CODE_NO_FILL:
                                listenerLogs.logs("Admob: Failed to received ad! No fill error code: '%s'."+ errorCode);
                                break;
                            default:
                                listenerLogs.logs("Admob: Failed to received ad! Error code: '%s'."+ errorCode);
                        }
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();

        Log.d("infoadmob","initAdmobNativeAdvance 25");

        adLoader.loadAd(new AdRequest.Builder().build());

        Log.d("infoadmob","initAdmobNativeAdvance 26");

    }


    /**
     * Populates a {@link NativeAppInstallAdView} object with data from a given
     * {@link NativeAppInstallAd}.
     *
     * @param nativeAppInstallAd the object containing the ad's assets
     * @param adView             the view to be populated
     */
    private void populateAppInstallAdView(NativeAppInstallAd nativeAppInstallAd,
                                          NativeAppInstallAdView adView) {

        adView.setHeadlineView(adView.findViewById(R.id.appinstall_headline));
        // adView.setBodyView(adView.findViewById(R.id.appinstall_body));
        adView.setCallToActionView(adView.findViewById(R.id.appinstall_call_to_action));
        adView.setIconView(adView.findViewById(R.id.appinstall_app_icon));
        // adView.setPriceView(adView.findViewById(R.id.appinstall_price));
        adView.setStarRatingView(adView.findViewById(R.id.appinstall_stars));
        adView.setStoreView(adView.findViewById(R.id.appinstall_store));
        adView.setMediaView((MediaView) adView.findViewById(R.id.appinstall_media));
        //adView.setIconView(adView.findViewById(R.id.appinstall_image));


        if(adView.getHeadlineView()!=null){
            isLoaded=true;
      //      listenerAds.loadNativeAds("admob");
        }


        // Some assets are guaranteed to be in every NativeAppInstallAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
        //  ((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
        ((ImageView) adView.getIconView()).setImageDrawable(
                nativeAppInstallAd.getIcon().getDrawable());

        MediaView mediaView = adView.findViewById(R.id.appinstall_media);
        //ImageView mainImageView = adView.findViewById(R.id.appinstall_image);

       /* if (nativeAppInstallAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAppInstallAd.getPrice());
        }*/

        if (nativeAppInstallAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAppInstallAd.getStore());
        }

        if (nativeAppInstallAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAppInstallAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAppInstallAd);
    }

    /**
     * Populates a {@link NativeContentAdView} object with data from a given
     * {@link NativeContentAd}.
     *
     * @param nativeContentAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {


        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        // adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        //((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());
        if(adView.getHeadlineView()!=null){
            isLoaded=true;
           // listenerAds.loadNativeAds("admob");
        }

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            Log.d("infoicon"," nu Este");
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            Log.d("infoicon","  Este");
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
    }



    public static synchronized AdmobNativeAds getInstance(Context activity, AdsListenerManager.ListenerLogs listenerLogs, AdsListenerManager.ListenerAds listenerAds){
        if (instance==null){
            instance = new AdmobNativeAds(activity,listenerLogs,listenerAds);
        }
        return instance;

    }


}
