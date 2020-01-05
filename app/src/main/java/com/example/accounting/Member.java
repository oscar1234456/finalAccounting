package com.example.accounting;



import android.net.Uri;

import java.io.Serializable;

public class Member implements Serializable {
    protected String name;
    protected String email;
    protected String photoUrl;

    public Member(String tempName, String tempEmail, Uri tempPhoto){
        name = tempName;
        email = tempEmail;
        photoUrl = tempPhoto.toString();
    }
    public Member(String tempName, String tempEmail, String tempPhoto){
        name = tempName;
        email = tempEmail;
        photoUrl = tempPhoto;
    }

    public void setName(String tempName){
        name = tempName;
    }

    public void setEmail(String tempEmail){
        email = tempEmail;
    }
    public void setPhotoUrl(String tempPhoto){
        photoUrl = tempPhoto;
    }
    public String showName(){
        return name;
    }
    public String showemail(){
        return email;
    }
    public String showPhoto(){
        return photoUrl;
    }

}
