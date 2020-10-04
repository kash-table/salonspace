package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class PopupActivity2 extends Activity {
    EditText name;
    EditText price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup2);
        name=(EditText)findViewById(R.id.menu_name2);
        price=(EditText)findViewById(R.id.menu_price2);

    }
    //확인 버튼 클릭
    public void Okbtn(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result","success");
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("price", price.getText().toString());
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }
    //취소 버튼 클릭
    public void Nobtn(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "error");
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