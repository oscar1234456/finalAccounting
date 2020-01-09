package com.example.accounting;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.accounting.dataStructure.incomeStruc;
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

public class editIncome extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{


    EditText editDate,editAmount,editText;
    Calendar mcal;
    ArrayAdapter<String> ClassArray;
    Spinner incomeClassSpinner;
    FloatingActionButton myfab;
    Intent it;
    incomeStruc selectedIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);

        it = getIntent();
        selectedIncome =  (incomeStruc) it.getSerializableExtra("selectedIncome");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendEditIncome);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editDate = this.findViewById(R.id.updateIncomeDate);
        editAmount = this.findViewById(R.id.updateIncomeAmount);
        editText = this.findViewById(R.id.updateIncomeText);

        incomeClassSpinner = this.findViewById(R.id.updateIncomeClass);
        myfab = this.findViewById(R.id.sendEditIncome);

        mcal = Calendar.getInstance();

        editAmount.setText(selectedIncome.getIncomeAmount());
        editDate.setText(selectedIncome.getIncomeDate());
        editText.setText(selectedIncome.getIncomeText());

        ClassArray = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,new String[]{"薪水","獎金","補助","投資","其他"});

        ClassArray.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        incomeClassSpinner.setAdapter(ClassArray);

        switch (selectedIncome.getIncomeClass()){
            case "薪水":
                incomeClassSpinner.setSelection(0);
                break;
            case "獎金":
                incomeClassSpinner.setSelection(1);
                break;
            case "補助":
                incomeClassSpinner.setSelection(2);
                break;
            case "投資":
                incomeClassSpinner.setSelection(3);
                break;
            case "其他":
                incomeClassSpinner.setSelection(4);
                break;
        }

        editDate.setOnClickListener(this);
        myfab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.updateIncomeDate:
                new DatePickerDialog(this,this,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;
            case R.id.sendEditIncome:
                Log.e("INCOME",editAmount.getText().toString());
                it.putExtra("amount",editAmount.getText().toString());
                it.putExtra("class",incomeClassSpinner.getSelectedItem().toString());
                it.putExtra("date",editDate.getText().toString());
                it.putExtra("text",editText.getText().toString());
                it.putExtra("docid",selectedIncome.takeDocid());
                if(editDate.getText().toString().equals(selectedIncome.incomeDate)){
                    it.putExtra("timeChange",false);
                }else{
                    it.putExtra("timeChange",true);
                    it.putExtra("oldTime",selectedIncome.incomeDate);
                }


                setResult(600,it);
                finish();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }
}
