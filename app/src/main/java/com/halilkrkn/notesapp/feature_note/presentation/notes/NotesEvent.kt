package com.halilkrkn.notesapp.feature_note.presentation.notes

import com.halilkrkn.notesapp.feature_note.domain.model.Note
import com.halilkrkn.notesapp.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNotes(val note: Note) : NotesEvent()
    object RestoreNotes : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
