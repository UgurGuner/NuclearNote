package com.example.nuclearnote.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuclearnote.domain.useCase.NoteUseCases
import com.example.nuclearnote.domain.util.NoteOrder
import com.example.nuclearnote.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val _selectedOrder: MutableStateFlow<NoteOrder> = MutableStateFlow(NoteOrder.Date(OrderType.Desc))
    val selectedOrder: StateFlow<NoteOrder> = _selectedOrder.asStateFlow()

    private var notesJob: Job? = null

    init {
        getNotes(noteOrder = selectedOrder.value)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase.invoke(note = event.note)
                }
            }

            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                _selectedOrder.value = selectedOrder.value

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

            is NotesEvent.RefreshNotes -> {
                _selectedOrder.value = selectedOrder.value

                getNotes(noteOrder = event.noteOrder)
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