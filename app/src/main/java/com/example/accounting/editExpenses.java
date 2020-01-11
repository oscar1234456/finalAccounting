package com.example.accounting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.accounting.dataStructure.expensesStruc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class editExpenses extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText editDate,editAmount,editText,editStore;
    Calendar mcal;
    ArrayAdapter<String> ClassArray;
    Spinner expensesClassSpinner;
    FloatingActionButton myfab;
    Intent it;
    expensesStruc selectedExpenses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        it = getIntent();
        selectedExpenses =  (expensesStruc) it.getSerializableExtra("selectedExpenses");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendEditExpenses);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editDate = this.findViewById(R.id.updateExpensesDate);
        editAmount = this.findViewById(R.id.updateExpensesAmount);
        editText = this.findViewById(R.id.updateExpensesText);
        editStore = this.findViewById(R.id.updateExpensesStore);

        expensesClassSpinner = this.findViewById(R.id.updateExpensesClass);
        myfab = this.findViewById(R.id.sendEditExpenses);

        mcal = Calendar.getInstance();




        Log.d("ORIDate",selectedExpenses.getExpenseDate());
        Log.d("ORIAmount",selectedExpenses.getExpenseAmount());
        Log.d("ORIText",selectedExpenses.getExpenseText());
        Log.d("ORIStore",selectedExpenses.getExpenseStore());
        editText.setText(selectedExpenses.getExpenseText());
        editStore.setText(selectedExpenses.getExpenseStore());
        editAmount.setText(selectedExpenses.getExpenseAmount());
        editDate.setText(selectedExpenses.getExpenseDate());

        ClassArray = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"食","衣","住","行","育","樂","其他"});

        ClassArray.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        expensesClassSpinner.setAdapter(ClassArray);

        switch (selectedExpenses.getExpenseClass()){
            case "食":
                expensesClassSpinner.setSelection(0);
                break;
            case "衣":
                expensesClassSpinner.setSelection(1);
                break;
            case "住":
                expensesClassSpinner.setSelection(2);
                break;
            case "行":
                expensesClassSpinner.setSelection(3);
                break;
            case "育":
                expensesClassSpinner.setSelection(4);
                break;
            case "樂":
                expensesClassSpinner.setSelection(5);
                break;
            case "其他":
                expensesClassSpinner.setSelection(6);
                break;
        }

        editDate.setOnClickListener(this);
        myfab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.updateExpensesDate:
                new DatePickerDialog(this,this,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.sendEditExpenses:
                Log.e("EXPENSES",editAmount.getText().toString());
                it.putExtra("amount",editAmount.getText().toString());
                it.putExtra("class", expensesClassSpinner.getSelectedItem().toString());
                it.putExtra("date",editDate.getText().toString());
                it.putExtra("text",editText.getText().toString());
                it.putExtra("store",editStore.getText().toString());
                it.putExtra("docid",selectedExpenses.takeDocid());
                Log.d("EDIT",editDate.getText().toString());
                if(editDate.getText().toString().equals(selectedExpenses.expenseDate)){
                    it.putExtra("timeChange",false);
                }else{
                    it.putExtra("timeChange",true);
                    it.putExtra("oldTime",selectedExpenses.expenseDate);
                }

                setResult(700,it);
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }
}
