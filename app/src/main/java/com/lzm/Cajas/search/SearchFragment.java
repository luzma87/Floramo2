package com.lzm.Cajas.search;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.customComponents.CustomToggleButton;
import com.lzm.Cajas.customComponents.FlowLayout;
import com.lzm.Cajas.db.Color;
import com.lzm.Cajas.db.FormaVida;
import com.lzm.Cajas.helpers.ResourcesHelper;
import com.lzm.Cajas.helpers.Utils;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ArrayList<CustomToggleButton> colorButtons;
    private ArrayList<CustomToggleButton> lifeFormButtons;

    private TextView conditionalInfo;
    private TextView colorInfo;
    private TextView lifeFormInfo;
    private TextView textInfo;
    private ImageButton searchButton;

    private MainActivity context;

    private ArrayList<Long> lifeFormSearch;
    private ArrayList<Long> colorSearch;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) this.getActivity();
        colorButtons = new ArrayList<>();
        lifeFormButtons = new ArrayList<>();
        lifeFormSearch = new ArrayList<>();
        colorSearch = new ArrayList<>();

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        getViews(view);

        initColorToggles(view);
        initLifeFormToggles(view);

        return view;
    }

    private void getViews(View view) {
        EditText searchByText = (EditText) view.findViewById(R.id.search_by_text);
        RadioButton radioAnd = (RadioButton) view.findViewById(R.id.search_radio_and);
        RadioButton radioOr = (RadioButton) view.findViewById(R.id.search_radio_or);
        conditionalInfo = (TextView) view.findViewById(R.id.search_conditional_info);
        colorInfo = (TextView) view.findViewById(R.id.search_color_info);
        lifeFormInfo = (TextView) view.findViewById(R.id.search_life_form_info);
        textInfo = (TextView) view.findViewById(R.id.search_text_info);
        searchButton = (ImageButton) view.findViewById(R.id.search_button);

        setRadioListener(radioAnd, R.string.search_conditional_info_and);
        setRadioListener(radioOr, R.string.search_conditional_info_or);
        setTextListener(searchByText);
    }

    private void setTextListener(EditText searchByText) {
        searchByText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateText(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setRadioListener(RadioButton radio, final int string) {
        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConditional(string);
            }
        });
    }

    private void updateConditional(int conditional) {
        Utils.hideSoftKeyboard(context);
        String text = context.getString(conditional);
        conditionalInfo.setText(text);
    }

    private void updateText(String text) {
        if (text.equals("")) {
            textInfo.setVisibility(View.GONE);
        } else {
            text = context.getString(R.string.search_text_info, text);
            textInfo.setVisibility(View.VISIBLE);
            textInfo.setText(text);
        }
    }

    private void updateLifeForms() {
        Utils.hideSoftKeyboard(context);
        ArrayList<String> lifeForms = new ArrayList<>();
        for (CustomToggleButton toggle : lifeFormButtons) {
            if (toggle.isChecked()) {
                lifeForms.add(toggle.getComponentLabel());
                lifeFormSearch.add(toggle.getComponentId());
            }
        }
        updateToggleInfo(lifeForms, lifeFormInfo, R.string.search_life_form_info);
    }

    private void updateColors() {
        Utils.hideSoftKeyboard(context);
        ArrayList<String> colors = new ArrayList<>();
        for (CustomToggleButton toggle : colorButtons) {
            if (toggle.isChecked()) {
                colors.add(toggle.getComponentLabel());
                colorSearch.add(toggle.getComponentId());
            }
        }
        updateToggleInfo(colors, colorInfo, R.string.search_color_info);
    }

    private void updateToggleInfo(ArrayList<String> itemNames, TextView textView, int stringId) {
        if (itemNames.size() > 0) {
            String text = TextUtils.join(", ", itemNames);
            text = context.getString(stringId, text);
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void initLifeFormToggles(View view) {
        FlowLayout lifeFormsLayout = (FlowLayout) view.findViewById(R.id.search_life_forms_layout);
        ArrayList<FormaVida> lifeForms = FormaVida.list(context);
        for (FormaVida lifeForm : lifeForms) {
            Long lifeFormId = lifeForm.getId();
            String lifeFormString = "fv_" + lifeForm.getNombre();
            CustomToggleButton lifeFormToggle = initToggleButton(lifeFormsLayout, lifeFormId, lifeFormString);
            lifeFormButtons.add(lifeFormToggle);
            lifeFormToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateLifeForms();
                }
            });
        }
    }

    private void initColorToggles(View view) {
        FlowLayout colorsLayout = (FlowLayout) view.findViewById(R.id.search_colors_layout);
        ArrayList<Color> colors = Color.list(context);
        for (Color color : colors) {
            Long colorId = color.getId();
            String colorString = "cl_" + color.getNombre();
            CustomToggleButton colorToggle = initToggleButton(colorsLayout, colorId, colorString);
            colorButtons.add(colorToggle);
            colorToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateColors();
                }
            });
        }
    }

    private CustomToggleButton initToggleButton(FlowLayout layout, Long itemId, String itemString) {
        String colorName = ResourcesHelper.getStringResourceByName(context, itemString);
        int colorImage = ResourcesHelper.getImageResourceByName(context, "ic_" + itemString + "_tiny");
        Drawable icon = ContextCompat.getDrawable(context, colorImage);
        CustomToggleButton colorToggle = new CustomToggleButton(context, itemId, colorName, icon);
        layout.addView(colorToggle);
        return colorToggle;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
