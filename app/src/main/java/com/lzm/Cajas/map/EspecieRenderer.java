package com.lzm.Cajas.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;

public class EspecieRenderer<T extends EspecieMarker> extends DefaultClusterRenderer<T> {
    private final IconGenerator iconGenerator;
    private final IconGenerator clusterIconGenerator;
    private final ImageView imageView;
    private final int dimension;
    MainActivity mainActivity;

    public EspecieRenderer(Context context, GoogleMap map, ClusterManager<T> clusterManager) {
        super(context, map, clusterManager);
        this.mainActivity = (MainActivity) context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        iconGenerator = new IconGenerator(context);
        clusterIconGenerator = new IconGenerator(context);

        View multiProfile = inflater.inflate(R.layout.marker_layout, null);
        clusterIconGenerator.setContentView(multiProfile);

        imageView = new ImageView(context);
        dimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(dimension, dimension));
        int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);
        imageView.setPadding(padding, padding, padding, padding);
        iconGenerator.setContentView(imageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(EspecieMarker especieMarker, MarkerOptions markerOptions) {
        Bitmap icon = especieMarker.getFoto();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
                .title(especieMarker.getName());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<T> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        float currentZoom = mainActivity.getClusterZoom();
        return currentZoom < 11.5 && cluster.getSize() >= 5;
    }

}
