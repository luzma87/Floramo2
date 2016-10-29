package com.lzm.Cajas.credits;

import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;

public class AboutQuitoFragment extends AboutFragment {

    public AboutQuitoFragment() {
        textId = R.string.about_quito;
        photoTitleId = R.string.about_quito_title;
        path = "quito.jpg";
        aboutType = FloramoFragment.ABOUT_QUITO;
    }

    public static AboutFragment newInstance() {
        return new AboutQuitoFragment();
    }

}
