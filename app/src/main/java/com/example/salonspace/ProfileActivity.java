package com.example.salonspace;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    ViewPager pager;
    ArrayList<View> viewlist=new ArrayList<View>();
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 없애기
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pager=(ViewPager)findViewById(R.id.pager);
        LayoutInflater inflater2 = getLayoutInflater();
        View v1=inflater2.inflate(R.layout.advertising1,null);
        View v2=inflater2.inflate(R.layout.advertising2,null);
        viewlist.add(v1);
        viewlist.add(v2);

        CustomAdaptor adaptor= new CustomAdaptor();
        pager.setAdapter(adaptor);
    }
    public void onReserveClick(View v){
        Intent  intent = new Intent(getApplicationContext(), ReserveActivity.class);
        startActivity(intent);
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
}
