package com.example.android_learning_tasks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.WindowCompat;

public class Week_7_Task_3 extends AppCompatActivity {

    static final String KEY_MODE = "display_mode";
    static final String DEFAULT_MODE = "light";
    RadioGroup rgMode;
    RadioButton rbLight, rbDark;
    Button btnReset;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.week7_task3);

        rgMode = findViewById(R.id.rgMode);
        rbLight = findViewById(R.id.rbLight);
        rbDark = findViewById(R.id.rbDark);
        btnReset = findViewById(R.id.btnReset);

        prefs = getSharedPreferences("system_mode", MODE_PRIVATE);

        String mode = prefs.getString(KEY_MODE, DEFAULT_MODE);
        applyMode(mode, false);

        rgMode.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedMode = (checkedId == R.id.rbDark) ? "dark" : "light";
            prefs.edit().putString(KEY_MODE, selectedMode).apply();
            applyMode(selectedMode, true);
            Toast.makeText(this, "Setting saved: " + selectedMode, Toast.LENGTH_SHORT).show();
        });

        btnReset.setOnClickListener(v -> {
            prefs.edit().putString(KEY_MODE, DEFAULT_MODE).apply();
            applyMode(DEFAULT_MODE, true);
            Toast.makeText(this, "Setting reset to default", Toast.LENGTH_SHORT).show();
        });
    }

    private void applyMode(String mode, boolean recreateIfNeeded) {
        if (mode.equals("dark")) {
            rbDark.setChecked(true);
            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                if (recreateIfNeeded) recreate();
            }
        } else {
            rbLight.setChecked(true);
            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                if (recreateIfNeeded) recreate();
            }
        }
    }
}