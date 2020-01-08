package com.example.accounting.mainView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounting.MainActivity;
import com.example.accounting.dataStructure.incomeStruc;
import com.example.accounting.invoiceList.PostInvoice;
import com.example.accounting.R;
import com.example.accounting.invoiceList.recycleViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * class: myInvoiceView
 * <p>
 * used to state the Invoice page in the tabLayout
 */


public class myInvoiceView extends PageView implements View.OnClickListener,DatePickerDialog.OnDateSetListener{
    private RecyclerView recyclerView;
    private ArrayList<incomeStruc> data;
    private recycleViewAdapter invoiceAdapter;
    private EditText editDayDate;
    private DocumentReference mDocRef;
    private CollectionReference mColRef ;
    private MainActivity mymain;
    View view;
    /**
     * Constructor for myInvoiceView
     *
     * @param context the root activity
     */
    public myInvoiceView(Context context) {
        super(context);

        //Create the new view object, and Inflate by the Inflater from context which you send
        //the root will be set to itself.
        view = LayoutInflater.from(context).inflate(R.layout.myinvoice_view, null);
        recyclerView = view.findViewById(R.id.invoiceList);
        editDayDate = view.findViewById(R.id.editDayDate);

        //test used
        data = new ArrayList<>();
        invoiceAdapter = new recycleViewAdapter(context, data);
        addData(context);
        //the Adapter for recyclerView


        /**
         TODO:Choose for one(Stagger or Linear)
         */
        /**
         * setLayoutManger() will set the Layout of recyclerView
         */
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
       recyclerView.setLayoutManager(new LinearLayoutManager(context));
       recyclerView.setAdapter(invoiceAdapter);


        editDayDate.setOnClickListener(this);

        //add view to current viewGroup (RelativeLayout)
        addView(view);

    }

    @Override
    public void refreshView() {

    }

    // Add invoice to this.data
    public void addData(Context context) {

     mColRef = FirebaseFirestore.getInstance().collection("Users/"+"f130501758@gmail.com"+"/IE").document("Income").collection("2020/1/17");
        mColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("2ININ", document.getId() + " => " + document.getData());
                         incomeStruc tempIncome = new incomeStruc( document.getString("incomeAmount"),document.getString("incomeClass"),document.getString("incomeDate"),document.getString("incomeText"));

                         data.add(tempIncome);

                    }
                } else {
                    Log.d("2ININ", "Error getting documents: ", task.getException());
                }
            }

        });
       invoiceAdapter.notifyDataSetChanged();

    }

    public void addData2(){
        data = new ArrayList<>();
        incomeStruc tempIncome = new incomeStruc("123","456","789","10111");
        data.add(tempIncome);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDayDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.editDayDate:
                Calendar mcal = Calendar.getInstance();
                new DatePickerDialog(view.getContext(),this,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
        }

    }
}





