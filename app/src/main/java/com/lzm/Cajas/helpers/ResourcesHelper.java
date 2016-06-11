package com.lzm.Cajas.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by luz on 6/5/16.
 */
public class ResourcesHelper {
    public static int getImageResourceByName(Context c, String resourceName) {
        String packageName = c.getPackageName();
        return c.getResources().getIdentifier(resourceName, "drawable", packageName);
    }

    public static Bitmap getAssetByName(Context c, String resourceName) throws IOException {
        AssetManager assetManager = c.getAssets();
        InputStream is = assetManager.open("encyclopedia/" + resourceName);
        return BitmapFactory.decodeStream(is);
    }

    public static String getStringResourceByName(Context c, String resourceName) {
        String packageName = c.getPackageName();
        int resId = c.getResources().getIdentifier(resourceName, "string", packageName);
        if (resId == 0) {
            return resourceName;
        } else {
            return c.getString(resId);
        }
    }

}
