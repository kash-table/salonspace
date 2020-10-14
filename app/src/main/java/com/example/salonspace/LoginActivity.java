package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Button btn_login, btn_signup;
    Button findID;
    Button findPW;
    EditText ID,PW;
    CheckBox login_checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findID=findViewById(R.id.button2);
        findPW=findViewById(R.id.button);
        //자동로그인 주소값
        login_checkbox=findViewById(R.id.checkbox_login);
        //default값으로 자동로그인 체크
        login_checkbox.setChecked(true);

        // 아이디 비밀번호 edittext 값저장
        ID=findViewById(R.id.editTextTextEmailAddress2);
        PW=findViewById(R.id.editTextTextPassword3);

        // 자동로그인
        DBHelper helper =new DBHelper(getApplicationContext());
        SQLiteDatabase db=helper.getReadableDatabase();

        String autoLogin="select count(*) as num, UserType from Login";
        Cursor c=db.rawQuery(autoLogin,null);
        int total_count=0;
        String Usertype="";
        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호 추출
            int index=c.getColumnIndex("num");
            int index2=c.getColumnIndex("UserType");
            total_count=c.getInt(index);
            Usertype=c.getString(index2);
            Log.d("test",""+total_count);
        }
        //행 1개 이상이면 자동로그인
        if(total_count>=1){
            Log.e("UserType", Usertype);
            if(Usertype.equals("0")) {
                Intent intent=new Intent(getApplicationContext(),MainActivity_custom.class);
                startActivity(intent);
            }
            else if(Usertype.equals("1")){
                Intent intent=new Intent(getApplicationContext(),MainActivity4Designer.class);
                startActivity(intent);
            }
        }

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id=ID.getText().toString();
                String pw=PW.getText().toString();

                InsertData insertdata = new InsertData();
                insertdata.execute(getString(R.string.IP_ADDRESS)+"login.php",id,pw);


                // 내부 db 저장
                // DB 오픈


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
    //아이디찾기 비번찾기
    public void btnID(View v){
        Intent intent = new Intent(getApplicationContext(),FindID.class);
        startActivity(intent);
    }
    public void btnPW(View v){
        Intent intent = new Intent(getApplicationContext(),FindPW.class);
        startActivity(intent);
    }
    //db연동
    class InsertData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("0")){
                DBHelper helper =new DBHelper(getApplicationContext());
                SQLiteDatabase db=helper.getReadableDatabase();
                //값 가져오기
                String name=ID.getText().toString();
                String passwd=PW.getText().toString();
                String usertype2="0";
                //쿼리문 작성

                String sql = "insert into Login(ID,PW,UserType) values (?,?,?)";
                String[] arg1 = {name, passwd, usertype2};

                //저장ㅣ
                db.execSQL(sql, arg1);
                db.close();

                Intent intent=new Intent(getApplicationContext(),MainActivity_custom.class);
                startActivity(intent);
            }
            if(s.equals("1")){
                DBHelper helper =new DBHelper(getApplicationContext());
                SQLiteDatabase db=helper.getReadableDatabase();
                //값 가져오기
                String name=ID.getText().toString();
                String passwd=PW.getText().toString();
                String usertype2="1";
                //쿼리문 작성

                String sql = "insert into Login(ID,PW,UserType) values (?,?,?)";
                String[] arg1 = {name, passwd, usertype2};

                //저장
                db.execSQL(sql, arg1);
                db.close();

                Intent intent=new Intent(getApplicationContext(),MainActivity4Designer.class);
                startActivity(intent);
            }
            if(s.equals("error")){
                //데이터 담아서 팝업(액티비티) 호출
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("data", "아이디 또는 비밀번호가 잘못되었습니다.");
                startActivityForResult(intent, 1);
            }
            Log.d("test4",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String id = params[1];
            String pw =params[2];
            String postparameters = "email="+id+"&pwd="+pw;

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
}