package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FindID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_i_d);


    }
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("data", "등록하신 연락처로 아이디를 보내드렸습니다.");
        startActivityForResult(intent, 1);
    }
}