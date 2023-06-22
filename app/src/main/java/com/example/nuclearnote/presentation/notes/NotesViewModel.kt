package com.example.nuclearnote.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuclearnote.domain.use_case.NoteUseCases
import com.example.nuclearnote.domain.util.NoteOrder
import com.example.nuclearnote.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var notesJob: Job? = null

    init {
        getNotes(noteOrder = NoteOrder.Date(OrderType.Desc))
    }

    fun onEvent(event: NotesEvent) {

        when (event) {

            is NotesEvent.DeleteNote -> {

                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase.invoke(note = event.note)
                }

            }

            is NotesEvent.Order -> {

                if (state.value.noteOrder::class == event.noteOrder::class
                    && state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                getNotes(noteOrder = event.noteOrder)

            }

            is NotesEvent.RestoreNote -> {

                viewModelScope.launch {
                    noteUseCases.addNoteUseCase.invoke(note = event.note)
                }

            }

            is NotesEvent.ToggleOrderSection -> {

                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )

            }

        }

    }

    private fun getNotes(noteOrder: NoteOrder) {

        notesJob?.cancel()

        notesJob = noteUseCases.getNotesUseCase.invoke(noteOrder = noteOrder).onEach { notes ->

            _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)

        }.launchIn(scope = viewModelScope)

    }

}