package com.lzm.Cajas.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.Cajas.R;
import com.lzm.Cajas.customComponents.TouchImageView;
import com.lzm.Cajas.models.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * http://www.androidhive.info/2013/09/android-fullscreen-image-slider-with-swipe-and-pinch-zoom-gestures/
 */
public class FullScreenImageAdapter extends PagerAdapter {

    private Activity context;
    private ArrayList<Foto> photos;

    public FullScreenImageAdapter(Activity activity, ArrayList<Foto> photos) {
        this.context = activity;
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup viewLayout = (ViewGroup)inflater.inflate(R.layout.detail_fullscreen_image_layout, container, false);

        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        Foto foto = photos.get(position);
        if (foto != null) {
            String path = "v2/full_size/" + foto.getPath();
            try {
                Bitmap bitmap = ResourcesHelper.getEncyclopediaAssetByName(context, path);
                imgDisplay.setImageBitmap(bitmap);
            } catch (IOException e) {
                System.out.println("........................FULL SCREEN .................................");
                e.printStackTrace();
                System.out.println(".........................................................");
            }
        }

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "pepe";
    }
}
