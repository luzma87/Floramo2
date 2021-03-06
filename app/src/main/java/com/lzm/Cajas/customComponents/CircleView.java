package com.lzm.Cajas.customComponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.lzm.Cajas.R;

/**
 * Created by iuliia on 16.05.16.
 * https://github.com/bieliaievays/ViewPagerIndicatorCircles
 */
public class CircleView extends View {

    private static final String TAG = CircleView.class.getSimpleName();

    private Paint paintBase;
    private Paint paintAccent;
    private int circleDistance;
    private static final int circleRadius = 10;
    private int mCurrentItem = 2; //default test value
    private int mItemCount = 5; //default test value
    private int colorBase;
    private int colorAccent;

    public CircleView(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleView,
                0, 0);
        try {
            colorBase = a.getColor(R.styleable.CircleView_base_color, getResources().getColor(R.color.white_30));
            colorAccent = a.getColor(R.styleable.CircleView_accent_color, getResources().getColor(R.color.white_80));

        } finally {
            a.recycle();
        }

        init();
    }


    public void setViewPager(ViewPager viewPager) {

        mCurrentItem = viewPager.getCurrentItem();

        mItemCount = viewPager.getAdapter().getCount();

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        invalidate();
        requestLayout();
    }

    public void setColorBase(int color) {
        colorBase = color;
        paintBase.setColor(colorBase);

        invalidate();
        requestLayout();
    }

    public void setColorAccent(int color) {
        colorAccent = color;
        paintAccent.setColor(colorAccent);

        invalidate();
        requestLayout();
    }

    private void setCurrentItem(int currentItem) {
        mCurrentItem = currentItem;

        invalidate();
        requestLayout();
    }

    private void init() throws Exception {
        circleDistance = generateCircleDistance();

        paintBase = new Paint();
        paintBase.setColor(colorBase);
        paintBase.setAntiAlias(true);

        paintAccent = new Paint();
        paintAccent.setColor(colorAccent);
        paintAccent.setAntiAlias(true);
    }

    private int generateCircleDistance() throws Exception {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth();

        int distance = circleRadius * 2;
        if (screenWidth < distance * mItemCount) {
            distance = (int) (circleRadius * 1.5);
            if (screenWidth < distance * mItemCount) {
                throw new Exception("ERROR: You have too many child in ViewPager. It is not possible to show CircleView widget in one line. Try to reorganize your ViewPager.");
            }
        }

        return distance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = circleRadius;
        for (int i = 0; i < mItemCount; i++) {

            if (i == mCurrentItem) //should have more light color
            {
                canvas.drawCircle(cx, circleRadius, circleRadius, paintAccent);
            } else {
                canvas.drawCircle(cx, circleRadius, circleRadius, paintBase);
            }

            cx = cx + circleRadius * 2 + circleDistance;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = circleRadius * 2;
        final int width = ((circleRadius * 2 + circleDistance) * mItemCount) - circleDistance;

        setMeasuredDimension(width, height);
    }
}