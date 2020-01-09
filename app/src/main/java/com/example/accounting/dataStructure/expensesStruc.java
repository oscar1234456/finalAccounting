package com.example.accounting.dataStructure;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class expensesStruc {
    public String expenseAmount;
    public String expenseClass;
    public String expenseStore;
    public String expenseDate;
    public String expenseText;

    @ServerTimestamp
    private Timestamp expenseTimestamp;

    public expensesStruc(String tempAmount, String tempClass,String tempStore ,String tempDate, String tempText){
        expenseAmount = tempAmount;
        expenseClass = tempClass;
        expenseStore = tempStore;
        expenseDate = tempDate;
        expenseText = tempText;

    }

    public String getExpenseAmount(){return expenseAmount;}
    public String getExpenseClass(){return expenseClass;}
    public String getExpenseDate(){return expenseDate;}
    public String getExpenseText(){return expenseText;}
    public Timestamp getExpenseTimestamp(){return expenseTimestamp;}

}
