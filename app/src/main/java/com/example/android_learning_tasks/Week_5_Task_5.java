package com.example.android_learning_tasks;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Week_5_Task_5 extends AppCompatActivity {

    BottomNavigationView btmNavigation;
    private Bundle pendingProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week5_task5);

        btmNavigation = findViewById(R.id.btmNavigation);

        btmNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.home){
                loadFragment(new HomeFragment());
            } else if (id == R.id.profile) {
               Fragment fragment = new ProfileFragment();
                if (pendingProfileData != null) {
                    fragment.setArguments(pendingProfileData);
                }
                loadFragment(fragment);
            }else {
                loadFragment(new SettingFragment());
            }
            return true;
        });

        if(savedInstanceState == null){
            btmNavigation.setSelectedItemId(R.id.home);
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment curr = getSupportFragmentManager().findFragmentById(R.id.container);
                if(curr instanceof HomeFragment){
                    finish();
                }else {
                    btmNavigation.setSelectedItemId(R.id.home);
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    public void profileData(Bundle args){
        pendingProfileData = args;
        btmNavigation.setSelectedItemId(R.id.profile);
    }
}
