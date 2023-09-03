package com.example.nuclearnote.di

import android.app.Application
import androidx.room.Room
import com.example.nuclearnote.data.dataSource.NoteDatabase
import com.example.nuclearnote.data.repository.NoteRepositoryImpl
import com.example.nuclearnote.domain.repository.NoteRepository
import com.example.nuclearnote.domain.useCase.AddNoteUseCase
import com.example.nuclearnote.domain.useCase.DeleteNoteUseCase
import com.example.nuclearnote.domain.useCase.GetNoteUseCase
import com.example.nuclearnote.domain.useCase.GetNotesUseCase
import com.example.nuclearnote.domain.useCase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = NoteDatabase::class.java,
            name = NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDao = db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository = noteRepository),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepository = noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository = noteRepository),
            getNoteUseCase = GetNoteUseCase(noteRepository = noteRepository)
        )
    }
}