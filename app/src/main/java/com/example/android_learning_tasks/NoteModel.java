package com.example.android_learning_tasks;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.Timestamp;

@Entity(tableName = "notes")
public class NoteModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String body;
    private long timestamp;

    public NoteModel(String title, String body, long timestamp){
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
