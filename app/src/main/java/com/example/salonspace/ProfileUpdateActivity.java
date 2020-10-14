package com.example.salonspace;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ProfileUpdateActivity extends AppCompatActivity {
    ListView list1;
    // 리스트뷰에 넣을 데이터
    String ID;
    String info;
    // 자기소개
    EditText edit_info;
    EditText addtional_info;
    TextView edit_name;
    //처음 db에서 받아올 메뉴들
    ArrayList<String> m_name;
    ArrayList<String> m_price;
    //프로이미지뷰
    ImageView image_profile;
    ArrayList<HashMap<String,Object>> data_list=new ArrayList<HashMap<String, Object>>();
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        edit_name=(TextView) findViewById(R.id.info_name);
        list1=(ListView)findViewById(R.id.list1);
        //리스너 셋팅
        ListListner listner=new ListListner();
        list1.setOnItemClickListener(listner);
        m_name=new ArrayList<>();
        m_price=new ArrayList<>();
        //내부 DB오픈
        DBHelper helper =new DBHelper(getApplicationContext());
        SQLiteDatabase db=helper.getReadableDatabase();

        String LoadID="select ID from Login";
        Cursor c=db.rawQuery(LoadID,null);
        ID="";
        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호 추출
            int index=c.getColumnIndex("ID");
            ID=c.getString(index);
            Log.d("testID",""+ID);
        }

        //자기소개 text값 가져오기
        edit_info=(EditText)findViewById(R.id.info);
        //추가정보 값 가져오기
        // addtional_info=(EditText)findViewById(R.id.addtional);
        //처음 시작이 외부 db에서 디자이너 정보 가져오기.
//        Log.d("test_info",info);
        InsertData3 insertData=new InsertData3();
        insertData.execute(getString(R.string.IP_ADDRESS)+"get_designer_info.php",edit_info.getText().toString());
//처음 시작이 외부 db에서 메뉴판 가져오기.
        InsertData4 insertData4=new InsertData4();
        insertData4.execute(getString(R.string.IP_ADDRESS)+"get_designer_style_table.php");

        //
        image_profile=(ImageView)findViewById(R.id.profile_image_5);
        InsertData6 insertDate5=new InsertData6();
        insertDate5.execute(getString(R.string.IP_ADDRESS)+"get_designer_image.php",ID);

    }
    //리스너 셋팅
    class ListListner implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("test@",i+"번째 메뉴 값: "+m_name.get(i)+"/"+m_price.get(i));
            Intent intent=new Intent(getApplicationContext(),PopupActivitymodify.class);
            intent.putExtra("name",m_name.get(i));
            intent.putExtra("price",m_price.get(i));
            startActivityForResult(intent,1);
        }
    }

    // 팝업 액티비티후 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String name=data.getStringExtra("name");
                String result=data.getStringExtra("result");
                String price=data.getStringExtra("price");

                if(result.equals("success")) {
                    /*HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("menu_name", name);
                    map.put("menu_price", price);
                    data_list.add(map);
                    String[] keys = {"menu_name", "menu_price"};
                    int[] ids = {R.id.menu_name, R.id.menu_price};
                    SimpleAdapter adapter = new SimpleAdapter(this, data_list, R.layout.menu_customlistview, keys, ids);
                    list1.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(list1);
*/
                    InsertData insertData=new InsertData();
                    insertData.execute(getString(R.string.IP_ADDRESS)+"upload_designer_table.php",name,price);
                    //초기화 - 외부db에서 다시 받아서 화면에 출력
                    InsertData4 insertData4=new InsertData4();
                    insertData4.execute(getString(R.string.IP_ADDRESS)+"get_designer_style_table.php");
                }
                if(result.equals("error")){
                    InsertData5 insertData5=new InsertData5();
                    insertData5.execute(getString(R.string.IP_ADDRESS)+"delete_designer_table.php",name);
                    //초기화
                    InsertData4 insertData4=new InsertData4();
                    insertData4.execute(getString(R.string.IP_ADDRESS)+"get_designer_style_table.php");

                }

            }
        }
    }

    // 설정하기 버튼 누르면
    public void test(View v){
        Button test=(Button)findViewById(R.id.btn_set_work);
        Intent intent = new Intent(getApplicationContext(), PopupActivity2.class);
        startActivityForResult(intent, 1);

    }
    // 디자이너 자기소개 설정하기 버튼 온클릭
    public void info(View v){
        info=edit_info.getText().toString();
        Log.d("test_info",info);
        InsertData2 insertData=new InsertData2();
        insertData.execute(getString(R.string.IP_ADDRESS)+"change_designer_info.php",info);
        Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
        intent.putExtra("data", "변경이 완료되었습니다.");
        startActivityForResult(intent, 1);
    }
    public void additionalinfo(View v){
        return;
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
    // 외부 db와 메뉴판 등록 ( 설정하기(추가) 버튼 누르면 실행되야됨)
    class InsertData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("test4",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String id=ID;
            String serverurl = params[0];
            String menu_name = params[1];
            String menu_price =params[2];
            String postparameters = "id="+ID+"&style="+menu_name+"&cost="+Integer.parseInt(menu_price);
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
    //처음시작시 메뉴판 가져오는 코드
    class InsertData4 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //arraylist 비우기
            m_name.clear();
            m_price.clear();
            data_list.clear();
            try {
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String n=jsonObject.getString("style");
                    String p=jsonObject.getString("cost");
                    m_name.add(n);
                    m_price.add(p);
                    Log.d("testarrayp",m_price.get(i));
                    Log.d("testarrayn",m_name.get(i));
                    //메뉴판 추가
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("menu_name", n);
                    map.put("menu_price", p);
                    data_list.add(map);
                    String[] keys = {"menu_name", "menu_price"};
                    int[] ids = {R.id.menu_name, R.id.menu_price};
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data_list, R.layout.menu_customlistview, keys, ids);
                    list1.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(list1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("testmenu",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String id=ID;
            String serverurl = params[0];
            String postparameters = "id="+ID;
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
    // 자기소개 등록하는 code
    class InsertData2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("test4",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String id=ID;
            String serverurl = params[0];
            String info = params[1];
            String postparameters = "id="+ID+"&desc="+info;
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
    //처음시작시 자기정보 불러오는 코드
    class InsertData3 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String json=s;
            try {
                JSONObject jsonObject=new JSONObject(json);
                String info=jsonObject.getString("description");
                String ad=jsonObject.getString("additional_desciption");
                String name=jsonObject.getString("name");
                Log.d("testinfo!",info);
                edit_name.setText(name);
                edit_info.setText(info);
                //addtional_info.setText(ad);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("testinfo",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String id=ID;
            String serverurl = params[0];
            String postparameters = "id="+ID;
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
    class InsertData5 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("testerase",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String id=ID;
            String serverurl = params[0];
            String style = params[1];
            String postparameters = "id="+ID+"&style="+style;
            Log.d("testinputtt",postparameters);
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
    class InsertData6 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String imageUrl = s;
            Glide.with(getApplicationContext()).load("https://salonspace.s3.ap-northeast-2.amazonaws.com/" + imageUrl).error(R.drawable.profile_default).into(image_profile);

            Log.d("testerase",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String id=ID;
            String serverurl = params[0];
            String style = params[1];
            String postparameters = "id="+ID+"&style="+style;
            Log.d("testinputtt",postparameters);
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