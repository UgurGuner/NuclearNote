package com.example.nuclearnote.domain.use_case

import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.repository.NoteRepository

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return noteRepository.getNoteById(id)
    }

}