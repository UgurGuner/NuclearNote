package com.example.nuclearnote.domain.use_case

import com.example.nuclearnote.data.repository.FakeNoteRepository
import com.example.nuclearnote.domain.model.Note
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var note: Note

    @Before
    fun setUp() {

        fakeNoteRepository = FakeNoteRepository()
        deleteNoteUseCase = DeleteNoteUseCase(fakeNoteRepository)

        note = Note(
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
    fun `Delete a note from the list correctly`() {

        val noteList = arrayListOf<Note>()

        runBlocking {
            deleteNoteUseCase.invoke(note)
            fakeNoteRepository.getNotes().collectLatest { list ->
                list.forEach {
                    noteList.add(it)
                }
                Truth.assertThat(noteList.find { it.id == note.id }).isNull()
            }
        }

    }

}