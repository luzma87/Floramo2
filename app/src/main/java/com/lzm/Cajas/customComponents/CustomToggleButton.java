package com.lzm.Cajas.customComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.lzm.Cajas.R;
import com.lzm.Cajas.helpers.Utils;

public class CustomToggleButton extends ToggleButton implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private int primaryColor;
    private int secondaryColor;
    private String label;
    private Long id;

    public CustomToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, null, null, null);
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, null, null, null);
    }

    public CustomToggleButton(Context context, Long id, String label, Drawable icon) {
        super(context);
        init(context, id, label, icon);
    }

    public Long getComponentId() {
        return this.id;
    }

    public String getComponentLabel() {
        return this.label;
    }

    private void init(Context context, Long id, String label, Drawable icon) {
        this.context = context;
        this.id = id;
        this.label = label;
        primaryColor = R.color.primary;
        secondaryColor = R.color.white;

        int textSp = 14;
        int paddingDp = 5;
        int drawableDp = 20;
        int drawablePx = Utils.dp2px(context, drawableDp);
        int paddingPx = Utils.dp2px(context, paddingDp);

        Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, drawablePx, drawablePx, true));

        setTextStyle(textSp);
        setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        setUncheckedColors();

        this.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
        super.setOnCheckedChangeListener(this);
    }

    private void setTextStyle(int textSp) {
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSp);
        this.setText(label);
        this.setTextOn(label);
        this.setTextOff(label);
        this.setAllCaps(false);
        this.setTransformationMethod(null);
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
