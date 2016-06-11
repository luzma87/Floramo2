package com.lzm.Cajas.detail;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.db.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailFragment extends Fragment {
    public static final String SPECIES_ID = "especieId";
    private MainActivity context;

    private Especie especie;
    private Long especieId;

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
        especie = Especie.getDatos(context, especieId);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

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

        collapsingToolbar.setTitle(especie.genero + " " + especie.nombre.toLowerCase());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.detail_fab_tropicos);

        String language = Locale.getDefault().getLanguage();
        String description;
        if (language.equals("en")) {
            description = especie.descripcionEn;
        } else {
            description = especie.descripcionEs;
        }

        List<Foto> fotos = Foto.findAllByEspecie(context, especie);
        Foto foto = null;
        if (fotos.size() > 0) {
            foto = fotos.get(0);
        }

        if (foto != null) {
            String path = "full_size/" + foto.path.replaceAll("-", "_").toLowerCase();
            try {
                Bitmap bitmap = ResourcesHelper.getAssetByName(context, path);
                detailImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                System.out.println("*********************************************************");
                e.printStackTrace();
                System.out.println("*********************************************************");
            }
        }

        setColor(especie.color1, detailColor1);
        setColor(especie.color2, detailColor2);
        setFormaVida(especie.formaVida1, detailLifeForm1);
        setFormaVida(especie.formaVida2, detailLifeForm2);

        detailDescription.setText(description);
//        txtEspecieInfoNombreCientifico.setText(especie.genero + " " + especie.nombre.toLowerCase());
        detailFamily.setText(especie.familia);
        detailGenus.setText(especie.genero);
        detailSpecies.setText(especie.nombre);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Abre tropicos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    private void setColor(String color, ImageView colorImage) {
        if (color != null && !color.equals("none")) {
            String resourceName = "ic_cl_" + color + "_tiny";
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
