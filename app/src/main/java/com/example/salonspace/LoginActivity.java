package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_signup;
    Button findID;
    Button findPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findID=findViewById(R.id.button2);
        findPW=findViewById(R.id.button);



        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int usertype = intent.getIntExtra("usertype", 2);
                Log.d("usertype", usertype+"");
                if(usertype==0){
                    Intent intent_ctm = new Intent(getApplicationContext(), MainActivity_custom.class);
                    startActivity(intent_ctm);
                }else if(usertype==1){
                    Intent intent_dsg = new Intent(getApplicationContext(), MainActivity4Designer.class);
                    startActivity(intent_dsg);
                }
            }
        });

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_signup = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent_signup);
            }
        });

    }
    public void btnID(View v){
        Intent intent=new Intent(this,FindID.class);
        startActivity(intent);
    }
    public void btnPW(View v){
        Intent intent=new Intent(this,FindPW.class);
        startActivity(intent);
    }
}