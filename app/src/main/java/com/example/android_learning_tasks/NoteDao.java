package com.example.android_learning_tasks;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NoteModel note);

    @Update
    void update(NoteModel note);

    @Delete
    void delete(NoteModel note);

    @Query("select * from notes order by id desc")
    LiveData<List<NoteModel>> getAllNotes();

    @Query("select * from notes where id = :id")
    NoteModel getNoteById(int id);
}
