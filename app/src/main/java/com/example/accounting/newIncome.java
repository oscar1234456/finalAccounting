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

public class newIncome extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {



    EditText editDate,editAmount,editText;
    Calendar mcal;
    ArrayAdapter<String> ClassArray;
    Spinner incomeClassSpinner;
    FloatingActionButton myfab;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        it = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editDate = this.findViewById(R.id.editDate);
        editAmount = this.findViewById(R.id.editIncomeAmount);
        editText = this.findViewById(R.id.editIncomeWrite);

        incomeClassSpinner = this.findViewById(R.id.IncomeClasspinner);
        myfab = this.findViewById(R.id.send);

        mcal = Calendar.getInstance();

        ClassArray = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"薪水","獎金","補助","投資","其他"});

        ClassArray.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        incomeClassSpinner.setAdapter(ClassArray);

        editDate.setOnClickListener(this);
        myfab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.editDate:
                new DatePickerDialog(this,this,
                            mcal.get(Calendar.YEAR),
                            mcal.get(Calendar.MONTH),
                            mcal.get(Calendar.DAY_OF_MONTH))
                            .show();
                break;
            case R.id.send:
                Log.e("INCOME",editAmount.getText().toString());
                it.putExtra("amount",editAmount.getText().toString());
                it.putExtra("class",incomeClassSpinner.getSelectedItem().toString());
                it.putExtra("date",editDate.getText().toString());
                it.putExtra("text",editText.getText().toString());

                setResult(400,it);
                finish();
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }
}
