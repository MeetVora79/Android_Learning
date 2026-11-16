package com.example.android_learning_tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    Context context;
    List<NoteModel> notes;

    public NoteAdapter(Context context, List<NoteModel> notes){
        this.context = context;
        this.notes = notes;
    }

    public void setNotes(List<NoteModel> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_raw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        NoteModel note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvBody.setText(note.getBody());
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(new Date(note.getTimestamp()));
        holder.tvTimestamp.setText(formattedDate);

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, NoteForm.class);
            intent.putExtra("noteId", note.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("Delete Note")
                            .setMessage("Are you sure you want to delete this note?")
                            .setPositiveButton("Yes", (dialog1, which) -> {
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    NoteDatabase.getInstance(context).noteDao().delete(note);
                                });
                            })
                            .setNegativeButton("No", (dialog2, which) -> dialog2.dismiss())
                            .create();
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody, tvTimestamp;
        Button btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTimestamp = itemView.findViewById(R.id.tvTime);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
