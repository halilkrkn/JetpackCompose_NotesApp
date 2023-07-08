package com.halilkrkn.notesapp.feature_note.domain.usecase

import com.halilkrkn.notesapp.feature_note.domain.model.Note
import com.halilkrkn.notesapp.feature_note.domain.repository.NoteRepository
import com.halilkrkn.notesapp.feature_note.domain.util.NoteOrder
import com.halilkrkn.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val repository: NoteRepository,
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    ): Flow<List<Note>> {

        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { note -> note.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { note -> note.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { note -> note.color }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { note -> note.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { note -> note.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { note -> note.color }
                    }
                }
            }
        }
    }
}