package com.example.accounting.mainView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.accounting.R;


/**
 * class: settingView
 *
 * used to state the setting page in the tabLayout
 */
public class settingView extends PageView {

    /**
     * Constructor for settingView
     * @param context the root activity
     */
    public settingView(Context context) {
        super(context);

        //Create the new view object, and Inflate by the Inflater from context which you send
        //the root will be set to itself.
        View view = LayoutInflater.from(context).inflate(R.layout.page_content, null);

        //test TextView
        //TextView textView = (TextView) view.findViewById(R.id.text);
        //textView.setText("Page three");

        //add view to current viewGroup (RelativeLayout)
        addView(view);
    }
    @Override
    public void refreshView() {

    }
}
