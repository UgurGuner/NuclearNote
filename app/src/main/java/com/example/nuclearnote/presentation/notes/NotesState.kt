package com.example.nuclearnote.presentation.notes

import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.util.NoteOrder
import com.example.nuclearnote.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(orderType = OrderType.Desc),
    val isOrderSectionVisible: Boolean = false
)