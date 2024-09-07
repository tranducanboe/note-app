package com.example.noteapp.repository

import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val db: NoteDatabase) {
    suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            db.getNoteDao().addNote(note)
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            db.getNoteDao().updateNote(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            db.getNoteDao().deleteNote(note)
        }
    }

    fun getAllNotes() = db.getNoteDao().getAllNotes()

}