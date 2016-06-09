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
        System.out.println("..............................");
        System.out.println("NEW INSTANCE");
        System.out.println(speciesId);
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

        TextView textView = (TextView) view.findViewById(R.id.detail_description);
        TextView textView2 = (TextView) view.findViewById(R.id.detail_description2);
        ImageView imageView = (ImageView) view.findViewById(R.id.detail_image);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.detail_collapsing_toolbar);
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
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                System.out.println("*********************************************************");
                e.printStackTrace();
                System.out.println("*********************************************************");
            }

//            path = "th_" + path;
//            itemFoto.setImageResource(ResourcesHelper.getImageResourceByName(context, path));
        }
        String str = "Under the bed hiss at vacuum cleaner. Chase ball of string kitty loves pigs yet pee in the shoe. Sleep on dog bed, force dog to sleep on floor shove bum in owner's face like camera lens for stretch, and mew lie on your belly and purr when you are asleep Gate keepers of hell. Lick arm hair inspect anything brought into the house, or cough furball for hunt by meowing loudly at 5am next to human slave food dispenser. Lick arm hair. Give attitude swat turds around the house and unwrap toilet paper chase ball of string refuse to leave cardboard box so russian blue. If it fits, i sits use lap as chair, or lick arm hair. ";
//        str += "\n\n";
        textView.setText(str);
        textView2.setText(description);

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
