package com.example.accounting.mainView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounting.invoiceList.PostInvoice;
import com.example.accounting.R;
import com.example.accounting.invoiceList.recycleViewAdapter;



import java.util.ArrayList;

/**
 * class: myInvoiceView
 * <p>
 * used to state the Invoice page in the tabLayout
 */


public class myInvoiceView extends PageView {
    private RecyclerView recyclerView;
    private ArrayList<PostInvoice> data;
    private recycleViewAdapter invoiceAdapter;

    /**
     * Constructor for myInvoiceView
     *
     * @param context the root activity
     */
    public myInvoiceView(Context context) {
        super(context);

        //Create the new view object, and Inflate by the Inflater from context which you send
        //the root will be set to itself.
        View view = LayoutInflater.from(context).inflate(R.layout.myinvoice_view, null);
        recyclerView = view.findViewById(R.id.invoiceList);

        //test used
        data = new ArrayList<>();

        //the Adapter for recyclerView
        invoiceAdapter = new recycleViewAdapter(context, data);

        /**
         TODO:Choose for one(Stagger or Linear)
         */
        /**
         * setLayoutManger() will set the Layout of recyclerView
         */
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(invoiceAdapter);

        //add view to current viewGroup (RelativeLayout)
        addView(view);

    }

    @Override
    public void refreshView() {

    }

    // Add invoice to this.data
    public void addData() {
        data.add(new PostInvoice("play","123"));
    }



}





