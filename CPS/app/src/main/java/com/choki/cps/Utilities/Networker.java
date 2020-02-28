package com.choki.cps.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class Networker {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
