package com.lzm.Cajas.credits;

import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;

public class AboutParamoFragment extends AboutFragment {

    public AboutParamoFragment() {
        textId = R.string.about_paramo;
        photoTitleId = R.string.about_paramo_title;
        path = "paramo.jpg";
        aboutType = FloramoFragment.ABOUT_PARAMO;
    }

    public static AboutFragment newInstance() {
        return new AboutParamoFragment();
    }

}
