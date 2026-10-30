package com.example.android_learning_tasks;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Week_4_Task_3 extends AppCompatActivity {

    ImageButton btnFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week4_task3);

        btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(v -> {
            btnFav.setSelected(!btnFav.isSelected());
            if(btnFav.isSelected()){
                btnFav.setImageResource(android.R.drawable.btn_star_big_on);
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();
            }else{
                btnFav.setImageResource(android.R.drawable.btn_star_big_off);
                Toast.makeText(this, "Removed from favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
