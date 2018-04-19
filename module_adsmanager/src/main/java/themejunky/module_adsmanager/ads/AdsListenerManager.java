package themejunky.module_adsmanager.ads;

/**
 * Created by Alin on 11/16/2017.
 */

public interface AdsListenerManager {


        interface ListenerLogs{
            void logs(String logs);
            void isClosedInterAds();
        }
        interface ListenerAds{
            void afterInterstitialIsClosed(String action);
            void loadedInterAds();
            void loadInterFailed();
            void loadNativeAds(String type);
        }
    interface ListenerNativeAds{
        void loadNativeAds(String type);
    }


}
