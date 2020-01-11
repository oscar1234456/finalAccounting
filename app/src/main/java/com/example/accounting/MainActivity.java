package com.example.accounting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.accounting.dataStructure.expensesStruc;
import com.example.accounting.dataStructure.incomeStruc;
import com.example.accounting.listener.tabClickListener;
import com.example.accounting.mainView.PageView;
import com.example.accounting.mainView.myCostView;
import com.example.accounting.mainView.myInvoiceView;
import com.example.accounting.mainView.settingView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int LOGIN_SUCCESS = 200;
    public static final String INCOMEADD_TAG = "INCOMEADD";
    private ViewPager mViewPager;
    private ArrayList<PageView> pageList;
    private TabLayout mTablayout;
    public myInvoiceView myInvoiceView;
    public myCostView myCostView;
    public settingView mySettingView;
    private SamplePagerAdapter pagerAdapter;
    private Member userData;
    private DocumentReference mDocRef;
    private CollectionReference mColRef;

    FloatingActionButton mfab1,mfab2;


    /**
     * Needed for Android System
     *
     * @param savedInstanceState the System State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String loginCode;


        SharedPreferences myPref = getPreferences(MODE_PRIVATE);
        loginCode = myPref.getString("LoginStatus","FALSE");
        if(loginCode.equals("FALSE")){   //尚未有登入資料
            Intent it = new Intent(this,LoginMenu.class);
            startActivityForResult(it,100);
            Log.e("main","FALSE");
        }else{
            View view = this.findViewById(R.id.snack);
            String userName = myPref.getString("userName","Guest");
            String userEmail = myPref.getString("userEmail","null");
            String userPhoto = myPref.getString("userPhoto","null");
            userData = new Member(userName,userEmail,userPhoto);
            Snackbar.make(view,"成功登入！"+userName+" "+userEmail+" "+userPhoto,Snackbar.LENGTH_LONG).show();

            init(userEmail,userData);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    protected void onResume() {
        super.onResume();






    }

    protected void init(String mail, Member tempUser) {

        initView(tempUser); //Initialize View
        initTab(); //Initialize TabLayout and viewPager
        mfab1 = this.findViewById(R.id.expenses);
        mfab2 = this.findViewById(R.id.income);


        mfab1.setOnClickListener(this);
        mfab2.setOnClickListener(this);


        mDocRef = FirebaseFirestore.getInstance().document("Users/"+mail);
        mColRef = FirebaseFirestore.getInstance().collection("Users/"+mail+"/IE");


    }


    /**
     * The method used to initialize the tabLayout and viewPager
     * :addTab
     * :setAdapter for viewPager
     * :call initListener
     */
    private void initTab() {
        mTablayout = (TabLayout) findViewById(R.id.tabs);

        // Set the tab on the Tablayout
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.myCost_tabItem)));
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.myInvoice_tabItem)));
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.setting_tabItem)));


        mViewPager = (ViewPager) findViewById(R.id.pager);
        // Use a attribute to save pagerAdapter
        pagerAdapter = new SamplePagerAdapter();
        // Set adapter to mViewPager
        mViewPager.setAdapter(pagerAdapter);

        initListener();
    }

    /**
     * The method used to initialize the view in pageList
     */
    private void initView(Member userData) {
        pageList = new ArrayList<>();

        myCostView = new myCostView(MainActivity.this,userData);
        // Add the view items to pageList
        pageList.add(myCostView);

        // Use a attribute to save myInvoiceView
        myInvoiceView = new myInvoiceView(MainActivity.this,userData);
        // Add the view items to pageList
        pageList.add(myInvoiceView);


        mySettingView = new settingView(MainActivity.this,userData);
        // Add the view items to pageList
        pageList.add(mySettingView);

        FloatingActionsMenu buttonBw = this.findViewById(R.id.multiple_actions);


    }

    /**
     * The method used to initialize Listener
     * :tabLayout =>addOnTabSelectedListener
     * :viewPager =>addOnPageChangeListener
     */
    private void initListener() {
        mTablayout.addOnTabSelectedListener(new tabClickListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }

    @Override
    public void onClick(View v) {
        View view = this.findViewById(R.id.snack);
        switch(v.getId()){
            case R.id.expenses:
                Snackbar.make(view,"Expense！",Snackbar.LENGTH_LONG).show();
                Intent ittoEx = new Intent(this,newExpenses.class);
                startActivityForResult(ittoEx,400);

                break;
            case R.id.income:
                Snackbar.make(view,"Income!",Snackbar.LENGTH_LONG).show();
                Intent ittoIn = new Intent(this,newIncome.class);
                startActivityForResult(ittoIn,300);
                break;


        }
    }




    private class SamplePagerAdapter extends PagerAdapter {



        //get numbers of pageList elements(:view)
        @Override
        public int getCount() {
            return pageList.size();
        }

        //make sure the object we will instantiated is View
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        //instantiating the view be the viewpager's items
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(pageList.get(position));

            //System.out.println("IN");  :for test use
            return pageList.get(position);
        }

        //remove the view be the viewpager's items
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

            System.out.println("OUT");
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent it){
        super.onActivityResult(requestCode, resultCode, it);
        if (requestCode != RESULT_CANCELED) {
            if(resultCode == LOGIN_SUCCESS && it != null){
                Member user;
                Log.e("mainProcess","LOGINSUECESS");

                user =  (Member) it.getSerializableExtra("user");
                View view = this.findViewById(R.id.snack);

                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

                editor.putString("LoginStatus","TRUE");
                editor.putString("userName",user.showName());
                editor.putString("userEmail",user.showemail());
                editor.putString("userPhoto",user.showPhoto());
                editor.commit();

                //Snackbar.make(view,"成功！",Snackbar.LENGTH_LONG).show();

               init(user.email, user);
                Snackbar.make(view,user.showName()+" "+user.showemail()+" "+user.showPhoto(),Snackbar.LENGTH_LONG).show();
            }else if(resultCode == 400 && it != null){
                /*for (ViewPager c : pageList){
                    c.onActivityResult(requestCode, resultCode, data);
                }*/

                myInvoiceView.onActivityResult(requestCode,  resultCode, it);

                //當新增收入頁面回來時
                View view = this.findViewById(R.id.snack);
                String amount = it.getStringExtra("amount");
                String stringclass = it.getStringExtra("class");
                String sdate = it.getStringExtra("date");
                String text  = it.getStringExtra("text");

                Snackbar.make(view,amount+" "+stringclass+" "+sdate+" "+text,Snackbar.LENGTH_LONG).show();
                incomeStruc dataToSave = new incomeStruc(amount,stringclass,sdate,text);

                //製造路線
                mColRef.document("Income").collection(dataToSave.getIncomeDate()).add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                            Log.d("IIII","now");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Log.d("IIII","low");
                    }
                });

            }else if(resultCode == 500 && it != null){
                /*for (ViewPager c : pageList){
                    c.onActivityResult(requestCode, resultCode, data);
                }*/


                //當新增支出頁面回來時
                View view = this.findViewById(R.id.snack);
                String amount = it.getStringExtra("amount");
                String stringclass = it.getStringExtra("class");
                String store = it.getStringExtra("store");
                String sdate = it.getStringExtra("date");
                String text  = it.getStringExtra("text");

                Snackbar.make(view,amount+" "+stringclass+" "+store+" "+sdate+" "+text,Snackbar.LENGTH_LONG).show();
                expensesStruc dataToSave = new expensesStruc(amount,stringclass,store,sdate,text);

                //製造路線
                mColRef.document("Expenses").collection(dataToSave.getExpenseDate()).add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("EEEE","now");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("EEEE","low");
                    }
                });

            }else if(resultCode == 600 && it != null){
                //從編輯收入頁面回來
                myInvoiceView.onActivityResult(requestCode,  resultCode, it);//呼叫view的onActivityResult

            }else if(resultCode == 700 && it != null){
                //從編輯支出頁面回來
                myInvoiceView.onActivityResult(requestCode,  resultCode, it);//呼叫view的onActivityResult

            }
        }
    }






    /**
     *
     * 每日畫面相關方法
     *
     * */

    public void btnRe(View v){
        View view = this.findViewById(R.id.snack);
        Snackbar.make(view,"123",Snackbar.LENGTH_LONG).show();
    }



}
