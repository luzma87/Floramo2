package com.lzm.Cajas.credits;

import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;

public class AboutCajasFragment extends AboutFragment {

    public AboutCajasFragment() {
        textId = R.string.about_cajas;
        photoTitleId = R.string.about_cajas_title;
        path = "cajas.jpg";
        aboutType = FloramoFragment.ABOUT_CAJAS;
    }

    public static AboutFragment newInstance() {
        return new AboutCajasFragment();
    }

}
