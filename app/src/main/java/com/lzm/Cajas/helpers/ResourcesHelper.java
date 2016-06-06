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
    public static int getImageResourceByName(Context c, String aString) {
        String packageName = c.getPackageName();
        int resId = c.getResources().getIdentifier(aString, "drawable", packageName);
        return resId;
    }

    public static Bitmap getAssetByName(Context c, String aString) throws IOException {
        AssetManager assetManager = c.getAssets();
        InputStream is = assetManager.open("encyclopedia/" + aString);
        return BitmapFactory.decodeStream(is);
    }


}
