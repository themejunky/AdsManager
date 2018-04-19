package themejunky.module_adsmanager.managers;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junky2 on 4/19/2018.
 */

public class ManagerBase extends SlaveListener{
    protected View mContainerView;
    protected Context mContext;
    protected int next;
    protected static List<String> addsFlow = new ArrayList<>();
    protected String nameLogs;
    protected View containerNativeView;


    @Override
    public void logs(String logs) {
        Log.d(nameLogs, logs);
    }


}
