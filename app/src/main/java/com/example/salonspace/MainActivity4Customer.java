package com.example.salonspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity4Customer extends BaseCustomBarActivity {


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentDesigner fragmentDesigner = new FragmentDesigner();
    @Override
    protected void Init() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4_customer);
        // SetActionBarLayout(R.layout.actionbar_default);
        // Defalut Main Fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.frameLayout, fragment_home).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Change Fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //transaction.replace(R.id.frameLayout, null).commitAllowingStateLoss();
                switch(item.getItemId()){
                    case R.id.Item1:
                        Intent intent=new Intent(getApplicationContext(),MainActivity4Customer.class);
                        startActivity(intent);
                        //transaction.replace(R.id.frameLayout, fragment_home).commitAllowingStateLoss();
                        break;
                    case R.id.Item2:
                        finish();
                        transaction.replace(R.id.frameLayout, fragmentDesigner).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }


}
