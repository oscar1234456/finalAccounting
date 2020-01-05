package com.example.accounting.invoiceList;

/**
* Class PostInvoice: The Data structure of the post of the invoice which will be viewed
*/
public class PostInvoice {

    public String posterName;
    public String content;

    /**
     * Constructor of PostInvoice
     * @param posterName
     * @param content
     */
    public PostInvoice(String posterName, String content) {
        if(posterName.length() >= 11){
            this.posterName = posterName.substring(0,10)+"...";
        }else{
            this.posterName = posterName;
        }
        this.content = "$" + content;
    }

}