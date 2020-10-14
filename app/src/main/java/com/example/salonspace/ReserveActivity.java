package com.example.salonspace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.lang.invoke.MutableCallSite;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ReserveActivity extends AppCompatActivity {

    // day listview
    private RecyclerView listview;
    private MyAdapter adapter;
    private MyAdapter_time adapter_time;
    private TextView a, b;
    int sum;
    ArrayList<HashMap<String,Object>> data_list;
    //메뉴판 리스트뷰
    ListView list1;
    TextView test;
    //메뉴판 받아올 객체
    ArrayList<String> name;
    ArrayList<String> price;
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        list1=(ListView)findViewById(R.id.list_finalmenu);
        test=(TextView)findViewById(R.id.sum_price);
        data_list=new ArrayList<HashMap<String, Object>>();

        //메뉴판 받아오기
        name=new ArrayList<String>();
        price=new ArrayList<String>();
        Intent intent=getIntent();
        name=(ArrayList<String>)intent.getSerializableExtra("listn");
        price=(ArrayList<String>)intent.getSerializableExtra("listp");
        sum=intent.getIntExtra("price",0);
        //메뉴판 설정
        for(int i=0;i<name.size();i++){
            //메뉴판 추가
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("menu_name", name.get(i));
            map.put("menu_price", price.get(i));
            data_list.add(map);
            String[] keys = {"menu_name", "menu_price"};
            int[] ids = {R.id.menu_name, R.id.menu_price};
            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data_list, R.layout.menu_customlistview, keys, ids);
            list1.setAdapter(adapter);
            setListViewHeightBasedOnChildren(list1);
        }

        test.setText(sum+"");
        // 날짜 불러오기
        dateInit();

        // 시간 불러오기
        timeInit();
    }
    // 리스트뷰 높이 자동설정
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
    private void dateInit(){
        listview = findViewById(R.id.main_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);

        String[] dayOfWeek = {"일","월","화","수","목","금","토"};
        ArrayList<MyAdapter.ReserveInfo> itemList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        for (int i = 0; i < 14; i++) {
            MyAdapter.ReserveInfo _t = new MyAdapter.ReserveInfo();

            _t.day = "" + dayOfWeek[(int)(cal.get(Calendar.DAY_OF_WEEK)) - 1];
            _t.date = "" + cal.get(Calendar.DATE);
            if (i == 0) _t.today = "오늘";
            itemList.add(_t);
            cal.add(Calendar.DATE, 1);
        }


        adapter = new MyAdapter(this, itemList, onClickItem);
        listview.setAdapter(adapter);

        MyListDecoration decoration = new MyListDecoration();
        listview.addItemDecoration(decoration);
    }

    private void timeInit(){
        listview = findViewById(R.id.time_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);

        ArrayList<String> itemList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        for (int i = 20; i < 40; i++) {
            itemList.add(getTimeByIndex(i));
        }


        adapter_time = new MyAdapter_time(this, itemList, onClickTime);
        listview.setAdapter(adapter_time);

        MyListDecoration decoration = new MyListDecoration();
        listview.addItemDecoration(decoration);
    }
    private String getTimeByIndex(int index){
        String hour = "" + (index < 20 ? "0" + (int)(index / 2) : "" + (int)(index / 2));
        String min = (index % 2 == 0) ? "00" : "30";
        return hour + ":" + min;
    }

    // 날짜 클릭 시
    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            adapter.setSelected_date(str);
            if (a!=null) a.setTextColor(Color.parseColor("#000000"));
            a = (TextView) v;
            a.setTextColor(Color.parseColor("#FE5722"));
            // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    };

    // 시간 클릭 시
    private View.OnClickListener onClickTime = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            adapter_time.setSelected_tag(str);
            if (b!=null) b.setTextColor(Color.parseColor("#000000"));
            b = (TextView) v;
            b.setTextColor(Color.parseColor("#FE5722"));
            // Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        }
    };
    public void onReserveClick(View v){
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
        intent.putExtra("price",sum);
        intent.putExtra("listn",name);
        intent.putExtra("listp",price);
        startActivity(intent);
    }
}
