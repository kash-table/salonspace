package com.example.salonspace;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

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

public class ProfileActivity extends AppCompatActivity {
    // 이미지 슬라이더
    SliderView sliderView;
    private SliderAdapterExample adapter;
    String ID;
    String url;
    ListView list1;
    ArrayList<HashMap<String,Object>> data_list;
    //처음 db에서 받아올 메뉴들
    ArrayList<String> m_name;
    ArrayList<String> m_price;
    //전달할 메뉴들
    ArrayList<String> m_name_p;
    ArrayList<String> m_price_p;

    TextView description;
    int result_price;
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        description=(TextView)findViewById(R.id.desc);
        list1=(ListView)findViewById(R.id.list_menu);
        ListListner5 listner2=new ListListner5();
        list1.setOnItemClickListener(listner2);
        //합계 초기화
        result_price=0;
        m_name=new ArrayList<String>();
        m_price=new ArrayList<String>();
        m_name_p=new ArrayList<String>();
        m_price_p=new ArrayList<String>();
        data_list=new ArrayList<HashMap<String, Object>>();
        //메뉴판 가져오기
        InsertData4 insertData4=new InsertData4();
        insertData4.execute(getString(R.string.IP_ADDRESS)+"get_designer_style_table.php");
        Log.d("aa","asdf");

        //intent 가져오기
        Intent intent=getIntent();
        ID=intent.getStringExtra("email");
        url=intent.getStringExtra("url");
        //디자이너 정보받아오기
        InsertData3 insertdata3 = new InsertData3();
        insertdata3.execute(getString(R.string.IP_ADDRESS)+"get_designer_info.php",ID);

        //디자이너 사진설정


        sliderView = (SliderView) findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        addNewItem("https://salonspace.s3.ap-northeast-2.amazonaws.com/"+url);
        Log.d("ppp",url);
        //addNewItem("data:image/jpeg;base64,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");
        //addNewItem("https://scontent-lga3-2.cdninstagram.com/v/t51.2885-15/sh0.08/e35/c240.0.960.960a/s640x640/96380058_1186616628346182_1112233204938198097_n.jpg?_nc_ht=scontent-lga3-2.cdninstagram.com&_nc_cat=106&_nc_ohc=ceBGoth1lvwAX-6vYlq&oh=cf5ed1265e208a93c6a3cb4645621b00&oe=5F86F130");
        //addNewItem("https://mblogthumb-phinf.pstatic.net/MjAyMDAxMThfNTEg/MDAxNTc5MzQxNzY2MjM4.zX1n8R67a8yHH7uJh3rDEjB6OuX7TCOpSCqcugfHpggg.5AHEjCl1Geq_v5G5icV4jSzjovbZC0exDaOf-pMiWkgg.GIF.jaepombo/1579341765098.gif?type=w800");
    }
    //리스너 셋팅
    class ListListner5 implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("test@",i+"번째 메뉴 값: "+m_name.get(i)+"/"+m_price.get(i));

            // view는 클릭한 Row의 view를 Object로 반환해 준다.
            CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox_menu);
            if(cb.isChecked()==true){
                cb.setChecked(false);
                m_name_p.remove(m_name.get(i).toString());
                m_price_p.remove(m_price.get(i).toString());
                result_price-=Integer.parseInt(m_price.get(i));
            }else{
                cb.setChecked(true);
                m_price_p.add(m_price.get(i).toString());
                m_name_p.add(m_name.get(i).toString());
                result_price+=Integer.parseInt(m_price.get(i));
            }
            Log.d("sum : ",""+result_price);

        }
    }

    //리뷰버튼
    public void reviewbtn(View v){
        Intent intent=new Intent(getApplicationContext(),ReviewActivity.class);
        intent.putExtra("ID",ID);
        startActivity(intent);
    }
    public void onReserveClick(View v){
        Intent  intent = new Intent(getApplicationContext(), ReserveActivity.class);
        intent.putExtra("price",result_price);
        intent.putExtra("listn",m_name_p);
        intent.putExtra("listp",m_price_p);
        startActivity(intent);
    }

    public void addNewItem(String url) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription("");
        sliderItem.setImageUrl(url);
        adapter.addItem(sliderItem);
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

            Log.d("aaa",s);
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
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data_list, R.layout.menu_check, keys, ids);
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
                String name=jsonObject.getString("description");

                description.setText(name);

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
}
