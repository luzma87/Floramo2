package com.lzm.Cajas.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luz on 6/12/16.
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
            String path = "full_size/" + foto.path.replaceAll("-", "_").toLowerCase();
            try {
                Bitmap bitmap = ResourcesHelper.getAssetByName(context, path);
                imgDisplay.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
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
