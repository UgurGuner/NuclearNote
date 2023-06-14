package com.example.nuclearnote.data.repository

import com.example.nuclearnote.data.data_source.NoteDao
import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id = id)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = note)
    }


}