package com.lzm.Cajas.map;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.enums.FloramoFragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FloramoMapFragment extends Fragment implements OnMapReadyCallback {
    private static final String SPECIES_ID = "speciesId";

    private Long speciesId;

    MapView mapView;
    private ClusterManager<EspecieMarker> clusterManager;
    private float clusterZoom;
    private MainActivity context;

    public FloramoMapFragment() {
    }

    public static FloramoMapFragment newInstance() {
        return new FloramoMapFragment();
    }

    public static FloramoMapFragment newInstance(Long speciesId) {
        FloramoMapFragment fragment = new FloramoMapFragment();
        Bundle args = new Bundle();
        args.putLong(SPECIES_ID, speciesId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            speciesId = getArguments().getLong(SPECIES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(FloramoFragment.MAP);

        View view = inflater.inflate(R.layout.floramo_map_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (mapView != null) {
            mapView.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(-2.84360424, -79.2282486);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6.5f));
        setUpClusterer(googleMap);
    }

    private void setUpClusterer(GoogleMap googleMap) {
        clusterManager = new ClusterManager<>(context, googleMap);
        clusterManager.setRenderer(new EspecieRenderer<>(this, context, googleMap, clusterManager));
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                clusterZoom = cameraPosition.zoom;
                clusterManager.onCameraChange(cameraPosition);
            }
        });
        googleMap.setOnMarkerClickListener(clusterManager);
        createSpeciesMarkers();
    }

    private void createSpeciesMarkers() {
        ArrayList<Especie> especies = (ArrayList<Especie>) Especie.list(context);
        for (Especie especie : especies) {
            ExecutorService queue = Executors.newSingleThreadExecutor();
            queue.execute(new EspecieLoader(this, context, especie));
        }
    }

    public float getClusterZoom() {
        return clusterZoom;
    }

    public void addSpeciesMarker(final EspecieMarker especieMarker) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                clusterManager.addItem(especieMarker);
            }
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        context.setTitle(FloramoFragment.MAP.getTitleId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
