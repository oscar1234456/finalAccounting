package com.example.accounting.mainView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;




import com.example.accounting.R;

import java.util.ArrayList;


/**
 * class: myCostView
 *
 * used to state the Cost page in the tabLayout
 */
public class myCostView extends PageView {



    /**
     * Constructor for myCostView
     * @param context the root activity
     */
    public myCostView(Context context) {
        super(context);

        //Create the new view object, and Inflate by the Inflater from context which you send
        //the root will be set to itself.
        View view = LayoutInflater.from(context).inflate(R.layout.cost_view, null);


        //add view to current viewGroup (RelativeLayout)
        addView(view);
    }
    @Override
    public void refreshView() {

    }
}
