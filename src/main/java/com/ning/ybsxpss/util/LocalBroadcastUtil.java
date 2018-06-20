package com.ning.ybsxpss.util;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.ning.ybsxpss.app.App;



/**
 * Created by fxn on 2017/12/21.
 */

public class LocalBroadcastUtil {
    /**
     * 发送我们的商品维护的广播
     */
    public static void sendshwhBroadcast(String action) {
        Intent intent = new Intent(action);
        LocalBroadcastManager.getInstance(App.getContext()).sendBroadcast(intent);
    }
}
