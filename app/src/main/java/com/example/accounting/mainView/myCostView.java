package com.example.accounting.mainView;

import android.app.DatePickerDialog;
import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.annotation.NonNull;

import com.example.accounting.Member;
import com.example.accounting.R;
import com.example.accounting.dataStructure.expensesStruc;
import com.example.accounting.dataStructure.incomeStruc;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * class: myCostView
 *
 * used to state the Cost page in the tabLayout
 */
public class myCostView extends PageView implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    PieChart pieChart ;
    Member userData;
    Button btnRefresh,btn3;
    EditText editChartDateStart,editChartDateEnd;
    Spinner spinnerChartSwitch;
    Calendar mcal;
    View view;
    String pickDate;
    private ArrayAdapter<String> choose;
    private DocumentReference mDocRef;
    private CollectionReference mColRef;
    private ArrayList<incomeStruc> data;
    private ArrayList<expensesStruc> data2;
    private  List<PieEntry> list;

    /**
     * Constructor for myCostView
     * @param context the root activity
     */
    public myCostView(Context context, Member tempuser) {
        super(context);
        userData = tempuser;
        mcal = Calendar.getInstance();
        //Create the new view object, and Inflate by the Inflater from context which you send
        //the root will be set to itself.
        view = LayoutInflater.from(context).inflate(R.layout.cost_view, null);
        btnRefresh = view.findViewById(R.id.btnChartRefresh);
        editChartDateStart = view.findViewById(R.id.editChartDateStart);
        editChartDateEnd = view.findViewById(R.id.editChartDateEnd);
        spinnerChartSwitch = view.findViewById(R.id.spinnerChartSwitch);
        btn3 = view.findViewById(R.id.btn3);

        editChartDateStart.setText("2020/1/10");
        editChartDateEnd.setText("2020/1/28");
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        list = new ArrayList<>() ;

        String engincome = getResources().getString(R.string.engincome);
        String endexpense= getResources().getString(R.string.engExpense);
        choose = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,new String[]{engincome,endexpense});
        spinnerChartSwitch.setAdapter(choose);

        //add view to current viewGroup (RelativeLayout)
        addView(view);
        showIncome(20,20,20,20,20,0,0,0) ;

        btnRefresh.setOnClickListener(this);
        editChartDateStart.setOnClickListener(this);
        editChartDateEnd.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }
    @Override
    public void refreshView() {

    }


    private void showIncome(int amountSal,int amountMon,int amountHelp,int amountShoot,int amountOther,int amountPlay,int amountExOther,int choose) {
        list.clear();
        pieChart = (PieChart) findViewById(R.id.act_pie_pieChart) ;
        //如果啟用此選項,則圖表中的值將以百分比形式繪製,而不是以原始值繪製
        pieChart.setUsePercentValues(true);
        //如果這個元件應該啟用(應該被繪製)FALSE如果沒有。如果禁用,此元件的任何內容將被繪製預設
        pieChart.getDescription().setEnabled(false);
        //將額外的偏移量(在圖表檢視周圍)附加到自動計算的偏移量
        pieChart.setExtraOffsets(5, 10, 5, 5);
        //較高的值表明速度會緩慢下降 例如如果它設定為0,它會立即停止。1是一個無效的值,並將自動轉換為0.999f。
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //設定中間字型

        //設定為true將餅中心清空
        pieChart.setDrawHoleEnabled(false);
        //套孔,繪製在PieChart中心的顏色
        pieChart.setHoleColor(Color.WHITE);
        //設定透明圓應有的顏色。
        pieChart.setTransparentCircleColor(Color.WHITE);
        //設定透明度圓的透明度應該有0 =完全透明,255 =完全不透明,預設值為100。
        pieChart.setTransparentCircleAlpha(110);
        //設定在最大半徑的百分比餅圖中心孔半徑(最大=整個圖的半徑),預設為50%
        pieChart.setHoleRadius(58f);
        //設定繪製在孔旁邊的透明圓的半徑,在最大半徑的百分比在餅圖*(max =整個圖的半徑),預設55% -> 5%大於中心孔預設
        pieChart.setTransparentCircleRadius(61f);
        //將此設定為true,以繪製顯示在pie chart
        pieChart.setDrawCenterText(true);
        //集度的radarchart旋轉偏移。預設270f -->頂(北)
        pieChart.setRotationAngle(0);
        //設定為true,使旋轉/旋轉的圖表觸控。設定為false禁用它。預設值:true
        pieChart.setRotationEnabled(true);
        //將此設定為false,以防止由抽頭姿態突出值。值仍然可以通過拖動或程式設計高亮顯示。預設值:真
        pieChart.setHighlightPerTapEnabled(true);
        //建立Legend物件
        Legend l = pieChart.getLegend();
        //設定垂直對齊of the Legend
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //設定水平of the Legend
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //設定方向
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        //其中哪一個將畫在圖表或外
        l.setDrawInside(false);
        //設定水平軸上圖例項之間的間距
        l.setXEntrySpace(7f);
        //設定在垂直軸上的圖例項之間的間距
        l.setYEntrySpace(0f);
        //設定此軸上標籤的所使用的y軸偏移量 更高的偏移意味著作為一個整體的Legend將被放置遠離頂部。
        l.setYOffset(0f);
        //設定入口標籤的顏色。
        pieChart.setEntryLabelColor(Color.WHITE);
        //設定入口標籤的大小。預設值:13dp
        pieChart.setEntryLabelTextSize(30f);
        //模擬的資料來源
        if(choose == 0){
            //收入
            PieEntry x1 = new PieEntry(amountSal , "薪水" , R.color.colorAccent) ;
            PieEntry x2 = new PieEntry(amountMon , "獎金") ;
            PieEntry x3 = new PieEntry(amountHelp , "補助") ;
            PieEntry x4 = new PieEntry(amountShoot , "投資") ;
            PieEntry x5 = new PieEntry(amountOther , "其他") ;
            list.add(x1) ;
            list.add(x2) ;
            list.add(x3) ;
            list.add(x4) ;
            list.add(x5) ;
            pieChart.setCenterText("收入");
        }else{
            //支出
            PieEntry x1 = new PieEntry(amountSal , "食" , R.color.colorAccent) ;
            PieEntry x2 = new PieEntry(amountMon , "衣") ;
            PieEntry x3 = new PieEntry(amountHelp , "住") ;
            PieEntry x4 = new PieEntry(amountShoot , "行") ;
            PieEntry x5 = new PieEntry(amountOther , "育") ;
            PieEntry x6 = new PieEntry(amountPlay , "樂") ;
            PieEntry x7 = new PieEntry(amountExOther , "其他") ;
            list.add(x1) ;
            list.add(x2) ;
            list.add(x3) ;
            list.add(x4) ;
            list.add(x5) ;
            list.add(x6) ;
            list.add(x7) ;
            pieChart.setCenterText("支出");
        }
        PieDataSet set;

        //設定到PieDataSet物件
        if(choose == 0){
             set = new PieDataSet(list , "收入") ;
        }else{
             set = new PieDataSet(list , "支出") ;
        }

        set.setDrawValues(true);//設定為true,在圖表繪製y
        set.setValueTextSize(20f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);//設定Y軸,這個資料集應該被繪製(左或右)。預設值:左
        set.setAutomaticallyDisableSliceSpacing(false);//當啟用時,片間距將是0時,最小值要小於片間距本身
        set.setSliceSpace(5f);//間隔
        set.setSelectionShift(10f);//點選伸出去的距離
        /**
         * 設定該資料集前應使用的顏色。顏色使用只要資料集所代表的條目數目高於顏色陣列的大小。
         * 如果您使用的顏色從資源, 確保顏色已準備好(通過呼叫getresources()。getColor(…))之前,將它們新增到資料集
         * */
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        set.setColors(colors);
        //傳入PieData
        PieData data = new PieData(set);
        //為圖表設定新的資料物件
        pieChart.setData(data);
        //重新整理
        pieChart.invalidate();
        //動畫圖上指定的動畫時間軸的繪製
       // pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        data.notifyDataChanged();
        pieChart.invalidate();

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnChartRefresh:
                String start = editChartDateStart.getText().toString();
                String end = editChartDateEnd.getText().toString();
                String[] start_year_month = start.split("/");
                String[] end_year_month = end.split("/");
                int choose = spinnerChartSwitch.getSelectedItemPosition();
                if(choose == 0 ){
                    //收入

                    dealTheData(start_year_month,end_year_month,0);
                }else{
                    //支出
                    Log.d("SSS","in");
                    dealTheData(start_year_month,end_year_month,1);
                }


                break;
            case R.id.editChartDateStart:
                new DatePickerDialog(view.getContext(),this,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();

                break;

            case R.id.editChartDateEnd:
                listenspinner2 templ = new listenspinner2(view);
                new DatePickerDialog(view.getContext(),templ,
                        mcal.get(Calendar.YEAR),
                        mcal.get(Calendar.MONTH),
                        mcal.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;

            case R.id.btn3:
                Log.d("SSS","btn3");
                int choose2 = spinnerChartSwitch.getSelectedItemPosition();
                handChart(choose2);
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        editChartDateStart.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }

    private void dealTheData(String[] tempStart,String[] tempEnd,int choose){
        if(choose == 0){
            //收入
            String startYear = tempStart[0];
            String startMonth = tempStart[1];
            String endMonth = tempEnd[1];
            String startDate = tempStart[2];
            String endDate = tempEnd[2];
            int startMonthInt = Integer.parseInt(startMonth);
            int endMonthInt = Integer.parseInt(endMonth);
            int startDateInt = Integer.parseInt(startDate);
            int endDateInt = Integer.parseInt(endDate);


            String colPath = "";
            Log.d("SSS",startMonth);
            Log.d("SSS",endMonth);
            colPath = "2020/"+String.valueOf(startMonthInt);
            Log.d("SSS",colPath);
            data.clear();
                for(int i=startDateInt;i<=endDateInt;i++) {
                     colPath = "2020/"+startMonth+"/"+String.valueOf(i);
                    //Log.d("SSS", colPath);
                    mColRef = FirebaseFirestore.getInstance().collection("Users/" + userData.showemail() + "/IE").document("Income").collection(colPath);
                    mColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        // List<PieEntry> list = new ArrayList<>() ;


                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.d("SSS", "inin2");

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("SSS", document.getId() + " => " + document.getData());
                                    incomeStruc tempIncome = new incomeStruc( document.getString("incomeAmount"),document.getString("incomeClass"),document.getString("incomeDate"),document.getString("incomeText"));
                                    tempIncome.setDocid(document.getId());//record the docid
                                    data.add(tempIncome);
                                }

                            } else {
                                Log.d("2ININ", "Error getting documents: ", task.getException());
                            }


                        }

                    });
                }
            View vs = view.findViewById(R.id.snack3);
            Snackbar.make(vs,"完成獲取！",Snackbar.LENGTH_LONG).show();


        }else{
            //支出
            String startYear = tempStart[0];
            String startMonth = tempStart[1];
            String endMonth = tempEnd[1];
            String startDate = tempStart[2];
            String endDate = tempEnd[2];
            int startMonthInt = Integer.parseInt(startMonth);
            int endMonthInt = Integer.parseInt(endMonth);
            int startDateInt = Integer.parseInt(startDate);
            int endDateInt = Integer.parseInt(endDate);


            String colPath = "";
            Log.d("SSS",startMonth);
            Log.d("SSS",endMonth);
            colPath = "2020/"+String.valueOf(startMonthInt);
            Log.d("SSS",colPath);
            data2.clear();
            for(int i=startDateInt;i<=endDateInt;i++) {
                colPath = "2020/"+startMonth+"/"+String.valueOf(i);
                //Log.d("SSS", colPath);
                mColRef = FirebaseFirestore.getInstance().collection("Users/" + userData.showemail() + "/IE").document("Expenses").collection(colPath);
                mColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // List<PieEntry> list = new ArrayList<>() ;


                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("SSS", "inin2");

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("SSS", document.getId() + " => " + document.getData());
                                expensesStruc tempExpense = new expensesStruc(document.getString("expenseAmount"),document.getString("expenseClass"),document.getString("expenseStore"),document.getString("expenseDate"),document.getString("expenseText"));
                                tempExpense.setDocid(document.getId());//record the docid
                                data2.add(tempExpense);
                            }
                        } else {
                            Log.d("2ININ", "Error getting documents: ", task.getException());
                        }


                    }

                });
            }
            View vs = view.findViewById(R.id.snack3);
            Snackbar.make(vs,"完成獲取！",Snackbar.LENGTH_LONG).show();
        }

    }
      private void handChart(int choose){
        if(choose == 0){
            //收入
            Log.d("SSS","hanin");
            int amountSal = 0;
            int amountMon = 0;
            int amountHelp = 0;
            int amountShoot = 0;
            int amountOther = 0;
            for (incomeStruc tremp:data){
                switch(tremp.getIncomeClass()){
                    case "薪水":
                        amountSal += Integer.parseInt(tremp.getIncomeAmount());
                        break;
                    case "獎金":
                        amountMon += Integer.parseInt(tremp.getIncomeAmount());
                        break;
                    case "補助":
                        amountHelp += Integer.parseInt(tremp.getIncomeAmount());
                        break;
                    case "投資":
                        amountShoot += Integer.parseInt(tremp.getIncomeAmount());
                        break;
                    case "其他":
                        amountOther+= Integer.parseInt(tremp.getIncomeAmount());
                        break;
                }
            }

            Log.d("SSS",String.valueOf(amountSal));
            Log.d("SSS",String.valueOf(amountMon));
            Log.d("SSS",String.valueOf(amountHelp));
            Log.d("SSS",String.valueOf(amountShoot));
            Log.d("SSS",String.valueOf(amountOther));


            showIncome(amountSal,amountMon,amountHelp,amountShoot,amountOther,0,0,0);
        }else{
            //支出
            Log.d("SSS","hanin");
            int amountSal = 0;
            int amountMon = 0;
            int amountHelp = 0;
            int amountShoot = 0;
            int amountOther = 0;
            int amountPlay = 0;
            int amountExOther = 0;
            for (expensesStruc tremp:data2){
                switch(tremp.getExpenseClass()){
                    case "食":
                        amountSal += Integer.parseInt(tremp.getExpenseAmount());
                        break;
                    case "衣":
                        amountMon += Integer.parseInt(tremp.getExpenseAmount());
                        break;
                    case "住":
                        amountHelp += Integer.parseInt(tremp.getExpenseAmount());
                        break;
                    case "行":
                        amountShoot += Integer.parseInt(tremp.getExpenseAmount());
                        break;
                    case "育":
                        amountOther+= Integer.parseInt(tremp.getExpenseAmount());
                        break;
                    case "樂":
                        amountPlay+= Integer.parseInt(tremp.getExpenseAmount());
                        break;
                    case "其他":
                        amountExOther+= Integer.parseInt(tremp.getExpenseAmount());
                        break;
                }
            }

            Log.d("SSS",String.valueOf(amountSal));
            Log.d("SSS",String.valueOf(amountMon));
            Log.d("SSS",String.valueOf(amountHelp));
            Log.d("SSS",String.valueOf(amountShoot));
            Log.d("SSS",String.valueOf(amountOther));
            Log.d("SSS",String.valueOf(amountPlay));
            Log.d("SSS",String.valueOf(amountExOther));


            showIncome(amountSal,amountMon,amountHelp,amountShoot,amountOther,amountPlay,amountExOther,1);
        }

      }


}

class listenspinner2 implements DatePickerDialog.OnDateSetListener{
    public String pickDate;
    EditText editDateEnd;
    View view;

   public listenspinner2(View tempview){
       view = tempview;
       editDateEnd = view.findViewById(R.id.editChartDateEnd);

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       editDateEnd.setText(year+"/"+(month+1)+"/"+dayOfMonth);
    }

}
