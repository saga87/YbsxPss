package com.ning.ybsxpss.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxn on 2017/7/18.
 */

public class ActivityCollector {
    //存储Activity的List
    public static List<Activity> activities = new ArrayList<Activity>();

    //添加Activity
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移出Activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁所有Activity
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}