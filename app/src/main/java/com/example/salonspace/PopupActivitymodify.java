package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class PopupActivitymodify extends Activity {
    EditText menu_name;
    EditText menu_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_activitymodify);
        menu_name=(EditText)findViewById(R.id.menu_name2);
        menu_price=(EditText)findViewById(R.id.menu_price2);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String price=intent.getStringExtra("price");
        menu_name.setText(name);
        menu_name.setEnabled(false);
        menu_price.setText(price);
    }
    //확인 버튼 클릭
    public void Okbtn2(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result","success");
        intent.putExtra("name", menu_name.getText().toString());
        intent.putExtra("price", menu_price.getText().toString());
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }
    //취소 버튼 클릭
    public void Nobtn2(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "error");
        intent.putExtra("name", menu_name.getText().toString());
        intent.putExtra("price", menu_price.getText().toString());

        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}