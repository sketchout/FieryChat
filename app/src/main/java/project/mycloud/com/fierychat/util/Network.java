package project.mycloud.com.fierychat.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by admin on 2016-08-09.
 */
public class Network {

    public static boolean isNormal(Context context) {
        boolean connected;

        ConnectivityManager conMgr =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        connected = conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();

        return connected;
    }
}
