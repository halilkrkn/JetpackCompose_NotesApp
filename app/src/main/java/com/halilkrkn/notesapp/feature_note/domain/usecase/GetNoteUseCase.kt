package com.halilkrkn.notesapp.feature_note.domain.usecase

import com.halilkrkn.notesapp.feature_note.domain.model.Note
import com.halilkrkn.notesapp.feature_note.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository,
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }

}