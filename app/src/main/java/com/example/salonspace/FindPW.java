package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FindPW extends AppCompatActivity {
    EditText name,email,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_p_w);
        name=findViewById(R.id.name3);
        email=findViewById(R.id.email3);
        contact=findViewById(R.id.contact3);
    }
    public void mOnPopupClickk(View v){

        String name_value=name.getText().toString();
        String email_value=email.getText().toString();
        String contact_value=contact.getText().toString();

        InsertData insertdata = new InsertData();
        insertdata.execute(getString(R.string.IP_ADDRESS)+"findPWD.php",name_value,email_value,contact_value);

    }
    public void btn5(View v){
        String name_value=name.getText().toString();
        String email_value=email.getText().toString();
        String contact_value=contact.getText().toString();

        InsertData insertdata = new InsertData();
        insertdata.execute(getString(R.string.IP_ADDRESS)+"findPWD.php",name_value,email_value,contact_value);

    }

    class InsertData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            s=s.replace("\t","");
            if(s.equals("error")){
                //데이터 담아서 팝업(액티비티) 호출
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("data", "일치하는 회원정보가 없습니다.");
                startActivityForResult(intent, 1);
            }
            else{
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("data", "비밀번호는 : "+s+"입니다");
                startActivityForResult(intent, 1);
            }

            Log.d("test4",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String name_value = params[1];
            String email_value =params[2];
            String contact_value=params[3];
            String postparameters = "name="+name_value+"&contact="+contact_value+"&email="+email_value;
            Log.d("testinput",postparameters);
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

                StringBuffer sb = new StringBuffer();
                String line = null;
                int a=1;
                while((line = bufferedreader.readLine())!=null){
                    sb.append(line);
                    Log.d("testsb",sb.toString());
                    Log.d("testline",line);
                    a++;
                }

                bufferedreader.close();
                Log.d("testresultidcheck", sb.toString());
                return sb.toString();

            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}