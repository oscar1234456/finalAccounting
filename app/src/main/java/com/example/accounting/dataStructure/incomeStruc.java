package com.example.accounting.dataStructure;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class incomeStruc {
    public String incomeAmount;
    public String incomeClass;
    public String incomeDate;
    public String incomeText;

    @ServerTimestamp
    private Timestamp incomeTimestamp;

    public incomeStruc(String tempAmount,String tempClass, String tempDate, String tempText){
        incomeAmount = tempAmount;
        incomeClass = tempClass;
        incomeDate = tempDate;
        incomeText = tempText;
    }

    public String getIncomeAmount(){return incomeAmount;}
    public String getIncomeClass(){return incomeClass;}
    public String getIncomeDate(){return incomeDate;}
    public String getIncomeText(){return incomeText;}
    public Timestamp getIncomeTimestamp(){return incomeTimestamp;}

}
