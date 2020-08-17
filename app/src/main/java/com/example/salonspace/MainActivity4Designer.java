package com.example.salonspace;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity4Designer extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentDesigner fragmentDesigner = new FragmentDesigner();
    private FragmentMypage fragmentMypage = new FragmentMypage();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_designer);

        // Defalut Main Fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentMypage).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Change Fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //transaction.replace(R.id.frameLayout, null).commitAllowingStateLoss();
                switch(item.getItemId()){
                    case R.id.Item1:
                        transaction.replace(R.id.frameLayout, fragmentDesigner).commitAllowingStateLoss();
                        break;
                    case R.id.Item2:
                        transaction.replace(R.id.frameLayout, fragmentMypage).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

    }

}
