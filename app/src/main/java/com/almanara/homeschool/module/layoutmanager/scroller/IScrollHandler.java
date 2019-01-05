package com.almanara.homeschool.module.layoutmanager.scroller;

import android.support.v7.widget.RecyclerView;

import com.almanara.homeschool.module.layoutmanager.circle_helper.quadrant_helper.QuadrantHelper;
import com.almanara.homeschool.module.layoutmanager.layouter.Layouter;

/**
 * Created by danylo.volokh on 28.11.2015.
 * This is an interface which descendants will "know" how to adjust view position using input parameters.
 *
 */
public interface IScrollHandler {

    int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler);

    public enum Strategy{
        PIXEL_PERFECT,
        NATURAL
    }

    public static class Factory{

        private Factory(){}

        public static IScrollHandler createScrollHandler(Strategy strategy, ScrollHandlerCallback callback, QuadrantHelper quadrantHelper, Layouter layouter){
            IScrollHandler scrollHandler = null;
            switch (strategy){
                case PIXEL_PERFECT:
                    scrollHandler = new PixelPerfectScrollHandler(callback, quadrantHelper, layouter);
                    break;
                case NATURAL:
                    scrollHandler = new NaturalScrollHandler(callback, quadrantHelper, layouter);
                    break;
            }
            return  scrollHandler;
        }
    }
}
