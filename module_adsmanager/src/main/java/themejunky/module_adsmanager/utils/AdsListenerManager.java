package themejunky.module_adsmanager.utils;

/**
 * Created by Alin on 11/16/2017.
 */

public interface AdsListenerManager {
    interface ListenerLogs {
        void logs(String logs);
    }

    interface ListenerAds {
        void loadedNativeAds(String type);
    }

    interface NativeListener {
        void nativeLoaded();

    }


}
