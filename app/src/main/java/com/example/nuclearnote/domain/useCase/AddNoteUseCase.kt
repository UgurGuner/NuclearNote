package com.example.nuclearnote.domain.useCase

import com.example.nuclearnote.domain.model.InvalidNoteException
import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.repository.NoteRepository

class AddNoteUseCase(
    private val noteRepository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Note Title cannot be empty.")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("Note Content cannot be empty.")
        }

        noteRepository.insertNote(note = note)
    }
}