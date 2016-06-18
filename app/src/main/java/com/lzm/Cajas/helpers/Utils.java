package com.lzm.Cajas.helpers;

import android.content.Context;

/**
 * Created by luz on 6/18/16.
 */
public class Utils {
    public static int dp2px(Context context, int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);

    }
}
