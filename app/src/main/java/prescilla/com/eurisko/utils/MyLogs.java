package prescilla.com.eurisko.utils;

import android.util.Log;


public class MyLogs {

    public static final String TAG = ">>>>>>>>>>>>>>>>";

    public static boolean debugEnable = true;
    public static boolean forceDebugEnable = true;

    public static void debug(String debug) {

        if (debugEnable)
            Log.d(TAG, debug);
    }

    public static void info(String info) {

        if (debugEnable)
            Log.i(TAG, info);
    }


    public static void error(String error) {
        if (error != null)
            if (debugEnable)
                Log.e(TAG, error);
    }

    public static void time(String error) {

        if (debugEnable)
            Log.i("TIME::", error);
    }

    public static void forceError(String error) {
        if (error != null)
            if (forceDebugEnable)
                Log.e(TAG, error);
    }

}
