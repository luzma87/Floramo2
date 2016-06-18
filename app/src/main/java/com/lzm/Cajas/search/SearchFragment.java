package com.lzm.Cajas.search;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.customComponents.CustomToggleButton;
import com.lzm.Cajas.customComponents.FlowLayout;
import com.lzm.Cajas.db.Color;
import com.lzm.Cajas.db.FormaVida;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final MainActivity context = (MainActivity) this.getActivity();
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        FlowLayout colorsLayout = (FlowLayout) view.findViewById(R.id.search_colors_layout);
        ArrayList<Color> colors = Color.list(context);
        for (Color color : colors) {
            String colorString = "cl_" + color.nombre;
            String colorName = ResourcesHelper.getStringResourceByName(context, colorString);
            int colorImage = ResourcesHelper.getImageResourceByName(context, "ic_" + colorString + "_tiny");
            Drawable icon = ContextCompat.getDrawable(context, colorImage);
            CustomToggleButton colorToggle = new CustomToggleButton(context, colorName, icon);
            colorsLayout.addView(colorToggle);
        }
        FlowLayout lifeFormsLayout = (FlowLayout) view.findViewById(R.id.search_life_forms_layout);
        ArrayList<FormaVida> lifeForms = FormaVida.list(context);
        for (FormaVida lifeForm : lifeForms) {
            String lifeFormString = "fv_" + lifeForm.nombre;
            String lifeFormName = ResourcesHelper.getStringResourceByName(context, lifeFormString);
            int colorImage = ResourcesHelper.getImageResourceByName(context, "ic_" + lifeFormString + "_tiny");
            Drawable icon = ContextCompat.getDrawable(context, colorImage);
            CustomToggleButton lifeFormToggle = new CustomToggleButton(context, lifeFormName, icon);
            lifeFormsLayout.addView(lifeFormToggle);
        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
