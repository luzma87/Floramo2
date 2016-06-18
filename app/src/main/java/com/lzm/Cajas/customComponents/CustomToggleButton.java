package com.lzm.Cajas.customComponents;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.lzm.Cajas.R;

/**
 * Created by luz on 6/18/16.
 */
public class CustomToggleButton extends ToggleButton implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private int primaryColor;
    private int secondaryColor;
    private String label;

    public CustomToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null, null);
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null);
    }

    public CustomToggleButton(Context context, String label, Drawable icon) {
        super(context);
        init(context, label, icon);
    }

    private void init(Context context, String label, Drawable icon) {
        this.context = context;
        this.label = label;
        primaryColor = R.color.primary;
        secondaryColor = R.color.primary_light;

        setUncheckedColors();
        setTextStyle();
        this.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        super.setOnCheckedChangeListener(this);
    }

    private void setTextStyle() {
        this.setTextSize(14);
        this.setText(label);
        this.setTextOn(label);
        this.setTextOff(label);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setStateColors(isChecked);
    }

    private void setStateColors(boolean isChecked) {
        if (isChecked) {
            setCheckedColors();
        } else {
            setUncheckedColors();
        }
    }

    private void setCheckedColors() {
        this.setBackgroundColor(ContextCompat.getColor(context, primaryColor));
        this.setTextColor(ContextCompat.getColor(context, secondaryColor));
    }

    private void setUncheckedColors() {
        this.setBackgroundColor(ContextCompat.getColor(context, secondaryColor));
        this.setTextColor(ContextCompat.getColor(context, primaryColor));
    }
}
