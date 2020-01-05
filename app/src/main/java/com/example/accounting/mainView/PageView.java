package com.example.accounting.mainView;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Abstract class: PageView
 *
 * used to ask programmer HOW to build pageView class
 */


public abstract class PageView extends RelativeLayout {

    public PageView(Context context) {
        super(context);
    }
    public abstract void refreshView();

}

