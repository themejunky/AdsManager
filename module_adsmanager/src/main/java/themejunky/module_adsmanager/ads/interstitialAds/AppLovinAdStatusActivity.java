package themejunky.module_adsmanager.ads.interstitialAds;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AppLovinAdStatusActivity extends AppCompatActivity
{

    protected TextView adStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );

        // TODO: use theme color
        getSupportActionBar().setBackgroundDrawable( new ColorDrawable( 0xDD0A83AA ) );
        getSupportActionBar().show();
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
    }

    protected void log(final String message)
    {
        if ( adStatusTextView != null )
        {
            runOnUiThread( new Runnable()
            {
                @Override
                public void run()
                {
                    adStatusTextView.setText( message );
                }
            } );
        }
        System.out.println( message );
    }
}