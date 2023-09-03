package com.example.nuclearnote.domain.useCase

import com.example.nuclearnote.data.repository.FakeNoteRepository
import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.domain.util.NoteOrder
import com.example.nuclearnote.domain.util.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {

    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotesUseCase = GetNotesUseCase(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timeStamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()
        runBlocking {
            notesToInsert.forEach {
                fakeNoteRepository.insertNote(it)
            }
        }
    }

    @Test
    fun `Notes In order for ascending by Title`() = runBlocking {
        val notes = getNotesUseCase.invoke(NoteOrder.Title(OrderType.Asc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Notes In order for descending by Title`() = runBlocking {
        val notes = getNotesUseCase.invoke(NoteOrder.Title(OrderType.Desc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Notes In order for ascending by Date`() = runBlocking {
        val notes = getNotesUseCase.invoke(NoteOrder.Date(OrderType.Asc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isLessThan(notes[i + 1].timeStamp)
        }
    }

    @Test
    fun `Notes In order for descending by Date`() = runBlocking {
        val notes = getNotesUseCase.invoke(NoteOrder.Date(OrderType.Desc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timeStamp).isGreaterThan(notes[i + 1].timeStamp)
        }
    }

    @Test
    fun `Notes In order for ascending by Color`() = runBlocking {
        val notes = getNotesUseCase.invoke(NoteOrder.Color(OrderType.Asc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    fun `Notes In order for descending by Color`() = runBlocking {
        val notes = getNotesUseCase.invoke(NoteOrder.Color(OrderType.Desc)).first()

        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }
}