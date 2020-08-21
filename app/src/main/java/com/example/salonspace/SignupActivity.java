package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class SignupActivity extends AppCompatActivity {
    RadioButton rbtn_ctm, rbtn_dsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
    }
}