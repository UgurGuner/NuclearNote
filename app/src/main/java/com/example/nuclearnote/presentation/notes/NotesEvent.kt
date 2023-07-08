package com.example.nuclearnote.presentation.notes

import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    data class RestoreNote(val note: Note): NotesEvent()
    object ToggleOrderSection: NotesEvent()
    data class RefreshNotes(val noteOrder: NoteOrder): NotesEvent()

}