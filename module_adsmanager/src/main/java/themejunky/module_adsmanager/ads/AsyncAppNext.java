/*
package themejunky.module_adsmanager.ads;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnext.core.AppnextError;
import com.appnext.nativeads.MediaView;
import com.appnext.nativeads.NativeAd;
import com.appnext.nativeads.NativeAdListener;
import com.appnext.nativeads.NativeAdView;

import java.util.ArrayList;
import java.util.List;

import themejunky.module_adsmanager.R;
import themejunky.module_adsmanager.ads.nativeAds.AppnextNativeAds;


public class AsyncAppNext extends AsyncTask<String,Integer,View> {

    private AppnextNativeAds mInstance;
    private Context mContext;
    private NativeAd mNativeAd;
    private String mUnitId;

    private Button button;
    public MediaView mediaView;
    private TextView textView;
    private AdsListenerManager.NativeListener nativeListener;
    private ImageView imageView;
    private TextView description;
    private List<View> viewArrayList;
    private NativeAdView nativeAdView;
    public boolean isAppnextNativeLoaded;
    Activity mActivity;
    public AsyncAppNext(AppnextNativeAds nInstance, Context nContext, NativeAd nNativeAd,String nUnitId, Activity nActivity) {
        this.mInstance = nInstance;
        this.mContext = nContext;
       // this.mNativeAd = nNativeAd;
        this.mUnitId = nUnitId;
this.mActivity = nActivity;
        Log.d("testaree","Async constructor");
    }

    @Override
    protected void onPreExecute() {
        Log.d("testaree","Async onPreExecute");
    }


    @Override
    protected View doInBackground(String... strings) {
//        Log.d("testaree","Async doInBackground");
//        mActivity.getMainLooper().
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                NativeAd  mNativeAd = new NativeAd(mContext, mUnitId);
//            }
//        });

//
//        mNativeAd.setAdListener(new NativeAdListener() {
//            @Override
//            public void onAdLoaded(NativeAd nativeAd) {
//                super.onAdLoaded(nativeAd);
//                Log.d("testaree","3");
//                View mAdView = mInflateLayout(R.layout.ad_appnext);
//                setViews(mAdView);
//
//                mediaView.setMute(true);
//                mediaView.setAutoPLay(true);
//                mediaView.setClickEnabled(true);
//
//                nativeAd.setMediaView(mediaView);
//                nativeAd.downloadAndDisplayImage(imageView, nativeAd.getIconURL());
//                textView.setText(nativeAd.getAdTitle());
//                nativeAd.setMediaView(mediaView);
//                description.setText(nativeAd.getAdDescription());
//                nativeAd.registerClickableViews(viewArrayList);
//                nativeAd.setNativeAdView(nativeAdView);
//                isAppnextNativeLoaded = true;
//
//
//                if (mInstance!=null) {
//                    mInstance.setAdd(mAdView);
//                }
//
////                if(listenerAds!=null) listenerAds.loadedNativeAds("appnext");
////                listenerLogs.logs("Appnex Native: Loaded");
//            }
//
//            @Override
//            public void onAdClicked(NativeAd nativeAd) {
//                super.onAdClicked(nativeAd);
//                Log.d("testaree","4");
//            }
//
//            @Override
//            public void onError(NativeAd nativeAd, AppnextError appnextError) {
//                super.onError(nativeAd, appnextError);
//                Log.d("testaree","5");
//              //  listenerLogs.logs("Appnex Native: onError: " +appnextError.getErrorMessage());
//            }
//
//            @Override
//            public void adImpression(NativeAd nativeAd) {
//                super.adImpression(nativeAd);
//                Log.d("testaree","6");
//            }
//        });

        return null;
    }

    @Override
    protected void onPostExecute(View result) {
      //  super.onPostExecute(result);
    //    Log.d("testaree","Async onPostExecute");
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


    View mInflateLayout(int nLayout) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        View DialogInflateView = factory.inflate(nLayout, null);
        return DialogInflateView.findViewById(R.id.nContainer);
    }
}
*/
