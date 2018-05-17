package themejunky.module_adsmanager.utils;

import android.view.View;

/**
 * Created by Junky2 on 5/17/2018.
 */

public class Stuff {

    public static boolean isNull(Object object){
        if(object==null){
            return true;
        }else {
            return false;
        }
    }
    public static boolean isNullView(View object){
        if(object==null){
            return true;
        }else {
            return false;
        }
    }
}
