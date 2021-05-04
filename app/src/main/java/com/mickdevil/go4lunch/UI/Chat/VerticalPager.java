package com.mickdevil.go4lunch.UI.Chat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalPager extends ViewPager {
    public VerticalPager(@NonNull Context context) {
        super(context);
    }

    public VerticalPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPageTransformer(true, new VerticalPage());
        setOverScrollMode(OVER_SCROLL_NEVER);

    }

    private MotionEvent getIntercambioXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        Float newX = (event.getY() / height) * width;
        Float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);
        return event;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interepted = super.onInterceptTouchEvent(getIntercambioXY(ev));
        getIntercambioXY(ev);
        return interepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(getIntercambioXY(ev));
    }


private class VerticalPage implements PageTransformer{

    @Override
    public void transformPage(@NonNull View page, float position) {
        if (position < -1){
            page.setAlpha(0);
        }
        else if (position <=1){
            page.setAlpha(1);
            page.setTranslationX( page.getWidth() * -position);
            float yPosition = position * page.getHeight();
      page.setTranslationY(yPosition);
        }
        else{
            page.setAlpha(0);
        }
    }
}











}
