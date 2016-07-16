package com.lzm.Cajas.map;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class EspecieMarker {

    private final LatLng coords;
    private final String nombre;
    private final Bitmap foto;

    public EspecieMarker(String nombre, Bitmap foto, LatLng coords) {
        this.nombre = nombre;
        this.foto = foto;
        this.coords = coords;
    }

    public LatLng getCoords() {
        return coords;
    }

    public String getNombre() {
        return nombre;
    }
}
