package com.example.accounting.mainView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounting.LoginMenu;
import com.example.accounting.MainActivity;
import com.example.accounting.dataStructure.expensesStruc;
import com.example.accounting.dataStructure.incomeStruc;
import com.example.accounting.invoiceList.PostInvoice;
import com.example.accounting.R;
import com.example.accounting.invoiceList.recycleViewAdapter;
import com.example.accounting.invoiceList.recycleViewExpenseAdapter;
import com.example.accounting.newIncome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Calendar;

import static androidx.core.app.ActivityCompat.startActivityForResult;

/**
 * class: myInvoiceView
 * <p>
 * used to state the Invoice page in the tabLayout
 */


public class myInvoiceView extends PageView implements View.OnClickListener,DatePickerDialog.OnDateSetListener{
    private RecyclerView recyclerView;
    private ArrayList<incomeStruc> data;
    private ArrayList<expensesStruc> exDataList;
    public recycleViewAdapter invoiceAdapter;
    public recycleViewExpenseAdapter expenseAdapter;
    private EditText editDayDate;
    private DocumentReference mDocRef;
    private CollectionReference mColRef ;
    private MainActivity mymain;
    private Button btntt;
    private Context MainContext;
    private ArrayAdapter<String> choose;
    Spinner spinnerDaySwitch;
    Calendar mcal;
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
        btntt = view.findViewById(R.id.btntt);
        spinnerDaySwitch = view.findViewById(R.id.spinnerDaySwitch);
        MainContext = context;

        mcal = Calendar.getInstance();
        editDayDate.setText( mcal.get(Calendar.YEAR)+"/"+(mcal.get(Calendar.MONTH)+1)+"/"+mcal.get(Calendar.DATE));

        choose = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,new String[]{"收入","支出"});
        spinnerDaySwitch.setAdapter(choose);
        //test used
        data = new ArrayList<>();
        exDataList = new ArrayList<>();
        initRecycleData();
        invoiceAdapter = new recycleViewAdapter(context, data);
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
        btntt.setOnClickListener(this);

        //add view to current viewGroup (RelativeLayout)
        addView(view);

    }

    @Override
    public void refreshView() {

    }


    public void addData(int choose,String colPath) {
        if(choose == 0){
            //get income data
            Log.d("2ININ","get income");
            data.clear();
            mColRef = FirebaseFirestore.getInstance().collection("Users/"+"s110616038@stu.ntue.edu.tw"+"/IE").document("Income").collection(colPath);
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
        }else if(choose == 1){
            // get expensive
            Log.d("2ININ","get expen");
            exDataList.clear();
            mColRef = FirebaseFirestore.getInstance().collection("Users/"+"s110616038@stu.ntue.edu.tw"+"/IE").document("Expenses").collection(colPath);
            mColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("2ININ", document.getId() + " => " + document.getData());
                            expensesStruc tempExpenses = new expensesStruc( document.getString("expenseAmount"),document.getString("expenseClass"),document.getString("expenseDate"),document.getString("expenseStore"),document.getString("expenseText"));

                            exDataList.add(tempExpenses);

                        }
                    } else {
                        Log.d("2ININ", "Error getting documents: ", task.getException());
                    }
                }

            });
        }

        //invoiceAdapter.notifyDataSetChanged();

    }

    public void initRecycleData(){
        Log.d("2ININ","init");
        String colPath =  mcal.get(Calendar.YEAR)+"/"+(mcal.get(Calendar.MONTH)+1)+"/"+mcal.get(Calendar.DATE);

        mColRef = FirebaseFirestore.getInstance().collection("Users/"+"s110616038@stu.ntue.edu.tw"+"/IE").document("Income").collection(colPath);
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

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDayDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.editDayDate:
                Log.d("2ININ","44");

                new DatePickerDialog(view.getContext(),this,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;

            case R.id.btntt:
                Log.d("2ININ","55");
                int choose = spinnerDaySwitch.getSelectedItemPosition();
                String colPath =  editDayDate.getText().toString();
                if(choose==0){
                    Log.d("2ININ","0000");
                    addData(0,colPath);
                    invoiceAdapter = new recycleViewAdapter(MainContext, data);
                    recyclerView.setAdapter(invoiceAdapter);
                    View vs = view.findViewById(R.id.snack2);
                    Snackbar.make(vs,"完成更新！",Snackbar.LENGTH_LONG).show();
                    break;
                }else if(choose == 1){
                    Log.d("2ININ","1111");
                    addData(1,colPath);
                    expenseAdapter = new recycleViewExpenseAdapter(MainContext, exDataList);
                    recyclerView.setAdapter(expenseAdapter);
                    View vs = view.findViewById(R.id.snack2);
                    Snackbar.make(vs,"完成更新！",Snackbar.LENGTH_LONG).show();
                    expenseAdapter.notifyDataSetChanged();
                }

               /* Intent it = new Intent(getContext(), newIncome.class);
                ((Activity)getContext()).startActivityForResult(it,200);*/
               //invoiceAdapter.notifyDataSetChanged();



                break;
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent it){
            Log.d("2ININ",String.valueOf(requestCode));
        String amount = it.getStringExtra("amount");
        Log.d("2ININ",amount);
    }



}





