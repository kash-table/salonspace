package com.example.salonspace;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class MainActivity4Designer extends AppCompatActivity {

    ViewPager pager;
    ArrayList<View> viewlist=new ArrayList<View>();
    TextView logout;

    //fragment
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentDesigner fragmentDesigner = new FragmentDesigner();
    private FragmentMypage fragmentMap = new FragmentMypage();
    private MypageDesignerActivity fragmentMypage = new MypageDesignerActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);


//  DB저장 잘됫나 확인 코드 ! ************************
        //DB 오픈
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql="select * from Login";
        Cursor c=db.rawQuery(sql,null);

        while(c.moveToNext()){
            // 가져올 컬럼의 인덱스 번호 추출
            int index=c.getColumnIndex("ID");
            int index2=c.getColumnIndex("PW");
            String id=c.getString(index);
            String pw=c.getString(index2);
            Log.d("test",id+pw);
        }
// ******************************************
        View view = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
            }
        }

        setContentView(R.layout.activity_main_custom);
        setContentView(R.layout.activity_main_designer);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentDesigner).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Change Fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch(item.getItemId()){
                    case R.id.Item1:
                        transaction.replace(R.id.frameLayout, fragmentDesigner).commitAllowingStateLoss();
                        break;
                    case R.id.Item2:
                        transaction.replace(R.id.frameLayout, fragmentMap).commitAllowingStateLoss();
                        break;
                    case R.id.Item3:
                        // transaction.replace(R.id.frameLayout, fragmentDesigner).commitAllowingStateLoss();
                        break;
                    case R.id.Item4:
                        transaction.replace(R.id.frameLayout, fragmentMypage).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }
    class CustomAdaptor extends PagerAdapter {
        @Override
        public int getCount() {
            return viewlist.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            pager.addView(viewlist.get(position));
            return viewlist.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            pager.removeView((View)object);
        }

    }
    public void MoveProfileManage(View view){
        Intent intent = new Intent(this, ProfileUpdateActivity.class);
        startActivity(intent);
        return;
    }
    // 로그아웃시 db 정보 없애기 !
    public void btnlogout(View v){
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql="delete from Login";
        db.execSQL(sql);
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
