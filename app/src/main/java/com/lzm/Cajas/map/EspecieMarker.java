package com.lzm.Cajas.map;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class EspecieMarker implements ClusterItem {

    private final LatLng coords;
    private final String nombre;
    private final Bitmap foto;

    public EspecieMarker(String nombre, Bitmap foto, LatLng coords) {
        this.nombre = nombre;
        this.foto = foto;
        this.coords = coords;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public String getName() {
        return nombre;
    }

    @Override
    public LatLng getPosition() {
        return coords;
    }
}
