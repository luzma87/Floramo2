package com.lzm.Cajas.enums;

import com.lzm.Cajas.R;

public enum FloramoFragment {
    ENCYCLOPEDIA(R.string.title_encyclopedia),
    DETAILS(R.string.title_detail),
    FEEDBACK(R.string.title_feedback),
    SEARCH(R.string.title_search),
    TROPICOS(R.string.title_tropicos),
    TROPICOS_RESULTS(R.string.title_tropicos),
    WEB_VIEW(R.string.title_webview),
    CREDITS(R.string.title_credits),
    ABOUT_CAJAS(R.string.menu_about_cajas),
    ABOUT_QUITO(R.string.menu_about_dmq),
    ABOUT_PARAMO(R.string.menu_about_paramo),
    ABOUT_APP(R.string.menu_about_this_app);

    private int titleId;

    FloramoFragment(final int titleId) {
        this.titleId = titleId;
    }

    public int getTitleId() {
        return titleId;
    }
}
