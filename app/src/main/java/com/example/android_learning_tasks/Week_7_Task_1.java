package com.example.android_learning_tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Week_7_Task_1 extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.week7_task1);

        recyclerView = findViewById(R.id.rvNotes);
        fabAdd = findViewById(R.id.fabAdd);
        tvEmpty = findViewById(R.id.tvEmpty);

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(Week_7_Task_1.this, NoteForm.class));
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter adapter = new NoteAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        NoteDatabase.getInstance(this).noteDao().getAllNotes()
                .observe(this, notesList -> {
                    if (notesList == null || notesList.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter.setNotes(notesList);
                    }
                });
    }
}