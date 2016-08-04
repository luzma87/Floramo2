package com.lzm.Cajas.map;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.db.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.List;

public class EspecieLoader implements Runnable {
    private final FloramoMapFragment floramoMapFragment;
    private MainActivity context;

    private Especie especie;

    public EspecieLoader(FloramoMapFragment floramoMapFragment, MainActivity mainActivity, Especie especie) {
        this.context = mainActivity;
        this.especie = especie;
        this.floramoMapFragment = floramoMapFragment;
    }

    @Override
    public void run() {
        List<Foto> fotos = Foto.findAllByEspecieWithdCoords(context, especie);
        String nombre = especie.getNombreCientifico();
        for (Foto foto : fotos) {
            LatLng latLng = new LatLng(foto.getLatitud(), foto.getLongitud());
            Bitmap bitmap;
            String path = "new/" + foto.getPath().replaceAll("-", "_").toLowerCase();
            try {
                bitmap = ResourcesHelper.getEncyclopediaAssetByName(context, path);
                EspecieMarker especieMarker = new EspecieMarker(nombre, bitmap, latLng);
                floramoMapFragment.addSpeciesMarker(especieMarker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
