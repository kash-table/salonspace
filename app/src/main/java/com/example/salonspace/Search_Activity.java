package com.example.salonspace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.ArrayUtils;

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

import static com.example.salonspace.ProfileUpdateActivity.setListViewHeightBasedOnChildren;

public class Search_Activity extends AppCompatActivity {

    Spinner spinner;
    ListView list1;
    //리스트뷰에 넣을 데이터
    ArrayList<String> url; //이미지 url
    ArrayList<String> name; //디자이너 이름
    ArrayList<Double> value; //평점
    ArrayList<Integer> count; //댓글 수
    ArrayList<String> email; // 이메일
    //검색 버튼
    Button searchbtn;
    //edittext 검색한 값 가져오기
    EditText searchbar;
    //리스트뷰에 저장될 리스트
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_);
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("이름으로 검색");
        arrayList.add("매장으로 검색");
        //스피너 항목 추가
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(arrayAdapter);

        //추후에 아이템 클릭시 !
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 선언
        url=new ArrayList<String>();
        name=new ArrayList<String>();
        value=new ArrayList<Double>();
        count=new ArrayList<Integer>();
        email=new ArrayList<String>();
        //리스트뷰 주소값
        list1=(ListView)findViewById(R.id.search_list);
        ListListner2 listListner=new ListListner2();
        list1.setOnItemClickListener(listListner);

        //edittext
        searchbar=(EditText)findViewById(R.id.search_text);
        //검색버튼 리스너


    }
    public void searchbtn(View v){
        url.clear();
        name.clear();
        value.clear();
        count.clear();
        email.clear();
        String search=searchbar.getText().toString();
        InsertData insertdata = new InsertData();

        insertdata.execute(getString(R.string.IP_ADDRESS)+"search_designers.php",search);

//        ListViewAdapter ad=new ListViewAdapter();
//        //여기서 db 랑 연동해서 추가하면됨!
//        ad.addiTem("Rohmindo","Rohmindo",3.5,2);
//
//        ad.addiTem("JunHo","JunHo",3.5,2);
//        list1.setAdapter(ad);
    }
    class ListViewAdapter extends BaseAdapter{

        LayoutInflater minflater;

        public void addiTem(String url_,String name_,Double value_,int count_,String mail){
            url.add(url_);
            name.add(name_);
            value.add(value_);
            count.add(count_);
            email.add(mail);
        }

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.d("pls","!!");
            if(view==null){
                minflater=getLayoutInflater();
                view=minflater.inflate(R.layout.searchcustomlist,null);

            }
            TextView value2=(TextView)view.findViewById(R.id.search_value);
            TextView count2=(TextView)view.findViewById(R.id.search_count);
            ImageView img2=(ImageView)view.findViewById(R.id.search_img);
            TextView text=(TextView)view.findViewById(R.id.search_name);
            text.setText(name.get(i));
            value2.setText(value.get(i).toString());
            count2.setText(count.get(i).toString());
            Glide.with(getApplicationContext()).load("https://salonspace.s3.ap-northeast-2.amazonaws.com/" + url.get(i).toString()).error(R.drawable.profile_default).into(img2);


            Log.d("pls","Asdf");
            return view;
        }
    }



    class ListListner2 implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("test@",i+"번째 메뉴 값: "+email.get(i).toString()+"/"+url.get(i).toString());
            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
            intent.putExtra("email",email.get(i).toString());
            intent.putExtra("url",url.get(i).toString());
            startActivity(intent);
        }
    }
    //이름이 맞는 디자이너 정보 받아오기
    class InsertData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test!","please wait...\n");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("error")){
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("data", "일치하는 디자이너가 없습니다.");
                startActivityForResult(intent, 1);
            }
            ListViewAdapter ad=new ListViewAdapter();
            //JSON파싱
            try {
                JSONArray jsonArray=new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String email=jsonObject.getString("id");
                    String path=jsonObject.getString("image_path");
                    Double avg=Double.parseDouble(jsonObject.getString("avg"));
                    int count=Integer.parseInt(jsonObject.getString("count"));
                    String name=jsonObject.getString("name");
                    Log.e("JsonName", name);

                    ad.addiTem(path,name,avg,count,email);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            //여기서 db 랑 연동해서 추가하면됨!

            list1.setAdapter(ad);


            Log.d("test4##",s);
        }

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String serverurl = params[0];
            String namevalue = params[1];
            String postparameters = "name="+namevalue;
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