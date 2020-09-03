package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    RadioButton rbtn_ctm, rbtn_dsg;
    Button btn_signup;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //toolbar = (Toolbar)findViewById(R.id.toolbar3);
        //getSupportActionBar();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        //signup whether customer or designer
        rbtn_ctm = findViewById(R.id.rbtn_ctm);
        rbtn_dsg = findViewById(R.id.rbtn_dsg);
        rbtn_ctm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    compoundButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor(("#FF9800"))));
                }else{
                    compoundButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor(("#000000"))));
                }
            }
        });
        rbtn_dsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    compoundButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor(("#FF9800"))));
                }else{
                    compoundButton.setButtonTintList(ColorStateList.valueOf(Color.parseColor(("#000000"))));
                }
            }
        });

        btn_signup= findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                if(rbtn_ctm.isChecked()){
                    intent.putExtra("usertype", 0);
                }else{
                    intent.putExtra("usertype", 1);
                }
                startActivity(intent);
            }
        });
    }
}