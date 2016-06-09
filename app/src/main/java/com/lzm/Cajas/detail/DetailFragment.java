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

        ImageView detailColor1 = (ImageView) view.findViewById(R.id.detail_color1);
        ImageView detailColor2 = (ImageView) view.findViewById(R.id.detail_color2);
        ImageView detailLifeForm1 = (ImageView) view.findViewById(R.id.detail_life_form1);
        ImageView detailLifeForm2 = (ImageView) view.findViewById(R.id.detail_life_form2);
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

        if (especie.color1 != null && !especie.color1.equals("none")) {
            detailColor1.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_cl_" + especie.color1 + "_tiny"));
            detailColor1.setVisibility(View.VISIBLE);
        } else {
            detailColor1.setVisibility(View.GONE);
        }
        if (especie.color2 != null && !especie.color2.equals("none")) {
            detailColor2.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_cl_" + especie.color2 + "_tiny"));
            detailColor2.setVisibility(View.VISIBLE);
        } else {
            detailColor2.setVisibility(View.GONE);
        }

        if (especie.formaVida1 != null && !especie.formaVida1.equals("none")) {
            detailLifeForm1.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_fv_" + especie.formaVida1 + "_tiny"));
            detailLifeForm1.setVisibility(View.VISIBLE);
        } else {
            detailLifeForm1.setVisibility(View.GONE);
        }
        if (especie.formaVida2 != null && !especie.formaVida2.equals("none")) {
            detailLifeForm2.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_fv _" + especie.formaVida2 + "_tiny"));
            detailLifeForm2.setVisibility(View.VISIBLE);
        } else {
            detailLifeForm2.setVisibility(View.GONE);
        }

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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
