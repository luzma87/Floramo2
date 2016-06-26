package com.lzm.Cajas.enums;

import com.lzm.Cajas.R;

/**
 * Created by luz on 6/26/16.
 */
public enum Fragments {
    ENCYCLOPEDIA(R.string.title_encyclopedia),
    DETAILS(R.string.title_detail),
    FEEDBACK(R.string.title_feedback),
    SEARCH(R.string.title_search),
    TROPICOS(R.string.title_tropicos),
    TROPICOS_RESULTS(R.string.title_tropicos),
    WEB_VIEW(R.string.title_webview);

    private int titleId;

    Fragments(final int titleId) {
        this.titleId = titleId;
    }

    public int getTitleId() {
        return titleId;
    }
}
