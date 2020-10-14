package com.example.salonspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {
    ArrayList<String> name;
    ArrayList<String> price;
    int sum;
    ListView list1;
    TextView result;
    ArrayList<HashMap<String,Object>> data_list;
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        list1=(ListView)findViewById(R.id.menupan);
        result=(TextView)findViewById(R.id.resultp);
        // 변수 선언
        name=new ArrayList<String>();
        price=new ArrayList<String>();
        data_list=new ArrayList<HashMap<String, Object>>();

        Intent intent=getIntent();
        name=(ArrayList<String>)intent.getSerializableExtra("listn");
        price=(ArrayList<String>)intent.getSerializableExtra("listp");
        sum=intent.getIntExtra("price",0);


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
        result.setText(sum+"");
    }
    public void gotoMain(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity_custom.class);
        startActivity(intent);
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
}
