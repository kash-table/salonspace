package com.example.salonspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity_custom extends AppCompatActivity {
    ViewPager pager;
    ArrayList<View> viewlist=new ArrayList<View>();

    //fragment
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentUser fragmentUser = new FragmentUser();
    private FragmentMypage fragmentMap = new FragmentMypage();
    private MypageDesignerActivity fragmentMypage = new MypageDesignerActivity();
    private FragmentReserveUser fragmentReserveUser = new FragmentReserveUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        View view = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
            }
        }

        setContentView(R.layout.activity_main_custom);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentUser).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Change Fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch(item.getItemId()){
                    case R.id.Item1:
                        transaction.replace(R.id.frameLayout, fragmentUser).commitAllowingStateLoss();
                        break;
                    case R.id.Item2:
                        transaction.replace(R.id.frameLayout, fragmentMap).commitAllowingStateLoss();
                        break;
                    case R.id.Item3:
                        transaction.replace(R.id.frameLayout, fragmentReserveUser).commitAllowingStateLoss();
                        break;
                    case R.id.Item4:

//                        transaction.replace(R.id.frameLayout, fragmentMypage).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
//        pager=(ViewPager)findViewById(R.id.pager);
//        LayoutInflater inflater2=getLayoutInflater();
//        View v1=inflater2.inflate(R.layout.advertising1,null);
//        View v2=inflater2.inflate(R.layout.advertising2,null);
//        viewlist.add(v1);
//        viewlist.add(v2);
//
//        CustomAdaptor adaptor=new CustomAdaptor();
//        pager.setAdapter(adaptor);
    }
    public void searchOnClick(View v){
        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
        startActivity(intent);
        return;
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
    public void btnlogout2(View v){
        DBHelper helper=new DBHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String sql="delete from Login";
        db.execSQL(sql);
        db.close();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
    public void onBackPressed() {
        //super.onBackPressed();
    }
}