package com.example.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addNote(note: Note)

    @Update
     fun updateNote(note: Note)

    @Delete
     fun deleteNote(note: Note)

    @Query("select * from notes order by id desc")
    fun getAllNotes(): LiveData<List<Note>>
}