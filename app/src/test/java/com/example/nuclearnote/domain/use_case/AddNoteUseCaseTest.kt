package com.example.nuclearnote.domain.use_case

import com.example.nuclearnote.data.repository.FakeNoteRepository
import com.example.nuclearnote.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class AddNoteUseCaseTest {

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var addNoteUseCase: AddNoteUseCase

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }

    @Test
    fun `Add a note to the list correctly`() {

        val noteList = arrayListOf<Note>()

        val note = Note(
            id = 1,
            content = "Test Content",
            title = "Test Title",
            timeStamp = 1,
            color = 1
        )

        runBlocking {
            addNoteUseCase.invoke(note)
            fakeNoteRepository.getNotes().collectLatest { list ->
                list.forEach {
                    noteList.add(it)
                }
                assertThat(noteList.find { it.id == note.id }).isNotNull()
            }
        }

    }

}