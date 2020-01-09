package com.example.accounting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

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

public class newExpenses extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{


    EditText editDate,editAmount,editText,editStore;
    Calendar mcal;
    ArrayAdapter<String> ClassArray;
    Spinner expensesClassSpinner;
    FloatingActionButton myfab;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expenses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        it = getIntent();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendExpenses);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editDate = this.findViewById(R.id.editExpensesDate);
        editAmount = this.findViewById(R.id.editExpensesAmount);
        editText = this.findViewById(R.id.editExpensesText);
        editStore = this.findViewById(R.id.editExpensesStore);

        expensesClassSpinner = this.findViewById(R.id.spinnerExpensesClass);
        myfab = this.findViewById(R.id.sendExpenses);

        mcal = Calendar.getInstance();

        ClassArray = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"食","衣","住","行","育","樂","其他"});

        ClassArray.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        expensesClassSpinner.setAdapter(ClassArray);

        editDate.setOnClickListener(this);
        myfab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.editExpensesDate:
                new DatePickerDialog(this,this,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.sendExpenses:
                Log.e("EXPENSES",editAmount.getText().toString());
                it.putExtra("amount",editAmount.getText().toString());
                it.putExtra("class", expensesClassSpinner.getSelectedItem().toString());
                it.putExtra("date",editDate.getText().toString());
                it.putExtra("text",editText.getText().toString());
                it.putExtra("store",editStore.getText().toString());

                setResult(500,it);
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }
}
