package com.example.android_learning_tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Week_5_Task_2 extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvEmpty;
    ArrayList<ContactModel> contactList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week5_task2);

        recyclerView = findViewById(R.id.rvContacts);
        tvEmpty = findViewById(R.id.tvEmpty);

        initContactList();

        if (contactList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ContactAdapter adapter = new ContactAdapter(this, contactList);
            recyclerView.setAdapter(adapter);
        }
    }

    private void initContactList() {
        contactList.add(new ContactModel("Meet Vora", "9876543210", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Jay Varun", "9877654322", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Rahul Patel", "9898123456", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Priya Shah", "9825012345", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Amit Desai", "9724567890", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Neha Mehta", "9909876543", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Karan Joshi", "9879001122", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Pooja Trivedi", "9712345098", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Harsh Modi", "9825678901", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Isha Parmar", "9898765432", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Dhruv Thakkar", "9913456780", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Riya Gandhi", "9638521470", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Nikhil Solanki", "9726543210", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Sneha Bhatt", "9824098765", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Vivek Chauhan", "9879123456", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Kavya Rathod", "9909012345", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Manav Panchal", "9898456123", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Disha Vyas", "9712098765", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Yash Kapadia", "9925347816", R.drawable.ic_launcher_background));
        contactList.add(new ContactModel("Tanvi Dave", "9737612345", R.drawable.ic_launcher_background));
    }
}
