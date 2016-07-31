package com.lzm.Cajas.map;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.db.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.List;

public class EspecieLoader implements Runnable {
    private MainActivity context;
    private Especie especie;

    public EspecieLoader(MainActivity mainActivity, Especie especie) {
        this.context = mainActivity;
        this.especie = especie;
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
                context.addSpeciesMarker(especieMarker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
