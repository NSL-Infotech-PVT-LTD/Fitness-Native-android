package com.netscape.utrain.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class TabLayoutEx extends TabLayout {

    public TabLayoutEx(Context context) {
        this(context, null);
    }

    public TabLayoutEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setTabIndicatorFullWidth(false);
        setIndicatorWidth(10);
    }


    private class DefPreDrawListener implements ViewTreeObserver.OnPreDrawListener {

        private LinearLayout tabStrip = null;
        private int tabWidth;
        private Field fieldLeft;
        private Field fieldRight;

        public void setTabStrip(LinearLayout tabStrip, int width) {
            try {
                this.tabStrip = tabStrip;
                this.tabWidth = width;
                Class cls = tabStrip.getClass();
                fieldLeft = cls.getDeclaredField("indicatorLeft");
                fieldLeft.setAccessible(true);
                fieldRight = cls.getDeclaredField("indicatorRight");
                fieldRight.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onPreDraw() {
            try {
                if (tabWidth > 0) {
                    int left = fieldLeft.getInt(this.tabStrip);
                    int right = fieldRight.getInt(this.tabStrip);
                    //根据目标宽度及现在的宽度调整为合适的left和right
                    int diff = right - left - tabWidth;
                    left = left + diff / 2;
                    right = right - diff / 2;
                    fieldLeft.setInt(this.tabStrip, left);
                    fieldRight.setInt(this.tabStrip, right);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private DefPreDrawListener defPreDrawListener = new DefPreDrawListener();

    public void setIndicatorWidth(int widthDp) {
        Class<?> tabLayout = TabLayout.class;
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
            tabStrip.setAccessible(true);
            LinearLayout tabIndicator = (LinearLayout) tabStrip.get(this);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDp, Resources.getSystem().getDisplayMetrics());
            //avoid add preDrawListener multi times
            tabIndicator.getViewTreeObserver().removeOnPreDrawListener(defPreDrawListener);
            tabIndicator.getViewTreeObserver().addOnPreDrawListener(defPreDrawListener);
            defPreDrawListener.setTabStrip(tabIndicator, width);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}