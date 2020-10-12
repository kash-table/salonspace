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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


public class SignupActivity extends AppCompatActivity {
    RadioButton rbtn_ctm, rbtn_dsg;
    Button btn_signup,btn_check;
    CheckBox agree;
    //Toolbar toolbar;
    EditText name_edit,pwd_edit,pwd_edit2,email_edit,contact_edit;
    //회원정보
    String usertp,name,email,pwd,pwd2,contact;
    String idcheck;
    // 중복확인 체크했는지
    int email_check=0;
    // null값이 있는지
    int check_null=0;
    // 제대로 입력을 했는지
    int confirm_input=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //editText 주소값
        name_edit=findViewById(R.id.name);
        pwd_edit=findViewById(R.id.pwd);
        pwd_edit2=findViewById(R.id.pwd2);
        email_edit=findViewById(R.id.email);
        contact_edit=findViewById(R.id.contactnumber);
        //체크박스 주소값
        agree=findViewById(R.id.checkBox5);
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

        // 중복확인 버튼
        btn_check=findViewById(R.id.check);
        btn_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                email=email_edit.getText().toString();
                InsertData2 insertdata = new InsertData2();
                insertdata.execute(getString(R.string.IP_ADDRESS)+"IDcheck.php",email);
            }
        });

        //회원가입 버튼
        btn_signup= findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_null=0;
                confirm_input=0;
                name=name_edit.getText().toString();
                email=email_edit.getText().toString();
                pwd=pwd_edit.getText().toString();
                pwd2=pwd_edit2.getText().toString();
                contact=contact_edit.getText().toString();


                if(rbtn_ctm.isChecked()){
                    usertp="0";
                    //intent.putExtra("usertype", 0);
                }else{
                    usertp="1";
                    //intent.putExtra("usertype", 1);
                }
                if(name.getBytes().length>0){
                    check_null++;
                }
                if(pwd.getBytes().length>0){
                    check_null++;
                }
                if(pwd2.getBytes().length>0){
                    check_null++;
                }
                if(email.getBytes().length>0){
                    check_null++;
                }
                if(contact.getBytes().length>0){
                    check_null++;
                }
                if(check_null!=5){
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "모든 항목에 대해 기입해주세요.");
                    confirm_input=1;
                    startActivityForResult(intent, 1);
                }
                else if(!pwd.equals(pwd2)){
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "비밀번호가 다릅니다.");
                    confirm_input=1;
                    startActivityForResult(intent, 1);
                }
                else if(email_check<1){
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "중복확인을 해주세요.");
                    confirm_input=1;
                    startActivityForResult(intent, 1);
                }
                else if(!email.contains("@")){
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "이메일 형식이 잘못되었습니다.");
                    confirm_input=1;
                    startActivityForResult(intent, 1);
                }
                else if(pwd.getBytes().length<6){
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "비밀번호는 6자리 이상 입력주세요.");
                    confirm_input=1;
                    startActivityForResult(intent, 1);
                }
                else if(!agree.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "이용 약관에 동의해주세요.");
                    confirm_input=1;
                    startActivityForResult(intent, 1);
                }
                if(confirm_input==0) {
                    InsertData insertdata = new InsertData();
                    insertdata.execute(getString(R.string.IP_ADDRESS)+"signup.php",name,email,pwd,contact,usertp);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                    //데이터 담아서 팝업(액티비티) 호출
                    intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("data", "Salon Space에 오신 것을 환영합니다.");
                    startActivityForResult(intent, 1);
                }



            }
        });
    }

    //아이디 중복확인
    class InsertData2 extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            idcheck=s;
            Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
            if(idcheck.equals("success")) {
                intent.putExtra("data", "사용가능한 이메일주소입니다.");
                email_check=1;
                startActivityForResult(intent, 1);
                }
            if(idcheck.equals("error")) {
                intent.putExtra("data", "이메일 주소 중복입니다.");
                email_check=0;
                startActivityForResult(intent, 1);
            }
            Log.d("test4",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String email = params[1];
            String postparameters = "email="+email;

            try{
                URL url = new URL(serverurl);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.connect();

                OutputStream outputstream = conn.getOutputStream();
                outputstream.write(postparameters.getBytes("UTF-8"));
                outputstream.flush();
                outputstream.close();

                InputStream inputstream;

                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    inputstream = conn.getInputStream();
                }else{
                    inputstream = conn.getErrorStream();
                }

                InputStreamReader inputreader = new InputStreamReader(inputstream, "UTF-8");
                BufferedReader bufferedreader = new BufferedReader(inputreader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                int a=1;
                while((line = bufferedreader.readLine())!=null){
                    sb.append(line);
                    a++;
                }

                bufferedreader.close();
                Log.d("testresultidcheck",sb.toString());
                return sb.toString();

            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
    //회원가입
    class InsertData extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("test3",s);


        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String name = params[1];
            String email = params[2];
            String pwd = params[3];
            String contact = params[4];
            String usertype = params[5];
            Log.d("testdb",name+"  "+email+"   "+pwd+"    "+contact+"   "+usertype);

            String postparameters = "name="+name+"&email="+email+"&pwd="+pwd+"&contact="+contact+"&usertype="+usertype;

            try{
                URL url = new URL(serverurl);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.connect();

                OutputStream outputstream = conn.getOutputStream();
                outputstream.write(postparameters.getBytes("UTF-8"));
                outputstream.flush();
                outputstream.close();

                InputStream inputstream;

                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    inputstream = conn.getInputStream();
                }else{
                    inputstream = conn.getErrorStream();
                }

                InputStreamReader inputreader = new InputStreamReader(inputstream, "UTF-8");
                BufferedReader bufferedreader = new BufferedReader(inputreader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                int a=1;
                while((line = bufferedreader.readLine())!=null){
                    sb.append(line);
                    a++;
                }

                bufferedreader.close();
                Log.d("testresult",sb.toString());
                return sb.toString();

            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

}