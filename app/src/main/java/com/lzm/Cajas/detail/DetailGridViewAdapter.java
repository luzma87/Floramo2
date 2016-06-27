package com.lzm.Cajas.detail;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.db.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.ArrayList;

public class DetailGridViewAdapter extends BaseAdapter {
    private MainActivity context;
    private ArrayList<Foto> photos = new ArrayList<>();
    private int imageWidth;

    public DetailGridViewAdapter(MainActivity activity, ArrayList<Foto> photos, int imageWidth) {
        this.context = activity;
        this.photos = photos;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.photos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

        Foto foto = photos.get(position);
        String path = "new/" + foto.getPath().replaceAll("-", "_").toLowerCase();
        try {
            Bitmap image = ResourcesHelper.getEncyclopediaAssetByName(context, path);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
            imageView.setImageBitmap(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageView;
    }
}
