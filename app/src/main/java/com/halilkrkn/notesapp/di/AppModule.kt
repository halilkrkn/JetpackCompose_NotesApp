package com.halilkrkn.notesapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.halilkrkn.notesapp.feature_note.data.data_source.NoteDatabase
import com.halilkrkn.notesapp.feature_note.domain.repository.NoteRepository
import com.halilkrkn.notesapp.feature_note.domain.repository.NoteRepositoryImpl
import com.halilkrkn.notesapp.feature_note.domain.usecase.AddNoteUseCase
import com.halilkrkn.notesapp.feature_note.domain.usecase.DeleteNoteUseCase
import com.halilkrkn.notesapp.feature_note.domain.usecase.GetNoteUseCase
import com.halilkrkn.notesapp.feature_note.domain.usecase.GetNotesUseCase
import com.halilkrkn.notesapp.feature_note.domain.usecase.NoteUseCases
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
    fun provideNoteDatabase(app: Application) = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        NoteDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository),
            getNoteUseCase = GetNoteUseCase(repository)
        )
    }
}