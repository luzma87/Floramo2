package com.lzm.Cajas.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class ResourcesHelper {
    public static int getImageResourceByName(Context c, String resourceName) {
        String packageName = c.getPackageName();
        return c.getResources().getIdentifier(resourceName, "drawable", packageName);
    }

    public static Bitmap getEncyclopediaAssetByName(Context c, String resourceName) throws IOException {
        return getAssetByName(c, "encyclopedia", resourceName);
    }

    public static Bitmap getAboutAssetByName(Context c, String resourceName) throws IOException {
        return getAssetByName(c, "about", resourceName);
    }

    public static Bitmap getAssetByName(Context c, String folder, String resourceName) throws IOException {
        AssetManager assetManager = c.getAssets();
        InputStream is = assetManager.open(folder + "/" + resourceName);
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
