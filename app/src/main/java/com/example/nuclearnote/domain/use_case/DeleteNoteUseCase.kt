package com.example.nuclearnote.domain.use_case

import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note = note)
    }

}