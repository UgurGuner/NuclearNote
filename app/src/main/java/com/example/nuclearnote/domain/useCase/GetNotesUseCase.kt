package com.example.nuclearnote.domain.useCase

import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.repository.NoteRepository
import com.example.nuclearnote.domain.util.NoteOrder
import com.example.nuclearnote.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Desc)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Asc -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Title -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Desc -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Title -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}