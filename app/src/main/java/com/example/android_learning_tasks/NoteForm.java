package com.example.android_learning_tasks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.concurrent.Executors;

public class NoteForm extends AppCompatActivity {

    TextInputLayout tilTitle, tilBody;
    TextInputEditText etTitle, etBody;
    Button btnSave;
    int noteId = -1;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.note_form);

        tilTitle = findViewById(R.id.tilTitle);
        tilBody = findViewById(R.id.tilBody);
        etTitle = findViewById(R.id.etTitle);
        etBody = findViewById(R.id.etBody);
        btnSave = findViewById(R.id.btnSave);

        noteId = getIntent().getIntExtra("noteId", -1);
        if (noteId != -1) {
            isEditMode = true;
            btnSave.setText("Update");
            Executors.newSingleThreadExecutor().execute(() -> {
                NoteModel note = NoteDatabase.getInstance(this).noteDao().getNoteById(noteId);
                if (note != null) {
                    runOnUiThread(() -> {
                        etTitle.setText(note.getTitle());
                        etBody.setText(note.getBody());
                    });
                }
            });
        }

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String body = etBody.getText().toString().trim();
            if(title.isEmpty()){
                tilTitle.setError("Title is required");
                tilTitle.requestFocus();
                return;
            }
            if(body.isEmpty()){
                tilBody.setError("Body is required");
                tilBody.requestFocus();
                return;
            }
            Executors.newSingleThreadExecutor().execute(() -> {
                if (isEditMode) {
                    NoteModel note = new NoteModel(title, body, System.currentTimeMillis());
                    note.setId(noteId);
                    NoteDatabase.getInstance(this).noteDao().update(note);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } else {
                    NoteModel note = new NoteModel(title, body, System.currentTimeMillis());
                    NoteDatabase.getInstance(this).noteDao().insert(note);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            });
        });
    }
}
