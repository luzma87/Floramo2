package com.lzm.Cajas.credits;

import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;

public class AboutAppFragment extends AboutFragment {

    public AboutAppFragment() {
        textId = R.string.about_app;
        photoTitleId = R.string.about_app_title;
        path = "paramo.jpg";
        aboutType = FloramoFragment.ABOUT_APP;
    }

    public static AboutFragment newInstance() {
        return new AboutAppFragment();
    }

}
