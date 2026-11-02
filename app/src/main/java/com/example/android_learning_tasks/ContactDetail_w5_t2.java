package com.example.android_learning_tasks;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetail_w5_t2 extends AppCompatActivity {

    ImageView ivContact;
    TextView tvName, tvMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.contact_detail_w5_t2);

        ivContact = findViewById(R.id.ivContact);
        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        int image = getIntent().getIntExtra("image", 0);

        tvName.setText(name);
        tvMobile.setText(phone);
        ivContact.setImageResource(image);
    }
}
