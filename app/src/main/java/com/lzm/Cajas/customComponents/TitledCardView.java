package com.lzm.Cajas.customComponents;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.helpers.TextHelper;

/**
 * Created by luz on 7/2/16.
 */
public class TitledCardView extends CardView {

    String titleText = "TITLE";
    String contentText = "CONTENT";
    int titleBackground;
    int contentBackground;
    int titleTextColor;
    int contentTextColor;
    boolean specialContent = false;

    public TitledCardView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public TitledCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public TitledCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitledCardView, 0, 0);
        try {
            titleText = a.getString(R.styleable.TitledCardView_cardTitleText);
            contentText = a.getString(R.styleable.TitledCardView_cardContentText);
            titleBackground = a.getColor(R.styleable.TitledCardView_cardTitleBackground,
                    ContextCompat.getColor(context, R.color.primary_light));
            titleTextColor = a.getColor(R.styleable.TitledCardView_cardTitleTextColor,
                    ContextCompat.getColor(context, R.color.text_primary));
            contentBackground = a.getColor(R.styleable.TitledCardView_cardContentBackground,
                    ContextCompat.getColor(context, R.color.white));
            contentTextColor = a.getColor(R.styleable.TitledCardView_cardContentTextColor,
                    ContextCompat.getColor(context, R.color.text_primary));
            specialContent = a.getBoolean(R.styleable.TitledCardView_cardSpecialContent, false);
        } finally {
            a.recycle();
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_titled_card, this, true);

        LinearLayout layout = (LinearLayout) getChildAt(0);

        TextView title = (TextView) layout.getChildAt(0);
        title.setBackgroundColor(titleBackground);
        title.setTextColor(titleTextColor);
        title.setText(titleText);

        TextView content = (TextView) layout.getChildAt(1);
        content.setBackgroundColor(contentBackground);
        content.setTextColor(contentTextColor);
        if (specialContent) {
            if (context instanceof MainActivity) {
                TextHelper.setTextWithLinks((MainActivity) context, content, contentText);
            }
        } else {
            content.setText(Html.fromHtml(contentText));
        }
    }

}