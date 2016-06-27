package com.lzm.Cajas.enums;

import com.lzm.Cajas.R;

public enum FloramoFragment {
    ENCYCLOPEDIA(R.string.title_encyclopedia),
    DETAILS(R.string.title_detail),
    FEEDBACK(R.string.title_feedback),
    SEARCH(R.string.title_search),
    TROPICOS(R.string.title_tropicos),
    TROPICOS_RESULTS(R.string.title_tropicos),
    WEB_VIEW(R.string.title_webview);

    private int titleId;

    FloramoFragment(final int titleId) {
        this.titleId = titleId;
    }

    public int getTitleId() {
        return titleId;
    }
}
