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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    EditText text_review;
    String ID;
    String userID;
    ListView list1;
    ArrayList<String> name;
    ArrayList<String> content;
    ArrayList<HashMap<String,Object>> data_list;
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        text_review=(EditText)findViewById(R.id.text_review);
        list1=(ListView)findViewById(R.id.review_list);
        //초기화
        name=new ArrayList<String>();
        content=new ArrayList<String>();
        data_list=new ArrayList<HashMap<String, Object>>();
        //내부 DB오픈
        DBHelper helper =new DBHelper(getApplicationContext());
        SQLiteDatabase db=helper.getReadableDatabase();

        String LoadID="select ID from Login";
        Cursor c=db.rawQuery(LoadID,null);
        userID="";
        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호 추출
            int index=c.getColumnIndex("ID");
            userID=c.getString(index);
            Log.d("testID",""+userID);
        }

        //id 값 가져오기
        Intent intent=getIntent();
        ID=intent.getStringExtra("ID");

        InsertData2 insertd=new InsertData2();
        insertd.execute(getString(R.string.IP_ADDRESS)+"get_review1.php",ID);

        name.add("노민도");
        content.add("sadf");
        //처음 시작이 외부 db에서 리뷰가져오기.


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
    //리뷰 등록 버튼
    public void btn(View v){
        String content=text_review.getText().toString();
        InsertData insertdata = new InsertData();
        insertdata.execute(getString(R.string.IP_ADDRESS)+"upload_review1.php",ID,content);

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
            Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
            intent.putExtra("data", "리뷰 등록이 완료되었습니다.");
            startActivityForResult(intent, 1);

            Log.d("test4",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String id = params[1];
            String content =params[2];
            String postparameters = "dsg_id="+id+"&ctm_id="+userID+"&comment="+content;
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
    //리뷰 가져오기
    class InsertData2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//arraylist 비우기
            name.clear();
            content.clear();
            data_list.clear();
            try {
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String n=jsonObject.getString("ctm_name");
                    String p=jsonObject.getString("comment");
                    name.add(n);
                    content.add(p);
                    Log.d("testarrayp",content.get(i));
                    Log.d("testarrayn",name.get(i));
                    //메뉴판 추가
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("menu_name", n);
                    map.put("menu_content", p);
                    data_list.add(map);
                    String[] keys = {"menu_name", "menu_content"};
                    int[] ids = {R.id.name_r, R.id.content_r};
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data_list, R.layout.review_custom, keys, ids);
                    list1.setAdapter(adapter);
                    setListViewHeightBasedOnChildren(list1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("testreview",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String id = params[1];
            String postparameters = "dsg_id="+id;
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
}
