package themejunky.module_adsmanager;

/**
 * Created by Junky2 on 8/9/2018.
 */

public class ListenerContract {
    public interface ListenerIntern {
        void isInterstitialClosed();
        void somethingReloaded(String whatIsLoaded);

    }
    public interface AdsInterstitialListener{
        void afterInterstitialIsClosed(String action);
    }

    public interface NoAdsLoaded{
        void noAdsLoaded(String action);
    }

}
