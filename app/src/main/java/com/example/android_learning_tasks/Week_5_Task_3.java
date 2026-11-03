package com.example.android_learning_tasks;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// on app launch : OnCreate -> OnStart -> OnResume called
// click Home btn : OnPause -> OnStop called (app run in background)
// From Recent to App : onRestart -> OnStart -> onResume called
// on Rotate : OnPause -> OnStop -> onDestroy -> OnCreate -> OnStart -> OnResume called and Counter state not preserved
// Close with Back button : OnPause -> OnStop (app run in background)
// Remove from recent : OnPause -> OnStop -> onDestroy Called
// Rotate with SaveInstance : OnPause -> OnStop -> onSaveInstanceState -> onDestroy ->  OnCreate -> OnStart -> OnResume called

public class Week_5_Task_3 extends AppCompatActivity {

    private static final String TAG = "LifecycleLogger";
    TextView tvCount;
    Button btnCount;
    private int count;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week5_task3);

        tvCount = findViewById(R.id.tvCount);
        btnCount = findViewById(R.id.btnCount);

        if(savedInstanceState != null){
            count = savedInstanceState.getInt("count", 0);
        }
        tvCount.setText("Counter: " + count);

        btnCount.setOnClickListener(view -> {
            count++;
            tvCount.setText("Counter: " + count);
        });
        Log.d(TAG, "OnCreate called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
        Log.d(TAG, "onSaveInstanceState called");
    }
}

