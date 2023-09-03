package com.example.nuclearnote.domain.useCase

import com.example.nuclearnote.data.repository.FakeNoteRepository
import com.example.nuclearnote.domain.model.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteUseCaseTest {

    private lateinit var getNoteUseCase: GetNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNoteUseCase = GetNoteUseCase(fakeNoteRepository)

        val note = Note(
            id = 1,
            content = "Test Content",
            title = "Test Title",
            timeStamp = 1,
            color = 1
        )

        runBlocking {
            fakeNoteRepository.insertNote(note)
        }
    }

    @Test
    fun `Added Note Has Been Found Correctly Test`() {
        val noteList = arrayListOf<Note>()

        runBlocking {
            fakeNoteRepository.getNotes().collectLatest { list ->
                list.forEach {
                    noteList.add(it)
                }
            }
            val note = getNoteUseCase.invoke(id = 1)
            assertThat(noteList.find { it.id == note?.id }).isNotNull()
        }
    }
}