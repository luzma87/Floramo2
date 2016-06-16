package com.lzm.Cajas.detail;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.db.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.ArrayList;

public class DetailFragment extends Fragment {
    public static final String SPECIES_ID = "especieId";
    private MainActivity context;

    private Especie especie;
    private Long especieId;
    private GridView gridView;
    private ArrayList<Foto> fotos;
    private int columnWidth = 400;
    private int gridPadding;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Long speciesId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong(SPECIES_ID, speciesId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            especieId = getArguments().getLong(SPECIES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(MainActivity.FRAGMENT_DETAILS);

        especie = Especie.getDatos(context, especieId);
        fotos = (ArrayList<Foto>) Foto.findAllByEspecie(context, especie);
        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.detail_collapsing_toolbar);

        TextView detailFamily = (TextView) view.findViewById(R.id.detail_family);
        TextView detailGenus = (TextView) view.findViewById(R.id.detail_genus);
        TextView detailSpecies = (TextView) view.findViewById(R.id.detail_species);
        TextView detailDescription = (TextView) view.findViewById(R.id.detail_description);

        TextView detailLifeForm1 = (TextView) view.findViewById(R.id.detail_life_form1_compund);
        TextView detailLifeForm2 = (TextView) view.findViewById(R.id.detail_life_form2_compund);

        ImageView detailColor1 = (ImageView) view.findViewById(R.id.detail_color1_image);
        ImageView detailColor2 = (ImageView) view.findViewById(R.id.detail_color2_image);
        ImageView detailImage = (ImageView) view.findViewById(R.id.detail_image);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.detail_fab_tropicos);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.detail_linear_layout);

        gridView = (GridView) view.findViewById(R.id.detail_grid_view);

        initializeGridView();
        DetailGridViewAdapter adapter = new DetailGridViewAdapter(context, fotos, columnWidth);
        gridView.setAdapter(adapter);

        float padding = getPadding();
        int cantPhotos = fotos.size();
        int photosWidth = columnWidth * cantPhotos;
        int paddingWidth = (int) padding * cantPhotos;

        linearLayout.getLayoutParams().width = photosWidth + paddingWidth;
        linearLayout.requestLayout();

        setBarTitle(collapsingToolbar);

        if (fotos.size() > 0) {
            Foto foto = fotos.get(0);
            if (foto != null) {
                String path = "full_size/" + foto.path.replaceAll("-", "_").toLowerCase();
                try {
                    Bitmap bitmap = ResourcesHelper.getAssetByName(context, path);
                    detailImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        setColor(especie.color1, detailColor1);
        setColor(especie.color2, detailColor2);
        setFormaVida(especie.formaVida1, detailLifeForm1);
        setFormaVida(especie.formaVida2, detailLifeForm2);

        detailDescription.setText(especie.getDescripcion());
//        txtEspecieInfoNombreCientifico.setText(especie.genero + " " + especie.nombre.toLowerCase());
        detailFamily.setText(especie.familia);
        detailGenus.setText(especie.genero);
        detailSpecies.setText(especie.nombre);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseUrl = context.getString(R.string.tropicos_base_url);
                String url = baseUrl + especie.idTropicos;
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(myIntent);
            }
        });
        return view;
    }

    private float getPadding() {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                gridPadding, context.getResources().getDisplayMetrics());
    }

    private void initializeGridView() {
        gridPadding = 8;
        float padding = getPadding();
        gridView.setNumColumns(fotos.size());
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    private void setBarTitle(CollapsingToolbarLayout collapsingToolbar) {
        collapsingToolbar.setTitle(especie.genero + " " + especie.nombre.toLowerCase());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

    private void setColor(String color, ImageView colorImage) {
        if (color != null && !color.equals("none")) {
            color = "cl_" + color;
            String resourceName = "ic_" + color + "_tiny";
            int imageResource = ResourcesHelper.getImageResourceByName(context, resourceName);
            colorImage.setImageResource(imageResource);
            colorImage.setVisibility(View.VISIBLE);
        } else {
            colorImage.setVisibility(View.GONE);
        }
    }

    private void setFormaVida(String formaVida, TextView formaVidaCompound) {
        if (formaVida != null && !formaVida.equals("none")) {
            formaVida = "fv_" + formaVida;
            String resourceName = "ic_" + formaVida + "_tiny";
            String formaVidaName = ResourcesHelper.getStringResourceByName(context, formaVida);
            int imageResource = ResourcesHelper.getImageResourceByName(context, resourceName);
            formaVidaCompound.setCompoundDrawablesWithIntrinsicBounds(imageResource, 0, 0, 0);
            formaVidaCompound.setText(formaVidaName);
            formaVidaCompound.setVisibility(View.VISIBLE);
        } else {
            formaVidaCompound.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
