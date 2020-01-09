package com.example.accounting.dataStructure;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class incomeStruc  implements Serializable{
    public String incomeAmount;
    public String incomeClass;
    public String incomeDate;
    public String incomeText;
    private String docid;

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
    public String takeDocid(){return docid;}
    public void setDocid(String tempid){docid = tempid;}
    public Timestamp getIncomeTimestamp(){return incomeTimestamp;}

}
